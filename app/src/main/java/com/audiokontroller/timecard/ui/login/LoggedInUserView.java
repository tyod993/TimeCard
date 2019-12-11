package com.audiokontroller.timecard.ui.login;

import android.net.Uri;

import androidx.annotation.Nullable;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {

    private boolean isAuthenticated;
    private String displayName;

    @Nullable
    private Uri profilePicture;

    public LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }

    void setDisplayName(String newDisplayName){this.displayName = newDisplayName;}

    Uri getProfilePicture(){return profilePicture;}

    void setProfilePicture(Uri newProfilePicture){this.profilePicture = newProfilePicture;}

    void setAuthenticated(){isAuthenticated = true;}

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    void unauthenticate(){isAuthenticated = false;}
}

