package com.audiokontroller.timecard.ui.mainmenu;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.UserDataSource;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * This class is the SINGLE SOURCE OF TRUTH for this entire application.
 *
 **/

public class MainMenuViewModel extends AndroidViewModel {

    public static final int FIREBASE_DB = 0;
    public static final int ROOM_DB = 1;

    public String userID;
    public int databasePreference;

    public SharedPreferences preferences;

    public ArrayList<TimeCard> loadedTimeCards;
    private UserDataSource userDataSource;
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<User> liveUser = new MutableLiveData<>();

    public User activeUser;

    public MainMenuViewModel(Application application) {
        super(application);
    }

    /*Call this method once you have retrieved userID and databasePreference.
      This function takes dataPreference and userID, and searches for the user data to
      supply the rest of the application. If no local database has been created for this
      user it will create one and populate it with default values.
      maybe this should return live data
     */
    public LiveData<User> retrieveUser(){
        userDataSource = UserDataSource.getInstance(getApplication());
        disposable.add(userDataSource.retrieveUserData(userID, databasePreference)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(user1 -> liveUser.setValue(user1), Throwable::printStackTrace));
        return liveUser;
    }

    public void setUserID(String userID){this.userID = userID;}

    public ArrayList<TimeCard> getLoadedTimeCards(){ return loadedTimeCards;}

    public void clearDisposable(){disposable.clear();}

  public void setPreferences(SharedPreferences preferences){this.preferences = preferences;}
}
