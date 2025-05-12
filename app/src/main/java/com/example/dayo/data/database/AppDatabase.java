package com.example.dayo.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Activity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;

    // Δήλωσε τα DAO interfaces
    public abstract UserDao userDao();
    public abstract ActivityDao activityDao();



}