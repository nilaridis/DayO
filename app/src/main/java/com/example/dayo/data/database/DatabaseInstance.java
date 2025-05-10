package com.example.dayo.data.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public class DatabaseInstance {

    private static AppDatabase instance;

    // Μέθοδος για να επιστρέφει το instance της βάσης δεδομένων
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            // Δημιουργία της βάσης δεδομένων με Room
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "dayo_database")
                    .fallbackToDestructiveMigration() // Χρήση destructive migration αν αλλάξει η schema
                    .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING) // Ενεργοποίηση Logging
                    .build();
        }
        return instance;
    }
}