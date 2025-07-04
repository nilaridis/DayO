package com.example.dayo.data.database;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class DatabaseInitializer {

    private static final String PREFS_NAME = "DatabaseInitializerPrefs";
    private static final String KEY_INITIALIZED = "isDatabaseInitialized";

    public static void populateDatabase(Context context, AppDatabase database) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isInitialized = sharedPreferences.getBoolean(KEY_INITIALIZED, false);

        if (isInitialized) {
            return;
        }

        try {
            InputStreamReader reader = new InputStreamReader(context.getAssets().open("activities.json"));

            Type listType = new TypeToken<List<Activity>>() {}.getType();
            List<Activity> activities = new Gson().fromJson(reader, listType);

            database.activityDao().insertAll(activities);
            sharedPreferences.edit().putBoolean(KEY_INITIALIZED, true).apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}