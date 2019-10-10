/*
ONLY IMPORT DATA THROUGH THIS CLASS!!!!
This class should never be visible by the UI thread.

This class first checks the user login info against the local db.
If login match then the local db holding the data will be used to populate
the UI. If the login does'nt match. Connect to the server to get the data.
 */


package com.audiokontroller.timecard;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MainMenuRepository {

    //This is set to the Dao of the local database if its being called.
    private TimeEntityDao mtimeEntryDao;

    //holds the data for the weeks Times
    public LiveData<List<TimeEntry>> allTimeEntries;

    public MainMenuRepository(Context context){
        TimeCardDatabase database = TimeCardDatabase.getInstance(context);
        mtimeEntryDao = database.timeEntityDao();
        allTimeEntries = mtimeEntryDao.getAllEntries();
    }

    //This is used by the view model to retrieve this time period's times
    LiveData<List<TimeEntry>> getAllEntries(){
        return allTimeEntries;
    }

    public void insert(TimeEntry timeEntry){
        new insertAsyncTask(mtimeEntryDao).execute(timeEntry);
    }

    public void delete(TimeEntry timeEntry){
        new deleteAsyncTask(mtimeEntryDao).execute(timeEntry);
    }

    public void update(TimeEntry timeEntry){
        new updateAsyncTask(mtimeEntryDao).execute(timeEntry);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(mtimeEntryDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<TimeEntry, Void, Void>{

        private TimeEntityDao timeEntityDao;

        insertAsyncTask(TimeEntityDao dao){
         timeEntityDao = dao;
        }

        @Override
        protected Void doInBackground(TimeEntry... timeEntries) {
            timeEntityDao.insert(timeEntries[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<TimeEntry, Void, Void>{

        private TimeEntityDao timeEntityDao;

        deleteAsyncTask(TimeEntityDao dao){
            timeEntityDao = dao;
        }

        @Override
        protected Void doInBackground(TimeEntry... timeEntries) {
            timeEntityDao.delete(timeEntries[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<TimeEntry, Void, Void>{

        private TimeEntityDao timeEntityDao;

        updateAsyncTask(TimeEntityDao dao){
            timeEntityDao = dao;
        }


        @Override
        protected Void doInBackground(TimeEntry... timeEntries) {
            timeEntityDao.update(timeEntries[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private TimeEntityDao timeEntityDao;

        deleteAllAsyncTask(TimeEntityDao dao){
            timeEntityDao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            timeEntityDao.deleteAllEntries();
            return null;
        }
    }

}
