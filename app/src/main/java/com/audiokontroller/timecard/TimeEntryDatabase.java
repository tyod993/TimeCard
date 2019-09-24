package com.audiokontroller.timecard;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TimeEntry.class, version = 1)
public abstract class TimeEntryDatabase extends RoomDatabase {

    private static final String TAG = "TE_DB";

    private static TimeEntryDatabase instance;

    public abstract TimeEntityDao timeEntityDao();

    public static synchronized TimeEntryDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TimeEntryDatabase.class, "time_entry_db")
                    .fallbackToDestructiveMigrationFrom()
                    .build();
            Log.d(TAG, ".build.ex");
        }
        return instance;
    }
    }
