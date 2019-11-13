package com.audiokontroller.timecard.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public User(@NonNull String password, @NonNull String email,
                String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email= email;

        Log.e(TAG, ".uName." + email + ".created");

    }

    public void setId(int newId){
        this.id = newId;
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

}
