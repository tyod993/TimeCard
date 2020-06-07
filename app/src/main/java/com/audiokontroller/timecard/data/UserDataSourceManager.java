package com.audiokontroller.timecard.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.audiokontroller.timecard.data.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;

//TODO Make sure that when saving user data to firestore for the first time to save the entire user class
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

    public void populateRoomFromFirebase(){
        User localUser = new User(user.getUid(), user.getEmail(), null, null);
        try {
            //THis is obviousy the problem.
            Map<String, Object> userDataMap =
            db.collection(Collections.USERS)
                    .whereEqualTo("userID", user.getUid())
                    .get()
                    .addOnCompleteListener(listener ->{
                        if(listener.getResult() != null){
                            listener.getResult().getDocuments().get(0).getData();
                        }
                    });


            if(userDataMap.get().size() > 2){
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
            popFirestoreWithNewUser();
        }
    }

    public void popFirestoreWithNewUser(){
        db.collection("users").add(new User(user.getUid(), user.getEmail(), null, null))
                .addOnCompleteListener(task -> {
                    populateRoomFromFirebase();
                });
    }
}
