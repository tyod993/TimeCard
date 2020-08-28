package com.audiokontroller.timecard.authentication.firebase;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.audiokontroller.timecard.authentication.Result;
import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthHandler{

    private final String TAG = FirebaseAuthHandler.class.getSimpleName();

    public Boolean authSuccess = null;
    private LoggedInUser loggedInUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private Error currentError;
    private MutableLiveData<Result> observableAuthResult = new MutableLiveData<>(); //TODO Register method needs to be fixed removing this dependancy


    public FirebaseAuthHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
    }

    //Im pretty sure this needs to update the observableAuthResult to trigger the Intent in activity
    public void loginWithFirebase(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener -> {
                        if (listener.isComplete() && listener.isSuccessful()) {
                            Log.d(TAG, "signInWithEmailAndPassword.successful");
                            currentUser = listener.getResult().getUser();
                            //observableAuthResult.setValue(new Result.Success<>(currentUser));
                            authSuccess = true;
                        } else {
                            Log.w(TAG, "signInWithEmailAndPassword.failure");
                            currentUser = null;
                            //observableAuthResult.setValue(new Result.Error(new Exception(listener.getException())));
                            authSuccess = false;
                            String word = "this";
                        }
                });
    }

    public void registerNewUser(final String email, final String password) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(authResultTask -> {
                        if(authResultTask.isSuccessful()){
                            authSuccess = true;
                            currentUser = firebaseAuth.getCurrentUser();
                            //observableAuthResult.postValue(new Result.Success<>(firebaseAuth.getCurrentUser()));
                        } else {
                            authSuccess = false;
                            //observableAuthResult.postValue(new Result.Error(new Exception(authResultTask.getException())));
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

    public Boolean getLoginSuccess() {
        return authSuccess;
    }

    public FirebaseUser getFirebaseUser(){return currentUser;}

    public LiveData<Result> getObservableAuthResult(){return observableAuthResult;}
}
