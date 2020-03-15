package com.audiokontroller.timecard.ui.mainmenu.TimeEntry;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.model.TimeEntry;

import java.util.Calendar;

public class TimeEntryEditFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private final TimeEntry timeEntry;
    private final TimeEntryEditViewModel viewModel;

    //UI Components
    private ConstraintLayout projectModule;//This can be deleted??
    private ConstraintLayout startModule;
    private ConstraintLayout endModule;
    private ConstraintLayout tasksModule;
    private ConstraintLayout breaksModule;

    private ListView breaksListView;
    private ListView tasksList;
    private AutoCompleteTextView projectET;
    private TextView startTV;
    private TextView endTV;

    // TODO: Add the save button

    public TimeEntryEditFragment(@NonNull TimeEntry timeEntry){
        this.timeEntry = timeEntry;
        viewModel = new ViewModelProvider(this).get(TimeEntryEditViewModel.class);
        viewModel.setActiveTimeEntry(timeEntry);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_entry_edit_frag, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Should use Data Binding eventually
        //Finding Layout references for click actions
        projectModule.findViewById(R.id.edit_project_name_lo);
        startModule.findViewById(R.id.edit_time_start_lo);
        endModule.findViewById(R.id.edit_time_end_lo);
        tasksModule.findViewById(R.id.edit_time_tasks_lo);
        breaksModule.findViewById(R.id.edit_time_breaks_lo);

        //Visual data components
        projectET.findViewById(R.id.edit_project_name_et);
        startTV.findViewById(R.id.edit_time_start_tv);
        endTV.findViewById(R.id.edit_time_end_tv);
        tasksList.findViewById(R.id.edit_time_tasks_list_view);
        breaksListView.findViewById(R.id.edit_time_breaks_list_view);

        viewModel.getTimeEntry().observe(this, timeEntry1 ->{
                    if(timeEntry1 != null && !timeEntry1.isActive()){
                        projectET.setText(timeEntry1.getJobName());
                        Calendar time = timeEntry1.getEntryStartTime();
                        startTV.setText(time.get(Calendar.HOUR) + time.get(Calendar.AM_PM));
                        time = timeEntry1.getEntryEndTime();
                        endTV.setText(time.get(Calendar.HOUR) + time.get(Calendar.AM_PM));
                    } else{
                        projectET.setText(R.string.time_card_loading_error_message);
                        Toast.makeText(this.getContext(), R.string.time_card_loading_error_message, Toast.LENGTH_LONG).show();
                    }
                });

        viewModel.getTaskList().observe(this, tasks -> {
            if(tasks != null){

            }else{

            }

        });

        viewModel.getBreaksList().observe(this, breaks -> {


        });

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
