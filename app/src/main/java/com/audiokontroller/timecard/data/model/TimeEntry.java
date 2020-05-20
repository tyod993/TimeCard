package com.audiokontroller.timecard.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// TODO: Set up type Converters to utilize object classes for simplification code
// TODO: Set up TimeCardID

public class TimeEntry implements Serializable {

    private final static String TAG = TimeEntry.class.getSimpleName();

    private long entryID;

    private long timeCardID;

    private Calendar entryDate;

    @NonNull
    private Calendar entryStartTime;

    @Nullable
    private Calendar entryEndTime;

    @Nullable
    private List<Task> tasks;

    @Nullable
    private List<Break> breaks;

    private double totalHours;

    @Nullable
    private String jobName;

    @Nullable
    private String jobNotes;

    private boolean submitted;

    private boolean active;

    public TimeEntry(@NonNull Calendar entryStartTime, @Nullable Calendar entryEndTime,
                     @Nullable String jobName, @Nullable String jobNotes, boolean submitted, boolean active){

        this.entryID = Calendar.getInstance().getTimeInMillis();
        entryDate = Calendar.getInstance();
        this.entryStartTime = entryStartTime;
        this.entryEndTime = entryEndTime;
        this.jobName = jobName;
        this.jobNotes = jobNotes;
        this.submitted = submitted;
        this.active = active;
        Log.d(TAG,"TimeEntry.Created");

    }

    //This constructor should only be used in the mainClockInFragment when clocking in;
    public TimeEntry(@NonNull TimeCard parentCard){
        this.active = true;
        this.entryID = Calendar.getInstance().getTimeInMillis();
        this.timeCardID = parentCard.getCardID();
        this.entryDate = Calendar.getInstance();
        this.entryStartTime = Calendar.getInstance();

    }

    //TODO:Organize me Please!!!

    public void setId(int id1){
        this.entryID = id1;
    }

    public long getId(){return this.entryID;}

    public long getTimeCardID() {
        return timeCardID;
    }

    public void setTimeCardID(int timeCardID) {
        this.timeCardID = timeCardID;
    }

    public Calendar getEntryDate(){
        return entryDate;
    }

    @NonNull
    public Calendar getEntryStartTime(){
        return entryStartTime;
    }

    public void setEntryStartTime(@NonNull Calendar newStartTime){
        this.entryStartTime = newStartTime;
    }

    @Nullable
    public Calendar getEntryEndTime(){ return entryEndTime; }

    public void setEntryEndTime(Calendar endTime){this.entryEndTime = endTime;}

    @Nullable
    public List<Break> getBreaks(){return breaks;}

    public void setBreaks(@NonNull List<Break> breaks){
        this.breaks = breaks;
    }

    public void addBreak(Break newBreak){
        if(breaks != null) {
            breaks.add(newBreak);
        } else {
            breaks = new LinkedList<>();
            breaks.add(newBreak);
        }
    }

    @Nullable
    public String getJobName(){
        return jobName;
    }

    @Nullable
    public String getJobNotes(){
        return jobNotes;
    }

    public boolean getSubmitted(){
        return submitted;
    }

    public void setSubmitted(boolean submitted){ this.submitted = submitted; }

    public void setTotalHours(double total){totalHours = total;}

    public double getTotalHours(){return totalHours;}

    public void calcTotalHours(){
        if(entryEndTime != null){
            //Get the difference in Milliseconds
            double dif = entryEndTime.getTimeInMillis() - entryStartTime.getTimeInMillis();
            //Calculate the difference in hours
            totalHours = dif / 3600000.0;
            Log.d(TAG, "dif value = " + dif);
            Log.d(TAG, "totalHours value = " + totalHours);
        }
    }

    @Nullable
    public List<Task> getTasks(){return tasks;}

    public void setTasks(@Nullable List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addNewTask(Task newTask){
        if(tasks != null) {
            tasks.add(newTask);
        }
        else{
            tasks = new LinkedList<>();
            tasks.add(newTask);
        }
    }

    public boolean isActive(){return active;}
}
