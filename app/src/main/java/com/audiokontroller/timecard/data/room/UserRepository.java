package com.audiokontroller.timecard.data.room;

import android.content.Context;
import android.os.AsyncTask;

import com.audiokontroller.timecard.data.model.User;

public class UserRepository {

    private static volatile UserRepository instance;

    private UserDao mUserDao;

    public UserRepository(Context context){
        getUserDB(context); }

    public UserRepository getInstance(Context context){
        if(instance == null){
            instance = new UserRepository(context);
        }
        return instance;
    }

    private void getUserDB(Context context){
        UserDatabase database = UserDatabase.getInstance(context);
        mUserDao = database.userDao();
    }



}
