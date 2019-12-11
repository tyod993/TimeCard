package com.audiokontroller.timecard.data.firebase;

import com.audiokontroller.timecard.data.firebase.FirebaseAuthHandler;
import com.google.firebase.auth.FirebaseUser;

public class FireUserProfileUpdate {

    private final FirebaseAuthHandler firebaseAuthHandler;
    private final FirebaseUser currentUser;

    public FireUserProfileUpdate(FirebaseAuthHandler firebaseAuthHandler){
        this.firebaseAuthHandler = firebaseAuthHandler;
        currentUser = this.firebaseAuthHandler.getFirebaseUser();
    }

    public
}
