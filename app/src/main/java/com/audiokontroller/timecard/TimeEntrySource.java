package com.audiokontroller.timecard;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TimeEntrySource {

    private TimeEntityDao mtimeEntryDao;

    public LiveData<List<TimeEntry>> allTimeEntries;

    public TimeEntrySource(Context context){
        TimeEntryDatabase database = TimeEntryDatabase.getInstance(context);
        mtimeEntryDao = database.timeEntityDao();
        allTimeEntries = mtimeEntryDao.getAllEntries();
    }
}
