package com.audiokontroller.timecard.ui.mainmenu.TimeEntry;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.timecard.data.TimeEntry.TimeEntryHandler;
import com.audiokontroller.timecard.data.model.TimeEntry;

public class TimeEntryReviewViewModel extends ViewModel {

    @NonNull
    private TimeEntry timeEntry;
    @NonNull
    private TimeEntryHandler timeEntryHandler;

    public TimeEntryReviewViewModel(@NonNull TimeEntry timeEntry, @NonNull TimeEntryHandler timeEntryHandler){
        this.timeEntry = timeEntry;
        this.timeEntryHandler = timeEntryHandler;
    }

    public void submitTimeEntry(TimeEntry timeEntry){

    }


}
