package com.example.dayo.data.database

import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites WHERE userId = :userId")
    suspend fun getFavoritesByUserId(userId: Int): List<Favorite>

    @Query("SELECT * FROM favorites WHERE userId = :userId AND activityId = :activityId")
    suspend fun isFavorite(userId: Int, activityId: Int): Favorite?
}