package com.audiokontroller.timecard.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.audiokontroller.timecard.data.TimeCardRepository;
import com.audiokontroller.timecard.data.TimeEntry;
import com.audiokontroller.timecard.data.User;
import com.audiokontroller.timecard.data.UserRepository;

import java.util.List;

public class MainMenuViewModel extends AndroidViewModel {

    private TimeCardRepository mtimeEntryRepository;
    private UserRepository muserRepository;

    public User activeUser;

    LiveData<List<TimeEntry>> allTimeEntries;

    public MainMenuViewModel(Application application){
        super(application);
        mtimeEntryRepository = new TimeCardRepository(application);
        muserRepository = new UserRepository(application);
        allTimeEntries = mtimeEntryRepository.getAllEntries();
    }

    private LiveData<List<TimeEntry>> getAllTimeEntries(){return allTimeEntries;}



    public void updateUser(User user){muserRepository.update(user);}

    public void deleteUser(User user){muserRepository.delete(user);}



    public void insertTimeEntry(TimeEntry timeEntry){mtimeEntryRepository.insert(timeEntry);}

    public void deleteTimeEntry(TimeEntry timeEntry){mtimeEntryRepository.delete(timeEntry);}

    public void updateTimeEntry(TimeEntry timeEntry){mtimeEntryRepository.update(timeEntry);}

    public void deleteAllTimeEntries(){mtimeEntryRepository.deleteAll();}
}
