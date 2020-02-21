package com.audiokontroller.timecard.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class Break {

    private Calendar startTime;

    @Nullable
    private Calendar endTime;

    //Represented in Milliseconds
    private int totalTime;

    public Break(@NonNull Calendar startTime, @Nullable Calendar endTime){
        this.startTime = startTime;
        if(endTime != null) {
            this.endTime = endTime;
            totalTime = startTime.compareTo(endTime);
        }
    }

    @Nullable
    public Calendar getEndTime() {
        return endTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setEndTime(@NonNull Calendar endTime) {
        this.endTime = endTime;
        totalTime = endTime.compareTo(startTime);
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
