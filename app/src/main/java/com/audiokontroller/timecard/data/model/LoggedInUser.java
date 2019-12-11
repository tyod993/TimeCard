package com.audiokontroller.timecard.data.model;

import android.net.Uri;

import androidx.annotation.Nullable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private boolean isAuthenticated;
    private String email;
    @Nullable private String displayName;
    @Nullable private Uri profilePicture;

    public LoggedInUser(String email, @Nullable String displayName, @Nullable Uri profilePicture) {
        this.email = email;
        this.displayName = displayName;
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    @Nullable
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    public Uri getProfilePicture(){return profilePicture;}

    public void setAuthenticated(){
        isAuthenticated = true;
    }

    public void unauthenticate(){
        isAuthenticated = false;
    }
}
