package com.audiokontroller.timecard.ui.TimeEntry;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.model.Break;
import com.audiokontroller.timecard.ui.utils.TimePickerFrag;

import java.util.Calendar;

public class BreaksEditFrag extends Fragment implements TimePickerDialog.OnTimeSetListener {

    public BreaksEditFrag(){}

    private Break mBreak;

    private boolean isStartEdit;

    private TextView totalTimeTV;
    private TextView startTV;
    private TextView endTV;
    private Button saveBtn;
    private Button cancelBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.breaks_edit_layout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeEntryEditViewModel viewModel = new ViewModelProvider(getParentFragment()).get(TimeEntryEditViewModel.class);
        mBreak = viewModel.getBreaksList().getValue().get(getArguments().getInt("BREAK_POSITION"));

        totalTimeTV = getActivity().findViewById(R.id.break_edit_pop_name_tv);
        startTV = getActivity().findViewById(R.id.break_edit_pop_start_tv);
        endTV = getActivity().findViewById(R.id.break_edit_pop_end_tv);
        saveBtn = getActivity().findViewById(R.id.break_edit_pop_save_btn);
        cancelBtn = getActivity().findViewById(R.id.break_edit_pop_cancel_btn);

        totalTimeTV.setText(mBreak.getTotalTime());
        startTV.setText(timeFormString(mBreak.getStartTime()));
        if (mBreak.getEndTime() != null) {
            endTV.setText(timeFormString(mBreak.getEndTime()));
        } else {
            endTV.setText(R.string.present);
        }

        startTV.setOnClickListener(view -> {
            DialogFragment timePicker = new TimePickerFrag();
            timePicker.show(getParentFragmentManager(), "time picker");
            isStartEdit = true;
        });

        endTV.setOnClickListener(view -> {
            DialogFragment timePicker = new TimePickerFrag();
            timePicker.show(getParentFragmentManager(), "time_picker");
        });

        saveBtn.setOnClickListener(view -> {
            viewModel.getBreaksList().getValue().set(getArguments().getInt("BREAK_POSITION"), mBreak);
            //TODO: Implement Navigation Component
        });

        cancelBtn.setOnClickListener(view -> {
            //TODO: Implement Navigation Component
        });
    }

    private String timeFormString(@NonNull Calendar current){
        return current.get(Calendar.HOUR) + ":"
                + current.get(Calendar.MINUTE)
                + current.get(Calendar.AM_PM);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(isStartEdit){
            mBreak.getStartTime().set(Calendar.HOUR, hourOfDay);
            mBreak.getStartTime().set(Calendar.MINUTE, minute);
            isStartEdit = false;
        } else {
            if(mBreak.getEndTime() == null){
                Calendar newEnd = (Calendar) mBreak.getStartTime().clone();
                newEnd.set(Calendar.HOUR, hourOfDay);
                newEnd.set(Calendar.MINUTE, minute);
                mBreak.setEndTime(newEnd);
            }else {
                mBreak.getEndTime().set(Calendar.HOUR, hourOfDay);
                mBreak.getEndTime().set(Calendar.MINUTE, minute);
            }
        }
    }
}
