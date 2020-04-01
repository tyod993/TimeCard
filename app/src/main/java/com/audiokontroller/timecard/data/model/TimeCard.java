package com.audiokontroller.timecard.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeCard {

    public final long USER_ID;
    public final long CARD_ID = Calendar.getInstance().getTimeInMillis();

    private boolean isActive;
    private Calendar periodStart;
    private Calendar periodEnd;
    private ArrayList<TimeEntry> entries;

    public TimeCard(long userID, @NonNull ArrayList<TimeEntry> timeEntryArrayList) {
        this.USER_ID = userID;
        this.entries = timeEntryArrayList;
    }

    public long getUSER_ID() {
        return USER_ID;
    }

    public long getCARD_ID() { return CARD_ID; }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }

    public ArrayList<TimeEntry> getEntries() {
        return entries;
    }

    public Calendar getPeriodEnd() {
        return periodEnd;
    }

    public Calendar getPeriodStart() {
        return periodStart;
    }

    public void setEntries(ArrayList<TimeEntry> entries) {
        this.entries = entries;
    }

    public void setPeriodEnd(Calendar periodEnd) {
        this.periodEnd = periodEnd;
    }

    public void setPeriodStart(Calendar periodStart) {
        this.periodStart = periodStart;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
