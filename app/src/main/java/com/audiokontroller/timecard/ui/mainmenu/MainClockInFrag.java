package com.audiokontroller.timecard.ui.mainmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.audiokontroller.timecard.R;

public class MainClockInFrag extends Fragment {

    private final String TAG = MainClockInFrag.class.getSimpleName();

    private final int[] buttonImageResID = {R.raw.clock_initial, R.raw.clock_out};

    public SharedPreferences preferences;

    private TextView totalHoursTV;
    private ImageView clockinButton;
    private Button breakButton;

    private MainClockInViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                                @Nullable Bundle savedInstanceState){
        return layoutInflater.inflate(R.layout.main_clockin_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        if(getActivity().getPreferences(Context.MODE_PRIVATE) != null){
             preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        } else {
            //this could be fixed
            preferences.edit().putBoolean(getResources().getString(R.string.is_clocked_in_key), false).apply();
        }

        viewModel = new ViewModelProvider(requireActivity()).get(MainClockInViewModel.class);

        totalHoursTV = rootView.findViewById(R.id.total_hours_clk_in);
        clockinButton = rootView.findViewById(R.id.clockin_button);
        breakButton = rootView.findViewById(R.id.break_button);

        viewModel.setClockState(getStateFromPreferences());

        //Draw all of the components with existing ClockState
        TimeClockFormState clockState = viewModel.getClockState().getValue();//This value is only used once to avoid more method calls
        Log.d(TAG, "Current clock state: ButtonState = " + clockState.getClockButtonState() + " TotalHours = " + clockState.getTotalHours() + " Break State = " + clockState.isOnBreak());
        clockinButton.setImageResource(buttonImageResID[clockState.getClockButtonState()]);//Ignore this error. line 62 sets the value so it cant be null

        if(clockState.getTotalHours() == null){
            totalHoursTV.setText("0");
        } else {
            totalHoursTV.setText(clockState.getTotalHours());
        }

        if(clockState.isOnBreak()){
            breakButton.setText(R.string.end_break);
        }

        //Observe the ClockState updating when it is changed.
        viewModel.getClockState().observe(getViewLifecycleOwner(), clockFormState ->{

            clockinButton.setImageResource(buttonImageResID[clockFormState.getClockButtonState()]);
            totalHoursTV.setText(clockFormState.getTotalHours());
            if(clockFormState.isOnBreak()) {
                breakButton.setText(R.string.end_break);
            } else {
                breakButton.setText(R.string.break_button);
            }
        });

        viewModel.getLiveTimeEntry().observe(getViewLifecycleOwner(), timeEntry -> {
            if(timeEntry != null) {
                if (timeEntry.getEntryEndTime() != null) {
                    Navigation.findNavController(rootView).navigate(R.id.action_mainClockInFrag_to_timeEntryReviewFrag);
                }
            }
        });

        clockinButton.setOnClickListener(view ->{
            viewModel.clockButtonPressed();
        });

        breakButton.setOnClickListener(view ->{
            viewModel.breakButtonPressed();
        });

        //

        MainMenuViewModel mainViewModel = new ViewModelProvider(getActivity()).get(MainMenuViewModel.class);
        mainViewModel.retrieveUser().observe(getViewLifecycleOwner(), user -> {
            if(user != null) {
                //TODO there is a problem here. The User is not being passed when the RX ends??

                viewModel.setUser(user);
                Log.d(TAG, "User posted to UserLiveData");
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            error.printStackTrace();
            Log.e(TAG, error.toString());
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        saveStateToPref();

    }

    @Override
    public void onResume() {
        super.onResume();
        checkClockStateManually();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        saveStateToPref();
        MainMenuViewModel mainMenuViewModel = new ViewModelProvider(getActivity()).get(MainMenuViewModel.class);
        viewModel.validateTimeEntry(mainMenuViewModel);
    }


    private TimeClockFormState getStateFromPreferences(){
        if(preferences.getBoolean(getResources().getString(R.string.is_clocked_in_key), false)) {
            return new TimeClockFormState(
                    preferences.getInt(getResources().getString(R.string.clock_state_key), TimeClockFormState.DEFAULT_CLOCK_STATE),
                    preferences.getBoolean(getResources().getString(R.string.on_break_key), TimeClockFormState.DEFAULT_BREAK_STATE),
                    preferences.getString(getResources().getString(R.string.total_hours_key), TimeClockFormState.DEFAULT_TOTAL_HOURS));
        } else {
            return new TimeClockFormState();
        }
    }

    // In the future change this to cache the data until onDestroy is called for better performance
    private void saveStateToPref(){
        TimeClockFormState formState = viewModel.getClockState().getValue();
        if(formState != null) {
            preferences.edit().putInt(getResources().getString(R.string.clock_state_key), formState.getClockButtonState()).apply();
            preferences.edit().putBoolean(getResources().getString(R.string.on_break_key), formState.isOnBreak()).apply();
            preferences.edit().putString(getResources().getString(R.string.total_hours_key), formState.getTotalHours()).apply();

        }
    }

    private void checkClockStateManually(){
        TimeClockFormState formState = getStateFromPreferences();
            totalHoursTV.setText(formState.getTotalHours());
            clockinButton.setImageResource(formState.getClockButtonState());
            if(formState.isOnBreak()) {
                breakButton.setText(R.string.end_break);
            } else{
                breakButton.setText(R.string.break_button);
            }

    }
}
