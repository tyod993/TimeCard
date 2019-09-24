package com.audiokontroller.timecard;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeEntityDao {

    @Insert
    void insert(TimeEntry timeEntry);

    @Delete
    void delete(TimeEntry timeEntry);

    @Update
    void update(TimeEntry timeEntry);

    @Query("DELETE FROM time_entries")
    void deleteAllEntries();

    @Query("SELECT * FROM time_entries ORDER BY id ASC")
    LiveData<List<TimeEntry>> getAllEntries();

}
