package com.audiokontroller.timecard.ui.mainmenu.TimeEntry;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.timecard.data.model.TimeEntry;

public class TimeEntryEditViewModel extends ViewModel {

    private MutableLiveData<TimeEntry> mTimeEntry;

    public TimeEntryEditViewModel(@NonNull TimeEntry timeEntry){
        mTimeEntry = timeEntry;
    }

    public
}
