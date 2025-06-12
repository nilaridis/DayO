package com.example.dayo.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import java.util.Random;

@Dao
public interface ActivityDao {

    @Insert
    void insertAll(List<Activity> activities);

    @Insert
    void insert(Activity activity);

    @Query("SELECT * FROM activities WHERE is_upcoming = 0")
    List<Activity> getAllActivities();

    @Query("SELECT * FROM activities WHERE category = :category")
    List<Activity> getActivitiesByCategory(String category);

    @Query("SELECT * FROM activities WHERE is_upcoming = 1")
    List<Activity> getUpcomingActivities();

    @Query("SELECT * FROM activities WHERE id = :id LIMIT 1")
    Activity getActivityById(int id);

}