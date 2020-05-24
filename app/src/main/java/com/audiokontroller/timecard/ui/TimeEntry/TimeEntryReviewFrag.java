package com.audiokontroller.timecard.ui.TimeEntry;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.TimeEntry.TimeEntryHandler;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.audiokontroller.timecard.data.model.UserPref;
import com.audiokontroller.timecard.ui.mainmenu.MainClockInViewModel;
import com.audiokontroller.timecard.ui.utils.TimePickerFrag;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TimeEntryReviewFrag extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = TimeEntryReviewFrag.class.getSimpleName();

    public static final String[] hrsArray = {
            "15min", "30min", "45min", "1hr", "2hr", "3hrs", "4hrs", "5hrs", "6hrs", "7hrs", "8hrs",
            "9hrs", "10hrs", "11hrs", "12hrs"
    };

    private MainClockInViewModel viewModel;

    private boolean isStartEdit;

    //Get rid of the notes section and add an time edit section
    private TextView totalHoursTV;
    private TextView startTimeTV;
    private TextView endTimeTV;
    private AutoCompleteTextView projectsAutoComplete;
    private Button submitButton;
    private Button deleteButton;
    private Button editStartButton;
    private Button editEndButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return layoutInflater.inflate(R.layout.time_review_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        totalHoursTV = rootView.findViewById(R.id.review_total_hours_tv);
        startTimeTV = rootView.findViewById(R.id.start_time_data_tv);
        endTimeTV = rootView.findViewById(R.id.end_time_data_tv);
        projectsAutoComplete = rootView.findViewById(R.id.project_auto);
        submitButton = rootView.findViewById(R.id.submit_btn);
        deleteButton = rootView.findViewById(R.id.delete_time_btn);
        editStartButton = rootView.findViewById(R.id.edit_start_btn);
        editEndButton = rootView.findViewById(R.id.edit_end_btn);

        viewModel = new ViewModelProvider(requireActivity()).get(MainClockInViewModel.class);

        //This sets the starting state of the fragment using the previous clocking state;
        updateUIState(Objects.requireNonNull(viewModel.getLiveTimeEntry().getValue()));

        //these all need to be launched as a seperate asych task while waiting on user retieval
        //Setting the AutoCompleteTextViews to the liveUser UserPref.
        //TODO there should be a SavedPreferences check before these are set
        projectsAutoComplete.setAdapter(viewModel.getSuggestions(UserPref.PROJECT));


        //

        //

        viewModel.getLiveTimeEntry().observe(getViewLifecycleOwner(), this::updateUIState);

        submitButton.setOnClickListener(view-> {
            viewModel.submitEntry();
            NavHostFragment.findNavController(TimeEntryReviewFrag.this).navigate(R.id.action_timeEntryReviewFrag_to_mainClockInFrag);
        });

        deleteButton.setOnClickListener(view->{
        });

        editStartButton.setOnClickListener(view -> {
            isStartEdit  = true;
            DialogFragment timePickerDialog = new TimePickerFrag(this);
            timePickerDialog.show(getParentFragmentManager(), "start time picker");
        });

        editEndButton.setOnClickListener(view -> {
            DialogFragment timePickerDialog = new TimePickerFrag(this);
            timePickerDialog.show(getParentFragmentManager(), "end time picker");
        });
    }

    /*
        The following code is for a future feature allowing for the adding of tasks to Time Entries.
        Code in this section may or may not work and should'nt be depended on for key functionality.
        If you're adding to the Tasks features functionality, please confine the code that you write
        to this section between the borders.
         */

    // <-------------------------------------------------------------------------------------->

    //Things to be added to the onViewCreated method
    /*
        addTaskButton.setOnClickListener(view -> {});

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.duration_array,
                R.layout.spinner_item_1
                );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskHourSpinner.setAdapter(adapter);

        tasksAutoComplete.setAdapter(viewModel.getSuggestions(UserPref.TASK));

     */


    // <-------------------------------------------------------------------------------------->

    //At some time this should be optimized
    private void updateUIState(@NonNull TimeEntry entry){

        String totalHours = entry.getTotalHours() + "";
        String startTime;
        String endTime;


        //Check if totalHours is a whole number or not
        if(entry.getTotalHours() % 1 != 0) {
            String[] totalWholeHours = totalHours.split("\\.");
            String ending = totalWholeHours[1].substring(0, 2);
            Log.d(TAG, totalWholeHours[0] + "sec = " + ending);
            totalHours = totalWholeHours[0].concat("." + ending);
        }


        //Format start time for UI
        if(entry.getEntryStartTime().get(Calendar.MINUTE) < 10) {
            startTime = entry.getEntryStartTime().get(Calendar.HOUR) + ":0" +
                    entry.getEntryStartTime().get(Calendar.MINUTE) + " " +
                    meridiemIntToString(entry.getEntryStartTime().get(Calendar.AM_PM));
        } else {
            startTime = entry.getEntryStartTime().get(Calendar.HOUR) + ":" +
                    entry.getEntryStartTime().get(Calendar.MINUTE) + " " +
                    meridiemIntToString(entry.getEntryStartTime().get(Calendar.AM_PM));
        }

        //Format end time for UI
        if(entry.getEntryEndTime().get(Calendar.MINUTE) < 10) {
            endTime = entry.getEntryEndTime().get(Calendar.HOUR) + ":0" +
                    entry.getEntryEndTime().get(Calendar.MINUTE) + " " +
                    meridiemIntToString(entry.getEntryEndTime().get(Calendar.AM_PM));
        } else {
            endTime = entry.getEntryEndTime().get(Calendar.HOUR) + ":" +
                    entry.getEntryEndTime().get(Calendar.MINUTE) + " " +
                    meridiemIntToString(entry.getEntryEndTime().get(Calendar.AM_PM));
        }

        //Update UI with formatted Strings
        startTimeTV.setText(startTime);
        endTimeTV.setText(endTime);
        totalHoursTV.setText(totalHours);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        viewModel.entryTimeChange(hourOfDay, minute, isStartEdit);
        isStartEdit = false;
    }

    private String meridiemIntToString(int value){
        if(value == 0){
            return "AM";
        }else {
            return "PM";
        }
    }
}
