package com.audiokontroller.timecard.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "time_card")
public class TimeEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "submission_date_time")
    private Calendar mDateTimeSubmitted;

    @ColumnInfo(name = "entry_date")
    private String mEntryDate;

    @ColumnInfo(name = "entry_start_time")
    private String mEntryStartTime;

    @ColumnInfo(name = "entry_end_time")
    private String mEntryEndTime;

    @ColumnInfo(name = "job_location")
    private String mJobName;

    @ColumnInfo(name = "job_notes")
    private String mJobNotes;

    @ColumnInfo(name = "submitted")
    private boolean mSubmitted;

    public TimeEntry(String date, String startTime, String endTime, String jobName, String jobNotes){

        this.mEntryDate = date;
        this.mEntryStartTime = startTime;
        this.mEntryEndTime = endTime;
        this.mJobName = jobName;
        this.mJobNotes = jobNotes;
        this.mDateTimeSubmitted = Calendar.getInstance();

    }

    public void setId(int id1){
        this.id = id1;
    }

    public void setmSubmitted(boolean submitted){
        this.mSubmitted = submitted;
    }

    public String getmEntryDate(){
        return mEntryDate;
    }

    public String getmEntryStartTime(){
        return mEntryStartTime;
    }

    public String getmEntryEndTime(){
        return mEntryEndTime;
    }

    public String getmJobName(){
        return mJobName;
    }

    public String getmJobNotes(){
        return mJobNotes;
    }

    public boolean getSubmition(){
        return mSubmitted;
    }

}
