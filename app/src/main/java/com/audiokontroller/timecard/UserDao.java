package com.audiokontroller.timecard;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Update
    void update(User user);

    @Delete
    void delete(User user);

}
