package com.audiokontroller.timecard.ui.mainmenu;

public class TimeClockFormState {

    public static final int DEFAULT_CLOCK_STATE = 0;
    public static final boolean DEFAULT_BREAK_STATE = false;
    public static final String DEFAULT_TOTAL_HOURS = "0";

    public static final int CLOCKED_IN = 1;
    public static final boolean ON_BREAK = true;

    private int clockButtonState;
    private boolean onBreak;
    private String totalHours;

    public TimeClockFormState(){
        clockButtonState = DEFAULT_CLOCK_STATE;
        onBreak = DEFAULT_BREAK_STATE;
        totalHours = DEFAULT_TOTAL_HOURS;
    }

    public TimeClockFormState(int clockButtonState, boolean onBreak, String totalHours){
        this.clockButtonState = clockButtonState;
        this.onBreak = onBreak;
        this.totalHours = totalHours;
    }

    public int getClockButtonState() {
        return clockButtonState;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public boolean isOnBreak() {
        return onBreak;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public void setOnBreak(boolean onBreak) {
        this.onBreak = onBreak;
    }

    public void setClockButtonState(int clockButtonState) {
        this.clockButtonState = clockButtonState;
    }


}
