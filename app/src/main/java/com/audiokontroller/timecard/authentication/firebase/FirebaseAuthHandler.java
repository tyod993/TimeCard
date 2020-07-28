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
        currentUser = firebaseAuth.getCurrentUser();
    }

    //Im pretty sure this needs to update the observableAuthResult to trigger the Intent in activity
    public void loginWithFirebase(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmailAndPassword.successful");
                            currentUser = firebaseAuth.getCurrentUser();
                            observableAuthResult.postValue(new Result.Success<>(firebaseAuth.getCurrentUser()));
                            authSuccess = true;
                        } else {
                            Log.w(TAG, "signInWithEmailAndPassword.failure");
                            currentUser = null;
                            observableAuthResult.postValue(new Result.Error(new Exception(task.getException())));
                            authSuccess = false;
                        }
                    }
                });
    }

    public void registerNewUser(final String email, final String password) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(authResultTask -> {
                        if(authResultTask.isSuccessful()){
                            observableAuthResult.postValue(new Result.Success<>(firebaseAuth.getCurrentUser()));
                        } else {
                            observableAuthResult.postValue(new Result.Error(new Exception(authResultTask.getException())));
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
        return currentUser != null;
    }

    public boolean getLoginSuccess() {
        return authSuccess;
    }

    public FirebaseUser getFirebaseUser(){return currentUser;}

    public LiveData<Result> getObservableAuthResult(){return observableAuthResult;}
}
