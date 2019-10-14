package com.audiokontroller.timecard.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeCardDao {

    @Insert
    void insert(TimeEntry timeEntry);

    @Delete
    void delete(TimeEntry timeEntry);

    @Update
    void update(TimeEntry timeEntry);

    @Query("DELETE FROM time_card")
    void deleteAllEntries();

    @Query("SELECT * FROM time_card ORDER BY id ASC")
    LiveData<List<TimeEntry>> getAllEntries();

}
