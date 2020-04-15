package com.audiokontroller.timecard.authentication.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.audiokontroller.timecard.authentication.Result;
import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthHandler{

    private final String TAG = FirebaseAuthHandler.class.getSimpleName();

    private boolean authSuccess;
    private LoggedInUser loggedInUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private MutableLiveData<Result> observableAuthResult = new MutableLiveData<>();

    public FirebaseAuthHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    //TODO Use the same observableAuthResult to observer the AsychTask Call of signInWithEmailAndPassword
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

    public void registerNewUser(final String email, final String password) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(authResultTask -> {
                        if(authResultTask.isSuccessful()){
                            observableAuthResult.setValue(new Result.Success<>(firebaseAuth.getCurrentUser()));
                        } else {
                            observableAuthResult.setValue(new Result.Error(new Exception(authResultTask.getException())));
                        }
                    });

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

    public LiveData<Result> getObservableAuthResult(){return observableAuthResult;}
}
