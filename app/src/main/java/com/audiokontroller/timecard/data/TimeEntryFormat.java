package com.audiokontroller.timecard.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audiokontroller.timecard.data.model.TimeEntry;

public class TimeEntryFormat extends TimeEntryFactory{

    public TimeEntryFormat(@NonNull TimeEntry timeEntry){
        super(timeEntry);
    }

    public String getDayNameFull(){
        String day = getTimeEntry().getEntryStartTime().trim().substring(0,2);
        switch (day) {
            case "Mon": day = "Monday";
                break;
            case "Tue": day = "Tuesday";
                break;
            case "Wed": day = "Wednesday";
                break;
            case "Thu": day = "Thursday";
                break;
            case "Fri": day = "Friday";
                break;
            case "Sat": day = "Saturday";
                break;
            case "Sun": day = "Sunday";
                break;
        }
        return day;
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
