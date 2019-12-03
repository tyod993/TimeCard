package com.audiokontroller.timecard.ui.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthHandler {

    private LoggedInUserView loggedInUserView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public FirebaseAuthHandler(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private LoggedInUserView formatFirebaseUser(FirebaseUser firebaseUser){
        loggedInUserView = new LoggedInUserView(firebaseUser.getDisplayName());
        loggedInUserView.setProfilePicture(firebaseUser.getPhotoUrl());
        return loggedInUserView;
    }

    public boolean isUserLoggedIn(){
        currentUser = firebaseAuth.getCurrentUser();
        if(currentUser == null){
            return false;
        }
        return true;
    }
}
