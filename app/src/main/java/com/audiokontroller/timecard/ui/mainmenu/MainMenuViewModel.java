package com.audiokontroller.timecard.ui.mainmenu;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.audiokontroller.timecard.data.room.TimeCardRepository;
import com.audiokontroller.timecard.data.model.TimeCard;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.room.UserRepository;

import java.util.ArrayList;

public class MainMenuViewModel extends AndroidViewModel {

    public ArrayList<TimeCard> loadedTimeCards;
    private TimeCardRepository mtimeEntryRepository;
    private UserRepository muserRepository;

    public User activeUser;

    public MainMenuViewModel(Application application) {
        super(application);
        mtimeEntryRepository = new TimeCardRepository(application);
        muserRepository = new UserRepository(application);
    }

    public ArrayList<TimeCard> getLoadedTimeCards(){ return loadedTimeCards;}

}
