package com.audiokontroller.timecard.data.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Executor;

public class FirebaseAuthHandler {

    private final String TAG = FirebaseAuthHandler.class.getSimpleName();

    private boolean authSuccess;
    private LoggedInUser loggedInUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public FirebaseAuthHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginWithFirebase(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
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

    public void registerNewUser(final String email, final String password, @Nullable final String firstName, @Nullable final String lastName) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, ".registerNewUser.successful");
                            currentUser = firebaseAuth.getCurrentUser();
                            if (firstName != null) {
                                updateUserDisplayName(firstName + " " + lastName);
                                authSuccess = true;
                            }
                        } else {
                            Log.w(TAG, ".registerNewUser.failure");
                            currentUser = null;
                            authSuccess = false;
                        }
                    }
                });

    }

    private void updateUserDisplayName(String newDisplayName) {
        if (newDisplayName != null) {
            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newDisplayName)
                    .build();
            currentUser.updateProfile(request)
                    .addOnCompleteListener((Executor) this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, ".displayNameChange.success");
                            }
                        }
                    });
        }
    }

    private void formatFirebaseUser(FirebaseUser firebaseUser) {
        loggedInUser = new LoggedInUser(firebaseUser.getEmail(), firebaseUser.getDisplayName(), firebaseUser.getPhotoUrl());
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
