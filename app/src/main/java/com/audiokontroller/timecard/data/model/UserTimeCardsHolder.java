package com.audiokontroller.timecard.data.model;

import androidx.annotation.NonNull;

import java.util.List;

public class UserTimeCardsHolder {

    public String userID;
    private List<TimeCard> timeEntries;

    public UserTimeCardsHolder(String userID, List<TimeCard> timeCardList){
        this.userID = userID;
        this.timeEntries = timeCardList;
    }

    public List<TimeCard> getTimeEntries() {
        return timeEntries;
    }

    public String getUserID() {
        return userID;
    }

    public void setTimeEntries(List<TimeCard> timeEntries) {
        this.timeEntries = timeEntries;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
