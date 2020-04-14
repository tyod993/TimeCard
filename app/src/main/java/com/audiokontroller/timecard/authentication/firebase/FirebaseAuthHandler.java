package com.audiokontroller.timecard.authentication.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audiokontroller.timecard.authentication.Result;
import com.audiokontroller.timecard.data.firebase.FireUserProfileUpdate;
import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthHandler {

    private final String TAG = FirebaseAuthHandler.class.getSimpleName();

    private boolean authSuccess;
    private LoggedInUser loggedInUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public FirebaseAuthHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    //TODO this should also be refactored to return a Result
    public void loginWithFirebase(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmailAndPassword.successful");
                            authSuccess = true;
                        } else {
                            Log.w(TAG, "signInWithEmailAndPassword.failure");
                            currentUser = null;
                            authSuccess = false;
                        }
                    }
                });
    }

    public Result registerNewUser(final String email, final String password, @Nullable final String firstName, @Nullable final String lastName) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, ".registerNewUser.successful");
                            currentUser = firebaseAuth.getCurrentUser();
                            if (firstName != null) {
                                FireUserProfileUpdate userUpdate = new FireUserProfileUpdate(currentUser);
                                userUpdate.updateUserDisplayName(firstName + " " + lastName);
                                authSuccess = true;
                            }
                        } else {
                            Log.e(TAG, ".registerNewUser.failure");
                            currentUser = null;
                            authSuccess = false;
                        }
                    }
                });
        if(authSuccess){
            return new Result.Success<>(currentUser);
        } else{
            return new Result.Error(new Exception("Error occurred while attempting to register new user with Firebase"));
        }
    }



    private void formatFirebaseUser(FirebaseUser firebaseUser) {
        loggedInUser = new LoggedInUser(firebaseUser.getUid() ,firebaseUser.getEmail(), firebaseUser.getDisplayName(), firebaseUser.getPhotoUrl());
    }

    public LoggedInUser getLoggedInUser() {
        formatFirebaseUser(currentUser);
        return loggedInUser;
    }

    public boolean isUserLoggedIn() {
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return true;
    }

    public boolean getLoginSuccess() {
        return authSuccess;
    }

    public FirebaseUser getFirebaseUser(){return currentUser;}
}
