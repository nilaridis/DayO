package com.example.dayo.data.database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActivityDao {
    @Query("SELECT * FROM activities")
    List<Activity> getAllActivities();

    @Insert
    void insertActivities(List<Activity> activities);

    @Query("DELETE FROM activities")
    void deleteAllActivities();
}