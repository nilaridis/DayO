package com.example.dayo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class], version = 1)
@TypeConverters(CategoryConverter::class) // Προσθήκη μετατροπέων
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}