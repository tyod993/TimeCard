package com.audiokontroller.timecard.ui.TimeEntry;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.TimeEntry.TimeEntryHandler;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.audiokontroller.timecard.ui.mainmenu.MainClockInViewModel;
import com.audiokontroller.timecard.ui.utils.TimePickerFrag;

import java.util.Calendar;
import java.util.Objects;

public class TimeEntryReviewFrag extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private MainClockInViewModel viewModel;

    private boolean isStartEdit;

    //Get rid of the notes section and add an time edit section
    private TextView totalHoursTV;
    private TextView startTimeTV;
    private TextView endTimeTV;
    private Spinner projectsSpinner;
    private Spinner tasksSpinner;
    private Spinner taskHourSpinner;
    private ImageView addTaskButton;
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
        projectsSpinner = rootView.findViewById(R.id.project_sp);
        tasksSpinner = rootView.findViewById(R.id.required_tasks_sp);
        taskHourSpinner = rootView.findViewById(R.id.required_task_hours_sp);
        addTaskButton = rootView.findViewById(R.id.add_task_btn);
        submitButton = rootView.findViewById(R.id.submit_btn);
        deleteButton = rootView.findViewById(R.id.delete_time_btn);
        editStartButton = rootView.findViewById(R.id.edit_start_btn);
        editEndButton = rootView.findViewById(R.id.edit_end_btn);

        viewModel = new ViewModelProvider(requireActivity()).get(MainClockInViewModel.class);
        initUIState(Objects.requireNonNull(viewModel.getLiveTimeEntry().getValue()));
        //TODO Change the spinners to editText with suggestions

        submitButton.setOnClickListener(view-> {

        });

        deleteButton.setOnClickListener(view->{

        });

        editStartButton.setOnClickListener(view -> {
            DialogFragment timePickerDialog = new TimePickerFrag();
            timePickerDialog.show(getParentFragmentManager(), "start time picker");
            isStartEdit  = true;
        });

        editEndButton.setOnClickListener(view -> {
            DialogFragment timePickerDialog = new TimePickerFrag();
            timePickerDialog.show(getParentFragmentManager(), "end time picker");
        });


    }

    private void initUIState(@NonNull TimeEntry entry){
        totalHoursTV.setText(entry.getTotalHours() + "");
        startTimeTV.setText(
                entry.getEntryStartTime().get(Calendar.HOUR)+
                        entry.getEntryStartTime().get(Calendar.MINUTE) +
                        entry.getEntryStartTime().get(Calendar.AM_PM)
                );
        endTimeTV.setText(
                entry.getEntryEndTime().get(Calendar.HOUR)+
                        entry.getEntryEndTime().get(Calendar.MINUTE)+
                        entry.getEntryEndTime().get(Calendar.AM_PM)
        );
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(isStartEdit){
            viewModel.getLiveTimeEntry().getValue().getEntryStartTime().set(Calendar.HOUR, hourOfDay);
            viewModel.getLiveTimeEntry().getValue().getEntryStartTime().set(Calendar.MINUTE, minute);
            viewModel.updateLiveEntry();
        }
    }
}
