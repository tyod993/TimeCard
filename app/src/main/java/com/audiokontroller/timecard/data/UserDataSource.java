package com.audiokontroller.timecard.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.audiokontroller.timecard.authentication.firebase.FirebaseAuthHandler;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.room.UserDao;
import com.audiokontroller.timecard.data.room.UserDatabase;
import com.audiokontroller.timecard.ui.mainmenu.MainMenuViewModel;
import com.google.firebase.auth.FirebaseUser;

import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;

/**
 * Create an observable of dbPreference
 */

public class UserDataSource {

    private static volatile UserDataSource instance;

    private Context context;

    private Flowable<User> reactiveUserData;

    private UserDao mUserDao;

    public UserDataSource(@NonNull Context context){
        this.context = context;
    }

    public static UserDataSource getInstance(@NonNull Context context){
        if(instance == null){
            instance = new UserDataSource(context);
        }
        return instance;
    }

    public Flowable<User> retrieveUserData(@NonNull String userID, int dbPreference){
        //This is going to have problems if the roomDB does'nt exist yet.
        //TODO: Solve the NullPointerException
        if(reactiveUserData == null) {
            if (dbPreference == MainMenuViewModel.ROOM_DB) {
                UserDatabase database = UserDatabase.getInstance(context);
                mUserDao = database.userDao();
                reactiveUserData = mUserDao.getUser(userID);
            } else if (dbPreference == MainMenuViewModel.FIREBASE_DB) {
                getDataFromFirebase(userID);
            }
            return reactiveUserData;
        }else return reactiveUserData;
    }

    public Flowable<User> getUserFromRoom(@NonNull String userID) {
        reactiveUserData = mUserDao.getUser(userID);
        return reactiveUserData;
    }

    private void getDataFromFirebase(@NonNull String userID){
        //all of this is still happening on the main thread!!!!
        //TODO this needs to be set up to query FireStore for the rest of the user data
        FirebaseAuthHandler firebaseAuthHandler = new FirebaseAuthHandler();
        if(firebaseAuthHandler.isUserLoggedIn()) {
           FirebaseUser firebaseUser = firebaseAuthHandler.getFirebaseUser();
           //TODO this is where the firestore query should go.
            User user = new User(userID, firebaseUser.getEmail(), null, null);
            reactiveUserData = Flowable.just(user);
        }
    }
}
