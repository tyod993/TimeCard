package com.audiokontroller.timecard.data.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "time_card")
public class TimeEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "entry_date")
    private String entryDate;

    @NonNull
    @ColumnInfo(name = "entry_start_time")
    private String entryStartTime;

    @Nullable
    @ColumnInfo(name = "entry_end_time")
    private String entryEndTime;

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

    public TimeEntry(String entryDate, @NonNull String entryStartTime, @Nullable String entryEndTime,
                     @Nullable String jobName, @Nullable String jobNotes, boolean submitted, boolean active){

        this.entryDate = entryDate;
        this.entryStartTime = entryStartTime;
        this.entryEndTime = entryEndTime;
        this.jobName = jobName;
        this.jobNotes = jobNotes;
        this.submitted = submitted;
        this.active = active;

    }

    public void setId(int id1){
        this.id = id1;
    }

    public int getId(){return this.id;}

    public void setSubmitted(boolean submitted){
        this.submitted = submitted;
    }

    public String getEntryDate(){
        return entryDate;
    }

    @NonNull
    public String getEntryStartTime(){
        return entryStartTime;
    }

    @Nullable
    public String getEntryEndTime(){
        return entryEndTime;
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

    public void setTotalHours(double total){totalHours = total;}

    public double getTotalHours(){return totalHours;}

    public boolean isActive(){return active;}
}
