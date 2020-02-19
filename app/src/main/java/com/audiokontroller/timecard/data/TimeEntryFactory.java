package com.audiokontroller.timecard.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audiokontroller.timecard.data.model.TimeEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeEntryFactory {

    private static final String TAG = TimeEntryFactory.class.getSimpleName();

    private static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
    private static final int MILLIS_IN_MIN = 60000;
    private static final int MIN_IN_HOUR = 60;

    private TimeEntry timeEntry;

    public TimeEntryFactory(@Nullable TimeEntry timeEntry){
        if(timeEntry != null){
            this.timeEntry = timeEntry;
        }
    }

    public TimeEntry clockIn(){
        String current = Calendar.getInstance().getTime().toString();
        return timeEntry = new TimeEntry(current, current, null,
                null, null, false, true);
    }

    public TimeEntry clockOut(TimeEntry timeEntry){
        String end = Calendar.getInstance().getTime().toString();
        timeEntry.setEntryEndTime(end);
        return timeEntry;
    }

    public TimeEntry startBreak(TimeEntry timeEntry){
        if(timeEntry.isActive()) {
            String[] breaks = {Calendar.getInstance().getTime().toString(), ""};
            timeEntry.setBreaks(breaks);
        }
        return timeEntry;
    }

    public TimeEntry endBreak(TimeEntry timeEntry){
        if(timeEntry.isActive()) {
            String[] breaks = timeEntry.getBreaks();
            breaks[1]= Calendar.getInstance().getTime().toString();
            timeEntry.setBreaks(breaks);
        }
        return timeEntry;
    }

    public TimeEntry calcTotalHours(@NonNull TimeEntry timeEntry){
        if(timeEntry.isActive()){
                Calendar start = formatCalendar(timeEntry.getEntryStartTime(), DATE_FORMAT);
                Calendar now = Calendar.getInstance();
                long startMillis = start.getTimeInMillis();
                long nowMillis = now.getTimeInMillis();
                timeEntry.setTotalHours(millisDiffHrs(startMillis, nowMillis));
        }else if(!timeEntry.isActive() && timeEntry.getEntryEndTime() != null){
            Calendar start = formatCalendar(timeEntry.getEntryStartTime(), DATE_FORMAT);
            Calendar end = formatCalendar(timeEntry.getEntryEndTime(), DATE_FORMAT);
            long startMillis = start.getTimeInMillis();
            long endMillis = end.getTimeInMillis();
            timeEntry.setTotalHours(millisDiffHrs(startMillis, endMillis));
        }
        return timeEntry;
    }

    public Calendar formatCalendar(@NonNull String v, @NonNull String dateFormat){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        try{cal.setTime(sdf.parse(v));
        }catch (Exception e){
            Log.e(TAG, e.toString());
            cal = null;
        }
        return cal;
    }

    private double millisDiffHrs(long start, long stop){
        long diff = start - stop;//In Millis
        int temp = (int) diff / MILLIS_IN_MIN;//Convert to Min
        return (double) temp % (double) MIN_IN_HOUR;
    }

    public TimeEntry getTimeEntry(){return timeEntry;}
}
