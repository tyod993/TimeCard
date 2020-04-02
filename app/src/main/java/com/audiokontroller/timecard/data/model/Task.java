package com.audiokontroller.timecard.data.model;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class Task {

    public final String[] TIME_SELECTIONS = {
            "15min", "30min", "45min", "1hr", "2hrs", "3hrs",
            "4hrs", "5hrs", "6hrs", "7hrs", "8hrs", "9hrs",
            "10hrs", "11hrs", "12hrs", "13hrs", "14hrs"
    };

    private long taskID;

    private long entryID;

    @NonNull
    private String mName;

    private int mHours;

    public Task(@NonNull String name, int hours){
        this.taskID = Calendar.getInstance().getTimeInMillis();
        this.mName = name;
        this.mHours = hours;
    }

    public long getTaskID() {
        return taskID;
    }

    public void setEntryID(long entryID) {
        this.entryID = entryID;
    }

    public long getEntryID() {
        return entryID;
    }

    @NonNull
    public String getName(){return mName;}

    public void setName(@NonNull String newName){mName = newName;}

    @NonNull
    public String getHoursAsString(){ return TIME_SELECTIONS[mHours];}

    public void setHours(int newHours){mHours = newHours;}

}
