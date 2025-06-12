package com.example.dayo.data.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public class DatabaseInstance {

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "dayo_database")
                    .fallbackToDestructiveMigration()
                    .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
                    .build();
        }
        return instance;
    }
}