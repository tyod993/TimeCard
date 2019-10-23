package com.audiokontroller.timecard.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private boolean isAuthenticated;
    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
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
