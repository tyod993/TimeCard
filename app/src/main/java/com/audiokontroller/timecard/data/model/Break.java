package com.audiokontroller.timecard.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Duration;
import java.util.Calendar;

public class Break {

    private Calendar startTime;

    @Nullable
    private Calendar endTime;

    public String totalTime;

    //Represented in Milliseconds
    private long totalTimeMillis;

    public Break(@NonNull Calendar startTime, @Nullable Calendar endTime){
        this.startTime = startTime;
        if(endTime != null) {
            this.endTime = endTime;
            totalTimeMillis = startTime.compareTo(endTime);
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
        totalTimeMillis = endTime.compareTo(startTime);
        Duration duration =
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public long getTotalTimeMillis() {
        return totalTimeMillis;
    }

    public void setTotalTimeMillis(long totalTime) {
        this.totalTimeMillis = totalTime;
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
