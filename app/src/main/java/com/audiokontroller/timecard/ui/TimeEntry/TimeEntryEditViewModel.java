package com.audiokontroller.timecard.ui.TimeEntry;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.timecard.data.model.Break;
import com.audiokontroller.timecard.data.model.Task;
import com.audiokontroller.timecard.data.model.TimeEntry;

import java.util.List;

public class TimeEntryEditViewModel extends ViewModel {

    private boolean isSubmitted;
    private MutableLiveData<TimeEntry> mTimeEntry = new MutableLiveData<>();
    private MutableLiveData<List<Task>> mTaskList = new MutableLiveData<>();
    private MutableLiveData<List<Break>> mBreaksList = new MutableLiveData<>();

    public TimeEntryEditViewModel(){
    }

    public void setActiveTimeEntry(@NonNull TimeEntry timeEntry){
        mTimeEntry.setValue(timeEntry);
        mTaskList.setValue(timeEntry.getTasks());
        mBreaksList.setValue(timeEntry.getBreaks());
    }

    public MutableLiveData<List<Task>> getTaskList() {
        return mTaskList;
    }

    public MutableLiveData<List<Break>> getBreaksList() {
        return mBreaksList;
    }

    public LiveData<TimeEntry> getTimeEntry(){return mTimeEntry;}

    public void replaceTask(@NonNull Task editedTask, int position){
         mTaskList.getValue().set(position, editedTask);
    }

    public boolean isSubmitted(){return isSubmitted;}
}
