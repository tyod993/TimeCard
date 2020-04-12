package com.audiokontroller.timecard.ui.mainmenu;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.UserDataSource;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainMenuViewModel extends AndroidViewModel {

    public String userID;
    public String databasePreference;
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
                .subscribe(user1 -> liveUser.setValue(user1)));
        return liveUser;
    }

    public void setUserID(String userID){this.userID = userID;}

    public ArrayList<TimeCard> getLoadedTimeCards(){ return loadedTimeCards;}

    public void setDatabasePreference(@NonNull String preferance){
        this.databasePreference = preferance;
    }

    public void clearDisposable(){disposable.clear();}

}
