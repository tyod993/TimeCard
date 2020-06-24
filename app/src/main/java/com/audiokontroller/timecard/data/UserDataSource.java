package com.audiokontroller.timecard.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.audiokontroller.timecard.authentication.firebase.FirebaseAuthHandler;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.room.UserDao;
import com.audiokontroller.timecard.data.room.UserDatabase;
import com.audiokontroller.timecard.ui.mainmenu.MainMenuViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Create an observable of dbPreference
 */

public class UserDataSource {

    private static final String TAG = UserDataSource.class.getSimpleName();

    private static volatile UserDataSource instance;

    private UserDataSourceManager dataSourceManager;

    private FirebaseAuthHandler firebaseAuthHandler;
    public FirebaseUser firebaseUser;

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<User> liveUserData = new MutableLiveData<>();

    public boolean isRoomPopulated = true;

    public UserDao mUserDao;

    public UserDataSource(){
        firebaseAuthHandler = new FirebaseAuthHandler();
        firebaseUser = firebaseAuthHandler.getFirebaseUser();
    }

    public static UserDataSource getInstance(){
        if(instance == null){
            instance = new UserDataSource();
            Log.d(TAG, "New UserDataSource instance created.");
            return instance;
        }else {
            Log.d(TAG, "UserDataSource instance returned");
            return instance;
        }
    }

    //This method holds the majority of the data logic
    public LiveData<User> retrieveUserData(int dbPreference, @NonNull Context context){
        Log.d(TAG, "retrieveUserData() invoked");
        if(firebaseAuthHandler.isUserLoggedIn()) {

            if (liveUserData.getValue() == null) {
                if (dbPreference == MainMenuViewModel.ROOM_DB) {
                    UserDatabase database = UserDatabase.getInstance(context);
                    mUserDao = database.userDao();
                    getDataFromRoom(firebaseUser.getUid());

                } else if (dbPreference == MainMenuViewModel.FIREBASE_DB) {

                    getDataFromFirebase(firebaseUser.getUid());
                    Log.d(TAG, "Retrieving data from Firestore");

                }
            }

                return liveUserData;

            } else return liveUserData;

    }

    //TODO Rewrite all of this for a Single instead of Flowable
    private void getDataFromRoom(@NonNull String userID){
        Log.d(TAG, "Attempting to get data from Room");
        disposable.add(mUserDao.getUser(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        if (user != null) { //This method isn't being called
                            Log.d(TAG, user.toString());
                            liveUserData.postValue(user);
                        } else { //if the Room query returns null get the data from firebase
                            getDataFromFirebase(userID);
                            isRoomPopulated = false;
                            Log.d(TAG, "Room is empty, retrieving data from Firebase");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        isRoomPopulated = false;
                        getDataFromFirebase(userID);
                        Log.d(TAG, "Room is empty and threw error, retrieving data from Firebase");
                    }
                }));
    }



    private void getDataFromFirebase(@NonNull String userID) {
        Log.d(TAG, "Attempting to get data from Firestore");

        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

        firestoreDB.collection("users")
                .whereEqualTo("userID", firebaseUser.getUid())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {// This is still getting called twice

                    if (e != null) {
                        Log.e(TAG, e.toString());
                        //TODO Handle exception
                    }

                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        Map<String, Object> dataMap = document.getData();

                        if (dataMap != null) {
                            liveUserData.postValue(dataMapToUser(dataMap));
                        }

                        if(!isRoomPopulated){
                            if(dataSourceManager == null) {
                                dataSourceManager = new UserDataSourceManager(UserDataSource.this);
                            }
                            if(dataMap != null) {
                                dataSourceManager.populateRoom(dataMapToUser(dataMap));
                            } else {
                                dataSourceManager.popFirestoreWithNewUser(null);
                            }
                        }

                        Log.d(TAG, "liveUserData set.");

                    } else {

                        User user = new User(userID, firebaseUser.getEmail(), null, null);
                        if(dataSourceManager == null) {
                            dataSourceManager = new UserDataSourceManager(UserDataSource.this);
                        }
                            dataSourceManager.popFirestoreWithNewUser(user);

                        liveUserData.postValue(user);
                    }
                });
    }


    public void clearDisposable(){
        disposable.clear();
    }

    //TODO Get userPreferences and TimeEntries
    private User dataMapToUser(@NonNull Map<String, Object> dataMap){
        User newUser = new User((String)dataMap.get("userID"), (String)dataMap.get("email"), (String)dataMap.get("firstName"), (String)dataMap.get("lastName"));
        newUser.setBirthday((String)dataMap.get("birthday"));
        newUser.setCompanyName((String)dataMap.get("companyName"));
        return newUser;
    }
}
