package com.audiokontroller.timecard.data.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.audiokontroller.timecard.data.model.User;
import com.audiokontroller.timecard.data.room.relation.UserWithTimeCards;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface UserDao {

    @Insert
    Completable insert(User user);

    @Update
    Completable update(User user);

    @Delete
    Completable delete(User user);

    @Query("SELECT * FROM user_data WHERE userID = :ID")
    Flowable<User> getUser(String ID);
}
