package com.audiokontroller.timecard.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.audiokontroller.timecard.data.TimeCardDatabase;

/*
 <-- TODO: Add functionality for profile images. -->
 */

@Entity(tableName = "user_data")
public class User {

    private static final String TAG = User.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    @Nullable
    @ColumnInfo
    private TimeCardDatabase currentTimeCard;

    public User(@NonNull String password, @NonNull String email,
                String firstName, String lastName, @Nullable TimeCardDatabase currentTimeCard){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email= email;
        this.currentTimeCard = currentTimeCard;

        Log.e(TAG, ".uName." + email + ".created");

    }

    public void setID(int newID){
        this.id = newID;
    }

    public int getId(){return this.id;}

    public void setCompanyName(String companyName){this.companyName = companyName;}

    public String getCompanyName(){
        return companyName;
    }

    public void setBirthday(String birthday){this.birthday = birthday;}

    public String getBirthday(){return birthday; }

    public void setPassword(String newPassword){ this.password = newPassword; }

    public String getPassword(){return password;}

    public void setEmail(String email){this.email = email;}

    public String getEmail(){return email;}

    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){this.lastName = lastName;}

    public String getLastName(){
        return lastName;
    }

    public TimeCardDatabase setCurrentTimeCard(TimeCardDatabase currentTimeCard){
        this.currentTimeCard = currentTimeCard;
        return currentTimeCard;
    }

    public TimeCardDatabase getCurrentTimeCard(){
        return currentTimeCard;
    }
}
