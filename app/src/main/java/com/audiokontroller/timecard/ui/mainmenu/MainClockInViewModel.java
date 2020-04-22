package com.audiokontroller.timecard.ui.mainmenu;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.timecard.data.TimeEntry.TimeEntryHandler;
import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.audiokontroller.timecard.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainClockInViewModel extends ViewModel {

    private final String TAG = MainClockInViewModel.class.getSimpleName();

    private TimeEntryHandler timeFactory;
    private MutableLiveData<TimeClockFormState> timeClockFormStateLiveData = new MutableLiveData<>(new TimeClockFormState());
    private MutableLiveData<TimeEntry> currentTimeEntry = new MutableLiveData<>();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<Error> currentError = new MutableLiveData<>();

    public MainClockInViewModel(){}

    public LiveData<TimeEntry> getCurrentEntry(){return currentTimeEntry;}

    public void clockButtonPressed(){
        if(timeClockFormStateLiveData.getValue() == null) {
            timeClockFormStateLiveData.postValue(new TimeClockFormState());
        }

        if(Objects.requireNonNull(timeClockFormStateLiveData.getValue()).getClockButtonState() == TimeClockFormState.DEFAULT_CLOCK_STATE){
            timeClockFormStateLiveData.getValue().setClockButtonState(TimeClockFormState.CLOCKED_IN);
            timeClockFormStateLiveData.postValue(timeClockFormStateLiveData.getValue());
            clockIn();
        } else {
            timeClockFormStateLiveData.getValue().setClockButtonState(TimeClockFormState.DEFAULT_CLOCK_STATE);
            timeClockFormStateLiveData.postValue(timeClockFormStateLiveData.getValue());
            clockOut();
        }
    }

    public void breakButtonPressed(){
        if(Objects.requireNonNull(timeClockFormStateLiveData.getValue()).isOnBreak()){
            timeClockFormStateLiveData.getValue().setOnBreak(TimeClockFormState.DEFAULT_BREAK_STATE);
            timeClockFormStateLiveData.postValue(timeClockFormStateLiveData.getValue());
            endBreak();
        } else {
            timeClockFormStateLiveData.getValue().setOnBreak(TimeClockFormState.ON_BREAK);
            timeClockFormStateLiveData.postValue(timeClockFormStateLiveData.getValue());
            startBreak();
        }
    }

    //TODO left off here, need to wrtie all logic for clock buttons as well as Callback to get timecard
    private void clockIn(){
        timeFactory = new TimeEntryHandler(null);
        //Calling timeFactory.clockIn() creates a new TimeEntry and returns it clocked in and active
        currentTimeEntry.postValue(timeFactory.clockIn());
        Log.d(TAG, ".clock.in");
    }

    //Calling clockOut triggers the review process
    private void clockOut(){
        if(currentTimeEntry.getValue() != null) {
            currentTimeEntry.postValue(timeFactory.clockOut(currentTimeEntry.getValue()));
            //TODO Add Navigation Component
            startReviewFrag(currentTimeEntry.getValue());
        } else {
            Error error = new Error("Error while clocking out. Cannot call clockOut on a null object.");
            currentError.postValue(error);
        }
    }

    private void startBreak(){
        if(currentTimeEntry.getValue() != null) {
            currentTimeEntry.postValue(timeFactory.startBreak(currentTimeEntry.getValue()));
        } else {
            Error error = new Error("Error when trying to clock in. Must instantiate a new TimeEntry before calling startBreak on it.");
            currentError.postValue(error);
        }
    }

    private void endBreak(){
        if(currentTimeEntry.getValue() != null) {
            currentTimeEntry.postValue(timeFactory.endBreak(currentTimeEntry.getValue()));
        } else {
            Error error = new Error("Error when trying to clock out. Cant call clock out on null object");
            currentError.postValue(error);
        }
    }

    public String getTotalHours(){
        if(currentTimeEntry.getValue() != null) {
            currentTimeEntry.postValue(timeFactory.calcTotalHours(currentTimeEntry.getValue()));
            return currentTimeEntry.getValue().getTotalHours() + "";
        } else {
            return TimeClockFormState.DEFAULT_TOTAL_HOURS;
        }

    }

    public LiveData<TimeClockFormState> getClockState(){
        if(timeClockFormStateLiveData.getValue() != null) {
            timeClockFormStateLiveData.getValue().setTotalHours(getTotalHours());
            timeClockFormStateLiveData.postValue(timeClockFormStateLiveData.getValue());
        } else {
            timeClockFormStateLiveData.postValue(new TimeClockFormState());
        }
        return timeClockFormStateLiveData;
    }

    public void setClockState(TimeClockFormState formState){
        timeClockFormStateLiveData.postValue(formState);
    }

    private void startReviewFrag(TimeEntry timeEntry){
        //This should be in the fragment class
        //TODO: Initialize the review fragment and pass the current TimeEntry via Navigation Component
    }

    public void setUser(@NonNull User newUser){
        userLiveData.postValue(newUser);
        getCurrentTimeEntry();
    }

    //This should be launched as a seperate Asynch task
    //I see a potential problem here if the user leaves on the ReviewEntryFrag and comes back.
    //Solution bay be to set Preference

    private void getCurrentTimeEntry() {
        List<TimeCard> timeCards;
        TimeCard currentCard;
        ArrayList<TimeEntry> timeEntries;
        if (userLiveData.getValue() != null) {
            timeCards = userLiveData.getValue().getTimeCards();
            currentCard = timeCards.get(timeCards.size() - 1);
            timeEntries = currentCard.getEntries();

            if (Objects.requireNonNull(timeClockFormStateLiveData.getValue()).getClockButtonState() != TimeClockFormState.DEFAULT_CLOCK_STATE) {
                TimeEntry tempEntry = timeEntries.get(timeEntries.size() - 1);

                if (tempEntry.isActive()) {
                    currentTimeEntry.postValue(tempEntry);

                }
            } else {
                for (TimeEntry entry : timeEntries) {
                    if (entry.isActive()) {
                        currentTimeEntry.postValue(entry);
                    }
                }
                //If the above for loop doesnt find an active TimeEntry currentTimeEntry should be null.
                if (currentTimeEntry.getValue() == null) {
                    currentTimeEntry.postValue(new TimeEntry(currentCard));
                    currentCard.getEntries().add(currentTimeEntry.getValue());
                }
            }
        }
    }
}
