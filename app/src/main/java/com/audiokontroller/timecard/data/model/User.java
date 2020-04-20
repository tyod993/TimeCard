package com.audiokontroller.timecard.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Call;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;


@Entity(tableName = "user_data")
public class User implements Serializable {

    private static final String TAG = User.class.getSimpleName();

    @NonNull
    @PrimaryKey
    private String userID;

    @ColumnInfo
    private boolean userAuthenticated;

    @ColumnInfo
    private String email;

    //Need to be removed after refactoring
    @ColumnInfo
    private String firstName;
    //Need to be removed after refactoring
    @ColumnInfo
    private String lastName;

    @ColumnInfo
    private String birthday;

    @ColumnInfo
    private String companyName;

    @ColumnInfo
    private UserTimeCardsHolder timeCardsHolder;

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

    public String getUserID(){return this.userID;}

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
        return timeCardsHolder.getTimeEntries();
    }

    public void setTimeCards(List<TimeCard> timeCards) {
        timeCardsHolder.setTimeEntries(timeCards);
    }

    public UserTimeCardsHolder getTimeCardsHolder(){
        return timeCardsHolder;
    }

    public void setTimeCardsHolder(UserTimeCardsHolder holder){
        this.timeCardsHolder = holder;
    }


}
