package com.audiokontroller.timecard.ui.mainmenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.TimeEntryHandler;
import com.audiokontroller.timecard.data.model.TimeEntry;

public class TimeEntryReviewFrag extends Fragment {

    private TimeEntryReviewViewModel viewModel;

    TimeEntryReviewFrag(TimeEntry timeEntry, TimeEntryHandler timeEntryHandler){
        viewModel = new TimeEntryReviewViewModel(timeEntry, timeEntryHandler);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return layoutInflater.inflate(R.layout.time_review_frag, container);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
