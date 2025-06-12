package com.example.dayo;


import android.app.Application;

import com.example.dayo.data.database.JsonDataLoader;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JsonDataLoader.loadActivitiesFromJson(this);
        JsonDataLoader.loadUpcomingActivitiesFromJson(this);
    }
}