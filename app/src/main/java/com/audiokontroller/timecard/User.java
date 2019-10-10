package com.audiokontroller.timecard;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_data")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo
    private String username;

    @ColumnInfo
    private String firstName;

    @ColumnInfo
    private String lastName;

    @ColumnInfo
    private String birthday;

    @ColumnInfo
    private String companyName;

    @ColumnInfo
    private TimeCardDatabase currentTimeCard;

    public User(String mUsername, String mFirstName, String mLastName, String mBirthday, String mCompanyName){

        this.username = mUsername;
        this.firstName = mFirstName;
        this.lastName = mLastName;
        this.birthday = mBirthday;
        this.companyName = mCompanyName;

    }

    public void setID(int newID){
        this.uid = newID;
    }

    public String getUsername(){
        return username;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getBirthday(){
        return birthday;
    }

    public String getCompanyName(){
        return companyName;
    }

    public TimeCardDatabase getCurrentTimeCard(){
        return currentTimeCard;
    }
}
