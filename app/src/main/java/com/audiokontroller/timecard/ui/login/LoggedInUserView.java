package com.audiokontroller.timecard.ui.login;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    @Nullable
    private String companyName;
    @Nullable
    private Bitmap profilePicture;
    //... other data fields that may be accessible to the UI

    public LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}
