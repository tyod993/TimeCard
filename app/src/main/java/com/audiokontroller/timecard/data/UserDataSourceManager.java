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

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

//TODO Make sure that when saving user data to firestore for the first time to save the entire user class
//TODO VERY IMPORTANT CHANGE THE FIRESTORE PERMISSIONS IN SECURITY
public class UserDataSourceManager extends UserDataSource{

    private static final String TAG = UserDataSource.class.getSimpleName();

    private static volatile UserDataSource dataSource;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CompositeDisposable allDisposables = new CompositeDisposable();

    //This should manage all of the updates and initialization of the database 
    public UserDataSourceManager(@NonNull UserDataSource dataSource){
        UserDataSourceManager.dataSource = dataSource;
        user = dataSource.firebaseUser;
        db = FirebaseFirestore.getInstance();
    }

    public void populateRoom(User userData){
        if(userData != null) {
            allDisposables.add(dataSource.mUserDao.insert(userData)
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
                    }));
            dataSource.isRoomPopulated = true;
        } else {
            Log.e(TAG, "User object passed to UserDataSourceManger.populateRoom() == null.");

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

    public void updateRoom(@NonNull User user){
        allDisposables.add(dataSource.mUserDao.update(user)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onComplete() {
                        //TODO Implement incrementing of DB version
                        Log.d(TAG, "Updated room successfully.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "There was an error when updating Room.");
                    }
                })

        );
    }

    //This method is used by WorkManger to update Firebase once a day
    public void updateFirebase(@NonNull User user, String docID) {
        User updatedUser = user;
        db.collection("users")
                .whereEqualTo("userID", user.getUserID())
                .addSnapshotListener((listener, e) -> {
                    db.collection("users").document(listener.getDocuments().get(0).getId())
                .set(user); //TODO This should change to use the .update() method in the future

                    //TODO Manage Document version

                });
    }

    public void clearDisposables(){
        allDisposables.dispose();
    }
}
