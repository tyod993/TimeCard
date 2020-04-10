package com.audiokontroller.timecard.authentication;

import android.content.Context;

import com.audiokontroller.timecard.data.UserDataSource;

public class UserAuthentication {

    public UserDataSource userRepo;

    private boolean isAuthenticated;

    private String testUsername;
    private String testPassword;

    public UserAuthentication(Context context, String username, String password){
        userRepo = new UserDataSource(context);
        testUsername = userRepo.getEmail();

        if(validLocalUser(testUsername)){
            if(testUsername.trim() == username.trim()){
                testPassword= userRepo.getPassword();
                if(testPassword == password) {
                    isAuthenticated = true;
                }
            }
        }
    }
    private boolean validLocalUser(String username){
        if(username == null || username == ""){
         return false;
        }
        return true;
    }

    public String getDisplayName(){
        String displayName = userRepo.getFirstName().trim() + " " + userRepo.getLastName().trim();
        return displayName;
    }

    public boolean getAuthentication(){
        return isAuthenticated;
    }
}
