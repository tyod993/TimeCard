package com.audiokontroller.timecard.ui.mainmenu;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.audiokontroller.timecard.data.TimeEntry.TimeCardRepository;
import com.audiokontroller.timecard.data.model.TimeEntry;
import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.UserRepository;

import java.util.List;

public class MainMenuViewModel extends AndroidViewModel {

    private TimeCardRepository mtimeEntryRepository;
    private UserRepository muserRepository;

    public User activeUser;

    LiveData<List<TimeEntry>> allTimeEntries;

    public MainMenuViewModel(Application application) {
        super(application);
        mtimeEntryRepository = new TimeCardRepository(application);
        muserRepository = new UserRepository(application);
        allTimeEntries = mtimeEntryRepository.getAllEntries();
    }

    private LiveData<List<TimeEntry>> getAllTimeEntries() {
        return allTimeEntries;
    }


}
