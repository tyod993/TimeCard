package com.audiokontroller.timecard.data.model;

import androidx.annotation.NonNull;


public class Task {

    @NonNull
    private String mName;
    @NonNull
    private String mHours;

    public Task(@NonNull String name, @NonNull String hours){
        this.mName = name;
        this.mHours = hours;
    }

    @NonNull
    public String getmName(){return mName;}

    public void setmName(@NonNull String newName){mName = newName;}

    @NonNull
    public String getmHours(){ return mHours;}

    public void setmHours(@NonNull String newHours){mHours = newHours;}

}
