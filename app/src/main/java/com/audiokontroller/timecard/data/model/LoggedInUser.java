package com.audiokontroller.timecard.data.model;

import androidx.annotation.Nullable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private boolean isAuthenticated;
    private String email;
    @Nullable private String displayName;

    public LoggedInUser(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

    public String getUserEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setAuthenticated(){
        isAuthenticated = true;
    }

    public void unauthenticate(){
        isAuthenticated = false;
    }
}
