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
        currentTask = viewModel.getTimeEntry().getValue().getTasks().get(getArguments().getInt("EDIT_TASK"));

        editName.findViewById(R.id.task_edit_pop_name_tv);
        durationSpinner.findViewById(R.id.task_edit_pop_duration_spin);
        saveButton.findViewById(R.id.task_edit_pop_save_btn);
        cancelButton.findViewById(R.id.task_edit_pop_cancel_btn);

        editName.setText(currentTask.getmName());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.duration_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter);

        saveButton.setOnClickListener(view -> {

        });

        cancelButton.setOnClickListener(view->{
          //FragmentTransaction im tired byby

        });
    }

}
