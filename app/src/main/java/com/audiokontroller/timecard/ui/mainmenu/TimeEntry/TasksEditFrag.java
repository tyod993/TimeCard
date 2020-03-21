package com.audiokontroller.timecard.ui.mainmenu.TimeEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.model.Task;

import java.util.Objects;

public class TasksEditFrag extends Fragment {

    private Task currentTask;

    private AutoCompleteTextView editName;
    private Spinner durationSpinner;
    private Button saveButton;
    private Button cancelButton;

    public TasksEditFrag(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return View.inflate(getContext(), R.layout.task_edit_layout, container);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeEntryEditViewModel viewModel = new ViewModelProvider(getParentFragment()).get(TimeEntryEditViewModel.class);
        currentTask = viewModel.getTimeEntry().getValue().getTasks().get(getArguments().getInt("TASK_POSITION"));

        editName = getActivity().findViewById(R.id.task_edit_pop_name_tv);
        durationSpinner = getActivity().findViewById(R.id.task_edit_pop_duration_spin);
        saveButton = getActivity().findViewById(R.id.task_edit_pop_save_btn);
        cancelButton = getActivity().findViewById(R.id.task_edit_pop_cancel_btn);

        editName.setText(currentTask.getName());
        //When Debugging make sure that the resource layout called in the ArrayAdapter is right and looks good!!!
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, currentTask.TIME_SELECTIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter);

        saveButton.setOnClickListener(view -> {
            currentTask.setName(editName.getText().toString());
            currentTask.setHours(durationSpinner.getSelectedItemPosition());
            viewModel.replaceTask(currentTask, getArguments().getInt("TASK_POSITION"));
            //TODO: Add Navigation
        });

        cancelButton.setOnClickListener(view->{
          //TODO: Add Navigation
        });
    }

}
