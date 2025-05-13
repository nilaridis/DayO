package com.example.dayo; // Το βασικό package της εφαρμογής σου


import android.app.Application;

import com.example.dayo.data.database.JsonDataLoader;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Κάλεσε εδώ τη μέθοδο για τη φόρτωση των δεδομένων από το JSON.
        // Το 'this' εδώ αναφέρεται στο Application context.
        JsonDataLoader.loadActivitiesFromJson(this);
    }
}