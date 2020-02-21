package com.audiokontroller.timecard.data.TimeEntry;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audiokontroller.timecard.data.model.Break;
import com.audiokontroller.timecard.data.model.TimeEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TimeEntryHandler {

    private static final String TAG = TimeEntryHandler.class.getSimpleName();

    private static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
    private static final int MILLIS_IN_MIN = 60000;
    private static final int MIN_IN_HOUR = 60;

    private TimeEntry timeEntry;

    public TimeEntryHandler(@Nullable TimeEntry timeEntry) {
        if (timeEntry != null) {
            this.timeEntry = timeEntry;
        }
    }

    public TimeEntry clockIn() {
        return timeEntry = new TimeEntry(Calendar.getInstance(), null,
                null, null, false, true);
    }

    public TimeEntry clockOut(TimeEntry timeEntry) {
        timeEntry.setEntryEndTime(Calendar.getInstance());
        return timeEntry;
    }

    public TimeEntry startBreak(TimeEntry timeEntry) {
        if (timeEntry.isActive()) {
            Break newBreak = new Break(Calendar.getInstance(), null);
            timeEntry.addBreak(newBreak);
        }
        return timeEntry;
    }

    public TimeEntry endBreak(TimeEntry timeEntry) {
        if (timeEntry.isActive() && timeEntry.getBreaks() != null) {
            List<Break> breaks = timeEntry.getBreaks();
            Break mBreak = breaks.get(breaks.size() - 1);
            mBreak.setEndTime(Calendar.getInstance());
        }
        return timeEntry;
    }

    public TimeEntry calcTotalHours(@NonNull TimeEntry timeEntry) {
        if (timeEntry.isActive()) {
            Calendar start = timeEntry.getEntryStartTime();
            Calendar now = Calendar.getInstance();
            long startMillis = start.getTimeInMillis();
            long nowMillis = now.getTimeInMillis();
            timeEntry.setTotalHours(millisDiffHrs(startMillis, nowMillis));
        } else if (!timeEntry.isActive() && timeEntry.getEntryEndTime() != null) {
            Calendar start = timeEntry.getEntryStartTime();
            Calendar end = timeEntry.getEntryEndTime();
            long startMillis = start.getTimeInMillis();
            long endMillis = end.getTimeInMillis();
            timeEntry.setTotalHours(millisDiffHrs(startMillis, endMillis));
        }
        return timeEntry;
    }

    public Calendar formatCalendar(@NonNull String v, @NonNull String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        try {
            cal.setTime(sdf.parse(v));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            cal = null;
        }
        return cal;
    }

    private double millisDiffHrs(long start, long stop) {
        long diff = start - stop;//In Millis
        int temp = (int) diff / MILLIS_IN_MIN;//Convert to Min
        return (double) temp % (double) MIN_IN_HOUR;
    }

    public TimeEntry getTimeEntry() {
        return timeEntry;
    }
}
