package com.audiokontroller.timecard.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.audiokontroller.timecard.data.TimeCardDatabase;

/*
Add functionality for profile images.
 */

@Entity(tableName = "user_data")
public class User {

    private static final String TAG = User.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String username;

    @ColumnInfo
    private String password;

    @ColumnInfo
    private String email;

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

    public User(@NonNull  String mUsername,@NonNull String mPassword,@NonNull String mEmail,
                String mFirstName, String mLastName, String mBirthday, String mCompanyName){

        this.username = mUsername;
        this.firstName = mFirstName;
        this.lastName = mLastName;
        this.birthday = mBirthday;
        this.companyName = mCompanyName;
        this.password = mPassword;
        this.email= mEmail;

        Log.e(TAG, ".uName." + username + ".created");

    }

    public void setID(int newID){
        this.id = newID;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){return password;}

    public String getEmail(){return email;}

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

    public TimeCardDatabase setTimeCardDB(TimeCardDatabase timeCardDatabase){
        this.currentTimeCard = timeCardDatabase;
        return timeCardDatabase;
    }

    public TimeCardDatabase getCurrentTimeCard(){
        return currentTimeCard;
    }
}