package com.audiokontroller.timecard.ui.TimeEntry;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.audiokontroller.timecard.ui.utils.BreaksListAdapter;
import com.audiokontroller.timecard.ui.utils.TasksListAdapter;
import com.audiokontroller.timecard.ui.utils.TimePickerFrag;

import java.util.Calendar;

public class TimeEntryEditFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private TimeEntry timeEntry;
    private TimeEntryEditViewModel viewModel;
    private boolean isStartEdit;

    //UI Components
    private ConstraintLayout startModule;
    private ConstraintLayout endModule;
    private ConstraintLayout tasksModule;
    private ConstraintLayout breaksModule;

    private ListView breaksListView;
    private ListView tasksList;
    private AutoCompleteTextView projectET;
    private TextView startTV;
    private TextView endTV;
    private TextView addTaskTv;
    private TextView addBreakTv;
    private Button saveBtn;
    private Button cancelBtn;

    // TODO: Add the save button
    // TODO: Apply the user Settings and dynamically load the UI accordingly

    public TimeEntryEditFragment(@NonNull TimeEntry timeEntry){
        this.timeEntry = timeEntry;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_entry_edit_frag, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TimeEntryEditViewModel.class);
        viewModel.setActiveTimeEntry(timeEntry);

        //Visual data components
        projectET = getActivity().findViewById(R.id.edit_project_name_et);
        startTV = getActivity().findViewById(R.id.edit_time_start_tv);
        endTV = getActivity().findViewById(R.id.edit_time_end_tv);
        tasksList = getActivity().findViewById(R.id.edit_time_tasks_list_view);
        breaksListView = getActivity().findViewById(R.id.edit_time_breaks_list_view);
        addTaskTv = getActivity().findViewById(R.id.edit_time_add_task_tv);
        addBreakTv = getActivity().findViewById(R.id.edit_time_add_break_tv);
        saveBtn = getActivity().findViewById(R.id.task_edit_pop_save_btn);
        cancelBtn = getActivity().findViewById(R.id.edit_time_cancel_btn);


        // Initial UI Draw
            if(viewModel.getTimeEntry().getValue().getJobName() != null) {
                projectET.setText(viewModel.getTimeEntry().getValue().getJobName());
            }else {
                projectET.setText(R.string.none);
            }
            Calendar time1 = viewModel.getTimeEntry().getValue().getEntryStartTime();
            startTV.setText(time1.get(Calendar.AM_PM) + time1.get(Calendar.HOUR));
            if(viewModel.getTimeEntry().getValue().getEntryEndTime() != null) {
                time1 = viewModel.getTimeEntry().getValue().getEntryEndTime();
                endTV.setText(time1.get(Calendar.HOUR) + time1.get(Calendar.AM_PM));
            } else {
                endTV.setText(R.string.present);
            }

            //The order of if statements should switch once the user preferences is implemented
            if(viewModel.getTimeEntry().getValue().getTasks() != null) {
                tasksList.setAdapter(new TasksListAdapter(viewModel.getTimeEntry().getValue().getTasks(), true));
            }
            if(viewModel.getTimeEntry().getValue().getBreaks() != null){
                breaksListView.setAdapter(new BreaksListAdapter(viewModel.getTimeEntry().getValue().getBreaks(), true));
            }

            //
            // *******  OnClickListeners ********
            //

            addTaskTv.setOnClickListener(view -> {
                //TODO: Add Navigation Component to EditBreakFragment. And edit EditBreak fragment for adding a break
            });

            addBreakTv.setOnClickListener(view->{
                //TODO: Add Navigation Component to EditTaskFragment. And edit EditTaskFragment for adding a task
            });

            startTV.setOnClickListener(view -> {
                DialogFragment timePickerDialog = new TimePickerFrag(this);
                timePickerDialog.show(getParentFragmentManager(), "start time picker");
                isStartEdit = true;
            });

            endTV.setOnClickListener(view -> {
                DialogFragment timePickerDialog = new TimePickerFrag(this);
                timePickerDialog.show(getParentFragmentManager(), "end time picker");
            });

            saveBtn.setOnClickListener(view -> {

            });

            cancelBtn.setOnClickListener(view -> {
                //TODO; Add navigation component
            });

            //
            //
            //


        viewModel.getTimeEntry().observe(this, timeEntry1 ->{
                    if(timeEntry1 != null && !timeEntry1.isActive()){
                        if(timeEntry1.getJobName() != null) {
                            projectET.setText(timeEntry1.getJobName());
                        }
                        Calendar time = timeEntry1.getEntryStartTime();
                        startTV.setText(time.get(Calendar.HOUR) + time.get(Calendar.AM_PM));
                        if(timeEntry1.getEntryEndTime() != null) {
                            time = timeEntry1.getEntryEndTime();
                            endTV.setText(time.get(Calendar.HOUR) + time.get(Calendar.AM_PM));
                        }
                    } else{
                        projectET.setText(R.string.time_card_loading_error_message);
                        Toast.makeText(this.getContext(), R.string.time_card_loading_error_message, Toast.LENGTH_LONG).show();
                    }
                });

            viewModel.getTaskList().observe(this, tasks -> {
                if (tasks != null) {
                    tasksList.setAdapter(new TasksListAdapter(viewModel.getTaskList().getValue(), true));
                }
            });

            viewModel.getBreaksList().observe(this, breaks -> {
                if(breaks != null){
                    breaksListView.setAdapter(new BreaksListAdapter(viewModel.getBreaksList().getValue(), true));
                }
            });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(isStartEdit){
                viewModel.getTimeEntry().getValue().getEntryStartTime().set(Calendar.HOUR, hourOfDay);
                viewModel.getTimeEntry().getValue().getEntryStartTime().set(Calendar.MINUTE, minute);
                isStartEdit = false;
        } else {
            viewModel.getTimeEntry().getValue().getEntryEndTime().set(Calendar.HOUR, hourOfDay);
            viewModel.getTimeEntry().getValue().getEntryEndTime().set(Calendar.MINUTE, minute);
        }
    }
}
