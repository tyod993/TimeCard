package com.audiokontroller.timecard.data.model;


import android.telecom.Call;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

// TODO: Set up type Converters to utilize object classes for simplification code

@Entity(tableName = "time_card")
public class TimeEntry {

    private final static String TAG = TimeEntry.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "entry_date")
    private Calendar entryDate;

    @NonNull
    @ColumnInfo(name = "entry_start_time")
    private Calendar entryStartTime;

    @Nullable
    @ColumnInfo(name = "entry_end_time")
    private Calendar entryEndTime;

    @Nullable
    @ColumnInfo(name = "tasks")
    private List<Task> tasks;

    @Nullable
    @ColumnInfo(name = "breaks")
    private List<Break> breaks;

    @ColumnInfo(name = "total_hours")
    private double totalHours;

    @Nullable
    @ColumnInfo(name = "job_location")
    private String jobName;

    @Nullable
    @ColumnInfo(name = "job_notes")
    private String jobNotes;

    @ColumnInfo(name = "submitted")
    private boolean submitted;

    @ColumnInfo(name = "active")
    private boolean active;

    public TimeEntry(@NonNull Calendar entryStartTime, @Nullable Calendar entryEndTime,
                     @Nullable String jobName, @Nullable String jobNotes, boolean submitted, boolean active){

        entryDate = Calendar.getInstance();
        this.entryStartTime = entryStartTime;
        this.entryEndTime = entryEndTime;
        this.jobName = jobName;
        this.jobNotes = jobNotes;
        this.submitted = submitted;
        this.active = active;
        Log.d(TAG,"TimeEntry.Created");

    }

    //TODO:Organize me Please!!!

    public void setId(int id1){
        this.id = id1;
    }

    public int getId(){return this.id;}

    public Calendar getEntryDate(){
        return entryDate;
    }

    @NonNull
    public Calendar getEntryStartTime(){
        return entryStartTime;
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
