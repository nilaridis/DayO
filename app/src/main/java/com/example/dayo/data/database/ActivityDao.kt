package com.example.dayo.data.database

import androidx.room.*

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(activities: List<Activity>)

    @Query("SELECT * FROM activities WHERE id = :activityId")
    suspend fun getActivityById(activityId: Int): Activity?

    @Query("SELECT * FROM activities WHERE category = :category")
    suspend fun getActivitiesByCategory(category: Category): List<Activity>

    @Query("SELECT * FROM activities")
    suspend fun getAllActivities(): List<Activity>

    @Delete
    suspend fun deleteActivity(activity: Activity)
}