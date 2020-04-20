package com.audiokontroller.timecard.ui.mainmenu;

public class TimeClockFormState {

    private int clockButtonState;
    private boolean onBreak;
    private String totalHours;

    public TimeClockFormState(){
        clockButtonState = 0;
        onBreak = false;
        totalHours = "0";
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
