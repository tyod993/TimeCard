package com.audiokontroller.timecard.data;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT username FROM user_data")
    String getUsername();

    @Query("SELECT currentTimeCard FROM user_data")
    TimeCardDatabase getCurrentTimeCard();
}
