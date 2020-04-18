package com.audiokontroller.timecard.ui.mainmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.audiokontroller.timecard.R;

public class MainClockInFrag extends Fragment {

    private final String TAG = MainClockInFrag.class.getSimpleName();

    private final int[] buttonImageResID = {R.raw.clock_initial, R.raw.clock_out};

    public final SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

    private TimeClockFormState clockState;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainClockInViewModel.class);

        totalHoursTV = view.findViewById(R.id.total_hours_clk_in);
        clockinButton = view.findViewById(R.id.clockin_button);
        breakButton = view.findViewById(R.id.break_button);

        getStateFromPreferences();

        //Draw all of the components with existing ClockState
        clockinButton.setImageResource(buttonImageResID[clockState.getClockButtonState()]);
        totalHoursTV.setText(clockState.getTotalHours());
        if(clockState.isOnBreak()){
            breakButton.setText(R.string.end_break);
        }

        //Observe the ClockState updating when it is changed.
        viewModel.getClockState().observe(getViewLifecycleOwner(), clockFormState ->{

            clockinButton.setImageResource(clockFormState.getClockButtonState());
            totalHoursTV.setText(clockFormState.getTotalHours());
            if(clockFormState.isOnBreak()) {
                breakButton.setText(R.string.break_button);
            } else {
                breakButton.setText(R.string.end_break);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        // In the future change this to cache the data until onDestroy is called for better performance
        TimeClockFormState formState = viewModel.getClockState().getValue();
        if(formState != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(getResources().getString(R.string.clock_state_key), formState.getClockButtonState());
            editor.putBoolean(getResources().getString(R.string.on_break_key), formState.isOnBreak());
            editor.putInt(getResources().getString(R.string.total_hours_key), formState.getTotalHours());
            editor.apply();
        }
    }


    private void getStateFromPreferences(){
        if(preferences.getBoolean(getResources().getString(R.string.is_clocked_in_key), false)) {
            clockState = new TimeClockFormState(
                    preferences.getInt(getResources().getString(R.string.clock_state_key), MainClockInViewModel.DEFAULT_CLOCK_STATE),
                    preferences.getBoolean(getResources().getString(R.string.on_break_key), MainClockInViewModel.DEFAULT_BREAK_STATE),
                    preferences.getInt(getResources().getString(R.string.total_hours_key), MainClockInViewModel.DEFAULT_TOTAL_HOURS));
        } else {
            clockState = new TimeClockFormState();
        }
    }
}
