package com.audiokontroller.timecard.ui.mainmenu;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.audiokontroller.timecard.data.room.TimeCardRepository;
import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.room.UserRepository;

import java.util.ArrayList;

public class MainMenuViewModel extends AndroidViewModel {

    public String userID;
    public ArrayList<TimeCard> loadedTimeCards;
    private UserRepository userRepository;

    public User activeUser;

    public MainMenuViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void setUserID(String userID){this.userID = userID;}

    public ArrayList<TimeCard> getLoadedTimeCards(){ return loadedTimeCards;}

}
