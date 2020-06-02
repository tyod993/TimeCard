package com.audiokontroller.timecard.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.audiokontroller.timecard.authentication.firebase.FirebaseAuthHandler;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.room.UserDao;
import com.audiokontroller.timecard.data.room.UserDatabase;
import com.audiokontroller.timecard.ui.mainmenu.MainMenuViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;

/**
 * Create an observable of dbPreference
 */

public class UserDataSource {

    private static final String TAG = UserDataSource.class.getSimpleName();

    private static volatile UserDataSource instance;
    private UserDataSourceManager dataSourceManager;

    private FirebaseAuthHandler firebaseAuthHandler;
    public FirebaseUser firebaseUser;

    private Context context;

    private Flowable<User> reactiveUserData;

    public UserDao mUserDao;

    public UserDataSource(@NonNull Context context){
        this.context = context;
        firebaseAuthHandler = new FirebaseAuthHandler();
        firebaseUser = firebaseAuthHandler.getFirebaseUser();
    }

    public static UserDataSource getInstance(@NonNull Context context){
        if(instance == null){
            instance = new UserDataSource(context);
        }
        return instance;
    }

    //This method holds the majority of the data logic
    public Flowable<User> retrieveUserData(@NonNull String userID, int dbPreference){
        //This is going to have problems if the roomDB does'nt exist yet.
        //TODO: Solve the NullPointerException
        if(reactiveUserData == null) {
            if (dbPreference == MainMenuViewModel.ROOM_DB) {
                UserDatabase database = UserDatabase.getInstance(context);
                mUserDao = database.userDao();
                reactiveUserData = mUserDao.getUser(userID);
                //if the Room query returns null get the data from firebase
                if(reactiveUserData.isEmpty().blockingGet()){
                    dataSourceManager = new UserDataSourceManager(UserDataSource.this);
                    dataSourceManager.populateRoomFromFirebase();
                    getDataFromFirebase(userID);
                    Log.d(TAG, "Room is empty, retrieving data from Firebase");
                }
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
        if(firebaseAuthHandler.isUserLoggedIn()) {
            /*
           FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
            User user = new User(userID, firebaseUser.getEmail(), null, null);
            firestoreDB.collection("users")
                    .whereEqualTo("user_ID", firebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(listener -> {
                        QuerySnapshot querySnapshot = listener.getResult();
                        if(querySnapshot != null) {
                            querySnapshot.getDocuments();
                        }
            });

             */
           //TODO this is where the firestore query should go.
            //This is just here to make the app work for the moment. All of the user data here should be
            //retrieved from Firestore in this method.
            User user = new User(firebaseUser.getUid(), firebaseUser.getEmail(), null, null);
            reactiveUserData = Flowable.just(user);
        }
    }
}
