package com.audiokontroller.timecard.ui.mainmenu.TimeEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.audiokontroller.timecard.data.model.TimeEntry;

public class TimeEntryEditFragment extends Fragment {

    private final TimeEntry timeEntry;
    private final TimeEntryEditViewModel viewModel;

    public TimeEntryEditFragment(@NonNull TimeEntry timeEntry){
        this.timeEntry = timeEntry;
        viewModel = new TimeEntryEditViewModel(this.timeEntry);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
