package com.audiokontroller.timecard.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audiokontroller.timecard.data.model.TimeEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeEntryHandler {

    private static final String TAG = TimeEntryHandler.class.getSimpleName();
    private static final int MILLIS_IN_SEC = 1000;
    private static final int SEC_IN_MIN = 60;
    private static final int MIN_IN_HOUR = 60;
    private static final int HOUR_IN_DAY = 24;

    private TimeEntry timeEntry;

    public TimeEntryHandler(@Nullable TimeEntry timeEntry){
        if(timeEntry != null){
            this.timeEntry = timeEntry;
        }
    }

    public TimeEntry clockIn(){
        String current = Calendar.getInstance().getTime().toString();
        return timeEntry = new TimeEntry(current, current, null, null, null, false, true);
    }

    public TimeEntry calcTotalHours(TimeEntry timeEntry){
        if(timeEntry.getTotalHours() == 0 && timeEntry.isActive()){
                Calendar start = formatCalendar(timeEntry.getEntryEndTime());
                Calendar now = Calendar.getInstance();
                long startMillis = start.getTimeInMillis();
                long nowMillis = now.getTimeInMillis();

        }
        return timeEntry;
    }

    private Calendar formatCalendar(@NonNull String v){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        try{cal.setTime(sdf.parse(v));
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        return cal;
    }

    private double millisDiff(long start, long stop){
        long diff = start - stop;

    }
}
