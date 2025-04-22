package com.example.dayo.data.database

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE :category IN (preferences)")
    suspend fun getUsersByCategory(category: Category): List<User>

    @Delete
    suspend fun deleteUser(user: User)
}