package com.audiokontroller.timecard.data.model;

import android.telecom.Call;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.List;

@Entity(tableName = "user_data")
public class User {

    private static final String TAG = User.class.getSimpleName();

    @PrimaryKey
    private String userID;

    @ColumnInfo
    private boolean userAuthenticated;

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
    private List<TimeCard> timeCards;

    public User(@NonNull String userID, @NonNull String email,
                String firstName, String lastName){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email= email;

        Log.e(TAG, ".uName." + email + ".created");

    }

    public void setId(String newId){
        this.userID = newId;
    }

    public String getId(){return this.userID;}

    public void setUserAuthenticated(boolean auth){userAuthenticated = auth;}

    public boolean isUserAuthenticated(){return userAuthenticated;}

    public void setCompanyName(String companyName){this.companyName = companyName;}

    public String getCompanyName(){
        return companyName;
    }

    public void setBirthday(String birthday){this.birthday = birthday;}

    public String getBirthday(){return birthday; }

    public void setEmail(String email){this.email = email;}

    public String getEmail(){return email;}

    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){this.lastName = lastName;}

    public String getLastName(){ return lastName; }

    public List<TimeCard> getTimeCards() {
        return timeCards;
    }

    public void setTimeCards(List<TimeCard> timeCards) {
        this.timeCards = timeCards;
    }
}
