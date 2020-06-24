package com.audiokontroller.timecard.data;

import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audiokontroller.timecard.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

//TODO Make sure that when saving user data to firestore for the first time to save the entire user class
//TODO VERY IMPORTANT CHANGE THE FIRESTORE PERMISSIONS IN SECURITY
public class UserDataSourceManager {

    private static final String TAG = UserDataSource.class.getSimpleName();

    private UserDataSource dataSource;
    private FirebaseUser user;
    private FirebaseFirestore db;

    //This should manage all of the updates and initialization of the database 
    public UserDataSourceManager(@NonNull UserDataSource dataSource){
        this.dataSource = dataSource;
        user = dataSource.firebaseUser;
        db = FirebaseFirestore.getInstance();
    }

    public void populateRoom(User userData){
        if(userData != null) {
            Disposable disposable = dataSource.mUserDao.insert(userData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribeWith(new DisposableCompletableObserver() {

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "Room populated with user " + userData.getUserID());
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Log.d(TAG, "There was a problem populating Room with user data provided");
                        }
                    });
            dataSource.isRoomPopulated = true;
        } else {
            Log.e(TAG, "User object passed to UserDataSourceManger.populateRoom() == null.");

        }
    }

    public void populateRoomFromFirebase(){ // this may be deleted
        User localUser = new User(user.getUid(), user.getEmail(), null, null);
        try {
         Map<String, Object> userDataMap = null;//Todo, change from null

            if(userDataMap.size() > 2){
                Log.d(TAG, "UserDataSourceManager.userDataMap = " + userDataMap);
                //TODO Iterate over the map to set values of user to be saved in room

            } else {
                Disposable disposable = dataSource.mUserDao.insert(new User(user.getUid(), user.getEmail(), null, null))
                        .subscribeWith(new DisposableCompletableObserver() {

                            @Override
                            public void onComplete() {
                                Log.d(TAG,"room.insert() call completed for user " + user.getEmail());
                                this.dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();;
                                Log.e(TAG, e.toString());
                            }
                        });
            }
        } catch(NullPointerException e){
            e.printStackTrace();
            Log.e(TAG, "NullPointerException in populateFromFirestore method, adding new user data to Firestore");
        }
    }

    public void popFirestoreWithNewUser(@Nullable User newUser){
        if(newUser == null) {
            newUser = new User(user.getUid(), user.getEmail(), null, null);
        }
        User finalNewUser = newUser;
        db.collection("users").add(newUser)

                .addOnCompleteListener(task -> {//add onFailure
                    populateRoom(finalNewUser);
                })

                .addOnFailureListener(listener->{
                Log.e(TAG, "There was a problem adding user to room");
        });
    }

    private void getDataMapFromFirestore(){//TODO THis should return Map<String, Object>, this method also does'nt need to exist
         db.collection(Collections.USERS)
                .whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(listener ->{
                        Log.d(TAG, "current user data structure" + listener.getResult().getDocuments().get(0).getData().toString());

                });

    }
}
