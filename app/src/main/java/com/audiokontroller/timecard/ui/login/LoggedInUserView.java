package com.audiokontroller.timecard.ui.login;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.net.URL;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;

    @Nullable
    private Uri profilePicture;
    //... other data fields that may be accessible to the UI

    public LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }

    void setDisplayName(String newDisplayName){this.displayName = newDisplayName;}

    Uri getProfilePicture(){return profilePicture;}

    void setProfilePicture(Uri newProfilePicture){this.profilePicture = newProfilePicture;}

}
