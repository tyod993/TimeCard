package com.audiokontroller.timecard.data.model;

public class TimeCardForm {

    final int timeCardID;

    String startDate;

    String endDate;

    public TimeCardForm(int id, String startDate, String endDate){
        this.timeCardID = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
