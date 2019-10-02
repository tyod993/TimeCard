package com.audiokontroller.timecard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainMenuViewModel extends AndroidViewModel {

    private TimeEntryRepository mtimeEntryRepository;

    private LiveData<List<TimeEntry>> allTimeEntries;

    public MainMenuViewModel(Application application){
        super(application);
        mtimeEntryRepository = new TimeEntryRepository(application);
        allTimeEntries = mtimeEntryRepository.getAllEntries();
    }

    LiveData<List<TimeEntry>> getAllTimeEntries(){return allTimeEntries;}

    public void insertTimeEntry(TimeEntry timeEntry){mtimeEntryRepository.insert(timeEntry);}

    public void deleteTimeEntry(TimeEntry timeEntry){mtimeEntryRepository.delete(timeEntry);}

    public void updateTimeEntry(TimeEntry timeEntry){mtimeEntryRepository.update(timeEntry);}

    public void deleteAllTimeEntries(){mtimeEntryRepository.deleteAll();}
}
