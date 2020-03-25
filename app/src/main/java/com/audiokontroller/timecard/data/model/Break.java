package com.audiokontroller.timecard.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class Break {

    private boolean active;

    private Calendar startTime;

    @Nullable
    private Calendar endTime;

    public String totalTime;

    private long totalTimeMillis;

    public Break(@NonNull Calendar startTime, @Nullable Calendar endTime){
        this.startTime = startTime;
        if(endTime != null) {
            this.endTime = endTime;
            totalTimeMillis = startTime.compareTo(endTime);
            setTotalTime();
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
        setTotalTime();
    }

    public void setStartTime(@NonNull Calendar startTime) {
        this.startTime = startTime;
        totalTimeMillis = endTime.compareTo(startTime);
        setTotalTime();
    }

    public String getTotalTime() {
        return totalTime;
    }

    public long getTotalTimeMillis() {
        return totalTimeMillis;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    private void setTotalTime(){
        long total = ((totalTimeMillis/1000)/60);
        if(total/60 >= 1){
            int totalHrs = (int) total/60;
            int totalMin = (int) total%60;
            totalTime = totalHrs + "hrs " + totalMin + "min";
        }else{
            totalTime = total + "min";
        }
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
