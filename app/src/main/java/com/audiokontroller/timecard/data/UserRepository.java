package com.audiokontroller.timecard.data;

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
    public void update(User user){
        new updateAsyncTask(mUserDao).execute(user);
    }

    public void delete(User user){
        new deleteAsyncTask(mUserDao).execute(user);
    }

    public String getFirstName(){return mUserDao.getFirstName();}

    public String getLastName(){return mUserDao.getLastName();}

    public String getPassword(){
        return mUserDao.getPassword();
    }

    private static class deleteAsyncTask extends AsyncTask<User, Void,Void>{

        private UserDao userDao;
        deleteAsyncTask(UserDao dao){
            userDao = dao;}

            @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<User, Void, Void>{

        private UserDao userDao;

        updateAsyncTask(UserDao dao){userDao = dao;}

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }
}
