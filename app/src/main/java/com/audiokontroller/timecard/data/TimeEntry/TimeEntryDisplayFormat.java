package com.audiokontroller.timecard.data.TimeEntry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audiokontroller.timecard.data.model.TimeEntry;

import java.util.Calendar;
import java.util.Locale;

public class TimeEntryDisplayFormat extends TimeEntryHandler{

    public TimeEntryDisplayFormat(@NonNull TimeEntry timeEntry){
        super(timeEntry);
    }

    public String getDayNameFull(){
        return getTimeEntry().getEntryStartTime().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }

    public String getSimpleDate(@NonNull String date, @Nullable String dateFormat){
        if(dateFormat == null){
            dateFormat = "MMM dd yyyy";
        }
        return formatCalendar(date, dateFormat).toString();
    }

    public String getTotalHours(String prefix){
        return prefix + getTimeEntry().getTotalHours();
    }

}
