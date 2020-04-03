/*
ONLY IMPORT DATA THROUGH THIS CLASS!!!!
This class should never be visible by the UI thread.

This class first checks the user login info against the local db.
If login match then the local db holding the data will be used to populate
the UI. If the login does'nt match. Connect to the server to get the data.
 */


package com.audiokontroller.timecard.data.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.audiokontroller.timecard.data.model.TimeEntry;

import java.util.List;

public class TimeCardRepository {

    //This is set to the Dao of the local database if its being called.
    private static volatile TimeCardRepository instance;
    private TimeCardDao mtimeCardDao;

    //holds the data for the weeks Times
    public LiveData<List<TimeEntry>> allTimeEntries;
    public LiveData<TimeEntry> activeEntry;

    public TimeCardRepository(Context context){
        getTimeCardInfo(context);
    }

    public TimeCardRepository getInstance(Context context){
        if(instance == null){
            instance = new TimeCardRepository(context);
        }
        return instance;
    }

    //This is used by the view model to retrieve this time period's times
    public LiveData<List<TimeEntry>> getAllEntries(){
        return allTimeEntries;
    }

    public LiveData<TimeEntry> getActiveEntry(){return activeEntry;}

    /*
    TODO:This needs to check local memory for database or retrieve it from GCP
    */



    private void getTimeCardInfo(Context context){
        TimeCardDatabase database = TimeCardDatabase.getInstance(context);
        mtimeCardDao = database.timeCardDao();
        allTimeEntries = mtimeCardDao.getAllEntries();
    }

    public void insert(TimeEntry timeEntry){
        new insertAsyncTask(mtimeCardDao).execute(timeEntry);
    }

    public void delete(TimeEntry timeEntry){
        new deleteAsyncTask(mtimeCardDao).execute(timeEntry);
    }

    public void update(TimeEntry timeEntry){
        new updateAsyncTask(mtimeCardDao).execute(timeEntry);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(mtimeCardDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<TimeEntry, Void, Void>{

        private TimeCardDao timeCardDao;

        insertAsyncTask(TimeCardDao dao){
         timeCardDao = dao;
        }

        @Override
        protected Void doInBackground(TimeEntry... timeEntries) {
            timeCardDao.insert(timeEntries[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<TimeEntry, Void, Void>{

        private TimeCardDao timeCardDao;

        deleteAsyncTask(TimeCardDao dao){
            timeCardDao = dao;
        }

        @Override
        protected Void doInBackground(TimeEntry... timeEntries) {
            timeCardDao.delete(timeEntries[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<TimeEntry, Void, Void>{

        private TimeCardDao timeCardDao;

        updateAsyncTask(TimeCardDao dao){
            timeCardDao = dao;
        }


        @Override
        protected Void doInBackground(TimeEntry... timeEntries) {
            timeCardDao.update(timeEntries[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private TimeCardDao timeCardDao;

        deleteAllAsyncTask(TimeCardDao dao){
            timeCardDao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            timeCardDao.deleteAllEntries();
            return null;
        }
    }

}