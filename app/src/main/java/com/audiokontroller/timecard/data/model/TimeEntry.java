package com.audiokontroller.timecard.data.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "time_card")
public class TimeEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "entry_date")
    private String entryDate;

    @ColumnInfo(name = "entry_start_time")
    private String entryStartTime;

    @ColumnInfo(name = "entry_end_time")
    private String entryEndTime;

    @ColumnInfo(name = "job_location")
    private String jobName;

    @ColumnInfo(name = "job_notes")
    private String jobNotes;

    @ColumnInfo(name = "submitted")
    private boolean submitted;

    public TimeEntry(String entryDate, String entryStartTime, String entryEndTime,
                     String jobName, String jobNotes, boolean submitted){

        this.entryDate = entryDate;
        this.entryStartTime = entryStartTime;
        this.entryEndTime = entryEndTime;
        this.jobName = jobName;
        this.jobNotes = jobNotes;
        this.submitted = submitted;

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

    public String getEntryStartTime(){
        return entryStartTime;
    }

    public String getEntryEndTime(){
        return entryEndTime;
    }

    public String getJobName(){
        return jobName;
    }

    public String getJobNotes(){
        return jobNotes;
    }

    public boolean getSubmitted(){
        return submitted;
    }

}
