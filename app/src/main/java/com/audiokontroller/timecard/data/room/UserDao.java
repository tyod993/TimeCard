package com.audiokontroller.timecard.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import androidx.room.Update;

import com.audiokontroller.timecard.data.model.User;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert
    Completable insert(User user);

    @Update
    Completable update(User user);

    @Delete
    Completable delete(User user);

    @Query("SELECT * FROM user_data WHERE userID = :ID")
    Single<User> getUser(String ID);
}
