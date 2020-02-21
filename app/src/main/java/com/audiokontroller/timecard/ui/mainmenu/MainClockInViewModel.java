package com.audiokontroller.timecard.ui.mainmenu;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.timecard.data.TimeEntry.TimeEntryHandler;
import com.audiokontroller.timecard.data.model.TimeEntry;

public class MainClockInViewModel extends ViewModel {

    private final String TAG = MainClockInViewModel.class.getSimpleName();

    public Context context;
    private TimeEntryHandler timeFactory;
    private MutableLiveData<TimeEntry> currentTimeEntry = new MutableLiveData<>();

    public MainClockInViewModel(){}

    public LiveData<TimeEntry> getCurrentEntry(){return currentTimeEntry;}

    public void clockIn(){
        timeFactory = new TimeEntryHandler(null);
        currentTimeEntry.setValue(timeFactory.clockIn());
        Log.d(TAG, ".clock.in");
    }

    //Calling clockOut triggers the review process
    public void clockOut(){timeFactory.clockOut(
            currentTimeEntry.getValue());
            startReviewFrag(currentTimeEntry.getValue());
    }

    public void startBreak(){
        currentTimeEntry.setValue(timeFactory.startBreak(currentTimeEntry.getValue()));
    }

    public void endBreak(){
        currentTimeEntry.setValue(timeFactory.endBreak(currentTimeEntry.getValue()));
    }

    public double getTotalHours(){
        currentTimeEntry.setValue(timeFactory.calcTotalHours(currentTimeEntry.getValue()));
        return currentTimeEntry.getValue().getTotalHours();
    }

    //TODO: Initialize the review fragment and pass the current TimeEntry
    private void startReviewFrag(TimeEntry timeEntry){

    }

    public void setContext(Context context){this.context = context;}
}
