package com.audiokontroller.timecard.data;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;

import com.audiokontroller.timecard.data.model.User;

@Dao
public interface UserDao {

    @Update
    void update(User user);

    @Delete
    void delete(User user);
    @Query("SELECT lastName FROM user_data")
    String getLastName();

    @Query("SELECT firstName FROM user_data")
    String getFirstName();

    @Query("SELECT password FROM user_data")
    String getPassword();

    @Query("SELECT currentTimeCard FROM user_data")
    TimeCardDatabase getCurrentTimeCard();
}
