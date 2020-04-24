package com.audiokontroller.timecard.ui.TimeEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.TimeEntry.TimeEntryHandler;
import com.audiokontroller.timecard.data.model.TimeEntry;

public class TimeEntryReviewFrag extends Fragment {

    private TimeEntryReviewViewModel viewModel;


    //Get rid of the notes section and add an time edit section
    private TextView totalHoursTV;
    private Spinner projectsSpinner;
    private Spinner tasksSpinner;
    private Spinner taskHourSpinner;
    private ImageView addTaskButton;
    private Button submitButton;
    private Button editTimeButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return layoutInflater.inflate(R.layout.time_review_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        totalHoursTV = rootView.findViewById(R.id.review_total_hours_tv);

    }
}
