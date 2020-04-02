package com.audiokontroller.timecard.data.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.room.relation.UserWithTimeCards;

import java.util.List;

@Dao
public interface UserDao {

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_data")
    User getUser();
}
