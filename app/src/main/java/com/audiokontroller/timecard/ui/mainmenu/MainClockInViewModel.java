package com.audiokontroller.timecard.ui.mainmenu;

import android.app.Application;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.audiokontroller.timecard.data.TimeEntry.TimeEntryHandler;
import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.model.UserPref;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

//TODO This class is getting cumbersome. Either clean up with other classes or Organize extensively
public class MainClockInViewModel extends AndroidViewModel {

    private final String TAG = MainClockInViewModel.class.getSimpleName();

    private TimeEntryHandler timeFactory;
    private MutableLiveData<TimeClockFormState> timeClockFormStateLiveData = new MutableLiveData<>(new TimeClockFormState());
    private MutableLiveData<TimeEntry> currentTimeEntry = new MutableLiveData<>();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<Error> currentError = new MutableLiveData<>();

    public MainClockInViewModel(Application application){
        super(application);
    }

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
        } else {
            Error error = new Error("Error while clocking out. Cannot call clockOut on a null object.");
            currentError.postValue(error);
            Log.e(TAG, error.toString());
        }
    }

    private void startBreak(){
        if(currentTimeEntry.getValue() != null) {
            currentTimeEntry.postValue(timeFactory.startBreak(currentTimeEntry.getValue()));
        } else {
            Error error = new Error("Error when trying to clock in. Must instantiate a new TimeEntry before calling startBreak on it.");
            currentError.postValue(error);
            Log.e(TAG, error.toString());
        }
    }

    private void endBreak(){
        if(currentTimeEntry.getValue() != null) {
            currentTimeEntry.postValue(timeFactory.endBreak(currentTimeEntry.getValue()));
        } else {
            Error error = new Error("Error when trying to clock out. Cant call clock out on null object");
            currentError.postValue(error);
            Log.e(TAG, error.toString());
        }
    }

    public void updateLiveEntry(){
        currentTimeEntry.postValue(currentTimeEntry.getValue());
    }

    public String getTotalHours(){
        if(currentTimeEntry.getValue() != null) {
            currentTimeEntry.postValue(timeFactory.calcTotalHours(currentTimeEntry.getValue()));//TODO This is where you left off
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

    public LiveData<Error> getError(){return currentError;}

    public LiveData<TimeEntry> getLiveTimeEntry(){
        return currentTimeEntry;
    }

    public void setUser(@NonNull User newUser){
        userLiveData.setValue(newUser);// change to none asych
        getCurrentTimeEntry();
    }

    //TODO In the future this should be optimized
    private void getCurrentTimeEntry() {
        List<TimeCard> timeCards;
        TimeCard currentCard;
        ArrayList<TimeEntry> timeEntries;
        if (userLiveData.getValue() != null) {
            timeCards = userLiveData.getValue().getTimeCards();
            if(timeCards.size() > 0) {
                currentCard = timeCards.get(timeCards.size() - 1); // array out of bounds
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
                    //Setting the current time entry to a new timeEntry for faster button response
                    //When the user closes the app without pressing the clock in button the Persistence layer
                    //checks to see if the currentTimeEntry is active and saves it if so.
                    if (currentTimeEntry.getValue() == null) {
                        currentTimeEntry.postValue(new TimeEntry(currentCard));
                        currentCard.getEntries().add(currentTimeEntry.getValue());
                    }
                }
            } else {
                currentCard = new TimeCard(userLiveData.getValue().getUserID(), new ArrayList<>());
                userLiveData.getValue().getTimeCardsHolder().getTimeEntries().add(currentCard);
            }
        }
    }


    public ArrayAdapter<String> getSuggestions(int type){
        if(type == UserPref.PROJECT) {
            return new ArrayAdapter<>(
                    getApplication().getApplicationContext(),
                    ArrayAdapter.NO_SELECTION,
                    userLiveData.getValue().getUserPref().getProjectSuggestions());
        } else if(type == UserPref.TASK){
            return new ArrayAdapter<>(
                    getApplication().getApplicationContext(),
                    ArrayAdapter.NO_SELECTION,
                    userLiveData.getValue().getUserPref().getTaskSuggestions());
        } else {
            //TODO this should eventually be changed to suggest typical auto suggestions
            return new ArrayAdapter<>(
                    getApplication().getApplicationContext(),
                    ArrayAdapter.NO_SELECTION,
                    new ArrayList<>()
                    );
        }
    }

    //This is called by the onTimeSet() method in the TimeEntryReviewFrag
    public void entryTimeChange(int hourOfDay, int minute, boolean isStartTime){
        if(currentTimeEntry.getValue() != null) {
            if (isStartTime) {
                currentTimeEntry.getValue().getEntryStartTime().set(Calendar.HOUR_OF_DAY, hourOfDay);
                currentTimeEntry.getValue().getEntryStartTime().set(Calendar.MINUTE, minute);
                currentTimeEntry.getValue().calcTotalHours();
                updateLiveEntry();
            } else {
                currentTimeEntry.getValue().getEntryEndTime().set(Calendar.HOUR_OF_DAY, hourOfDay);
                currentTimeEntry.getValue().getEntryEndTime().set(Calendar.MINUTE, minute);
                currentTimeEntry.getValue().calcTotalHours();
                updateLiveEntry();
            }
        } else {
            Error error = new Error("Cannot change time, currentTimeEntry = null");
            currentError.postValue(error);
            Log.e(TAG, error.toString());
        }
    }


    public void submitEntry(){
        if(userLiveData.getValue() != null) {
            int currentIndex = 0;
            int timeCardIndex = userLiveData.getValue().getTimeCardsHolder().getTimeEntries().size() - 1;
            TimeCard currentTimeCard = userLiveData.getValue().getTimeCardsHolder().getTimeEntries()
                    .get(timeCardIndex);
            ArrayList<TimeEntry> timeCards = currentTimeCard.getEntries();
            if(timeCards.size() > 0) {
                for (TimeEntry entry : timeCards) { //Search the existing time cards for the active timecard to update.
                    if (entry.isActive() && entry.getId() == Objects.requireNonNull(currentTimeEntry.getValue()).getId()) {
                        break;
                    }
                    currentIndex++;
                }
                userLiveData.getValue().getTimeCardsHolder().getTimeEntries()
                        .get(timeCardIndex).getEntries().add(currentIndex, currentTimeEntry.getValue());
            }else {
                userLiveData.getValue().getTimeCardsHolder().getTimeEntries()
                        .get(timeCardIndex).getEntries().add(currentTimeEntry.getValue());
            }
            currentTimeEntry.postValue(null);
        } else {
            Log.e(TAG, "There was a problem adding new TimeEntry to TImeCard because current user == null;");
        }
    }
}
