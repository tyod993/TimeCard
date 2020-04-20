package com.audiokontroller.timecard.ui.mainmenu;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.timecard.data.TimeEntry.TimeEntryHandler;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.audiokontroller.timecard.data.model.User;

public class MainClockInViewModel extends ViewModel {

    private final String TAG = MainClockInViewModel.class.getSimpleName();

    public static final int DEFAULT_CLOCK_STATE = 0;
    public static final boolean DEFAULT_BREAK_STATE = false;
    public static final String DEFAULT_TOTAL_HOURS = "0";

    public static final int CLOCKED_IN = 1;
    public static final boolean ON_BREAK = true;

    private TimeEntryHandler timeFactory;
    private MutableLiveData<TimeClockFormState> timeClockFormStateLiveData = new MutableLiveData<>(new TimeClockFormState());
    private MutableLiveData<TimeEntry> currentTimeEntry = new MutableLiveData<>();
    private LiveData<User> userLiveData ;

    public MainClockInViewModel(){}

    public LiveData<TimeEntry> getCurrentEntry(){return currentTimeEntry;}

    public void clockButtonPressed(){
        if(timeClockFormStateLiveData.getValue().getClockButtonState() == DEFAULT_CLOCK_STATE){
            timeClockFormStateLiveData.getValue().setClockButtonState(CLOCKED_IN);
            clockIn();
        } else {
            timeClockFormStateLiveData.getValue().setClockButtonState(DEFAULT_CLOCK_STATE);
            clockOut();
        }
    }

    public void breakButtonPressed(){
        if(timeClockFormStateLiveData.getValue().isOnBreak()){
            timeClockFormStateLiveData.getValue().setOnBreak(DEFAULT_BREAK_STATE);
            endBreak();
        } else {
            timeClockFormStateLiveData.getValue().setOnBreak(ON_BREAK);
            startBreak();
        }
    }

    //TODO left off here, need to wrtie all logic for clock buttons as well as Callback to get timecard
    private void clockIn(){
        timeFactory = new TimeEntryHandler(null);
        currentTimeEntry.setValue(timeFactory.clockIn());
        Log.d(TAG, ".clock.in");
    }

    //Calling clockOut triggers the review process
    private void clockOut(){timeFactory.clockOut(
            currentTimeEntry.getValue());
            //TODO Add Navigation Component
            startReviewFrag(currentTimeEntry.getValue());
    }

    private void startBreak(){
        currentTimeEntry.setValue(timeFactory.startBreak(currentTimeEntry.getValue()));
    }

    private void endBreak(){
        currentTimeEntry.setValue(timeFactory.endBreak(currentTimeEntry.getValue()));
    }

    public String getTotalHours(){
        currentTimeEntry.setValue(timeFactory.calcTotalHours(currentTimeEntry.getValue()));
        return currentTimeEntry.getValue().getTotalHours() + "";
    }

    public LiveData<TimeClockFormState> getClockState(){
        timeClockFormStateLiveData.getValue().setTotalHours(getTotalHours());
        return timeClockFormStateLiveData;
    }

    public void setClockState(TimeClockFormState formState){
        timeClockFormStateLiveData.setValue(formState);
    }

    private void startReviewFrag(TimeEntry timeEntry){
        //TODO: Initialize the review fragment and pass the current TimeEntry via Navigation Component
    }

    public void getUserFromViewModel(@NonNull MainMenuViewModel viewModel){
        userLiveData = viewModel.retrieveUser();
    }
}
