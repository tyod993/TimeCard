/*
This Database should only be used for saving TimeCard entries temporarily.
When the data has been sent to the server this DataBase is deleted.
Use this database as a data holder populated onCreate() to be sent to the UI.
The Table Format:
___________________________________________________________________________________________
|id|submission_date_time|entry_date|entry_start_time|entry_end_time|job_location|job_notes|
|int|     Calender      |String    |String          |String        |String      |String   |
-------------------------------------------------------------------------------------------

To Do:
1.Implement Version control
 */


package com.audiokontroller.timecard.data.TimeEntry;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.audiokontroller.timecard.data.model.TimeEntry;

@Database(entities = TimeEntry.class, version = 1)
public abstract class TimeCardDatabase extends RoomDatabase {

    private static final String TAG = TimeCardDatabase.class.getSimpleName();

    // Create an instance of the database to check against for temporary insertions.
    private static TimeCardDatabase instance;

    //This DAO is accessed in the Repository class.
    public abstract TimeCardDao timeCardDao();

    //Called in the Repository class to retrieve the db or create one if null.
    public static synchronized TimeCardDatabase getInstance(Context context){
        if(instance == null){
            //If the db does'nt exist create one
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TimeCardDatabase.class, "time_card_db")
                    .fallbackToDestructiveMigrationFrom()
                    .build();
            Log.d(TAG, ".build.ex");
        }
        return instance;
    }
    }
