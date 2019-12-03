package com.audiokontroller.timecard.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.audiokontroller.timecard.data.model.User;

@Database(entities = User.class, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static final String TAG = UserDatabase.class.getSimpleName();

    private static final String DATABASE_NAME = "user_db";

    private static volatile UserDatabase instance;

    public abstract UserDao userDao();

    public static synchronized UserDatabase getInstance(final Context context){
        if(instance == null) {
            synchronized (UserDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, DATABASE_NAME)
                        .build();
                Log.e(TAG, ".build.exe");
            }
        }
        return instance;
    }
}
