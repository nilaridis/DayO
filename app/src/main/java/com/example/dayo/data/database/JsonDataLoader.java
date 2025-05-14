package com.example.dayo.data.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dayo.data.database.Activity;
import com.example.dayo.data.database.ActivityDao;
import com.example.dayo.data.database.AppDatabase;
import com.example.dayo.data.database.DatabaseInstance; // Χρησιμοποίησε τη σωστή κλάση για το instance

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonDataLoader {

    private static final String TAG = "JsonDataLoader";

    public static void loadActivitiesFromJson(Context context) {
        final AppDatabase db = DatabaseInstance.getInstance(context.getApplicationContext()); // Χρησιμοποίησε application context για ασφάλεια
        final ActivityDao activityDao = db.activityDao();

        // Εκτέλεσε τον έλεγχο και την πιθανή φόρτωση σε background thread
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Έλεγχος αν υπάρχουν ήδη δραστηριότητες στη βάση
            if (activityDao.getAllActivities().isEmpty()) {
                Log.d(TAG, "Database is empty. Loading activities from JSON...");
                try {
                    // Διάβασε το JSON αρχείο από τον φάκελο assets
                    InputStream is = context.getAssets().open("activities.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String jsonString = new String(buffer, StandardCharsets.UTF_8);

                    JSONArray jsonArray = new JSONArray(jsonString);
                    List<Activity> activitiesList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject activityJson = jsonArray.getJSONObject(i);

                        String name = activityJson.getString("name");
                        String category = activityJson.getString("category");
                        String description = activityJson.getString("description");
                        String location = activityJson.getString("location");
                        double price = activityJson.getDouble("pricePerPerson");
                        int duration = activityJson.getInt("duration");
                        String imageName = activityJson.optString("imageName","default_image");

                        Activity activity = new Activity(name, category, description, location, price, duration, imageName, false);
                        activitiesList.add(activity);
                    }

                    if (!activitiesList.isEmpty()) {
                        activityDao.insertAll(activitiesList); // Η εκτέλεση γίνεται ήδη σε background thread
                        Log.d(TAG, "Activities loaded and inserted into database. Count: " + activitiesList.size());
                    } else {
                        Log.d(TAG, "JSON file was empty or contained no activities.");
                    }

                } catch (IOException e) {
                    Log.e(TAG, "Error reading JSON file: " + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON data: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "Activities already exist in the database. Skipping JSON load. Count: " + activityDao.getAllActivities().size());
            }
        });
    }
    public static void loadUpcomingActivitiesFromJson(Context context) {
        final AppDatabase db = DatabaseInstance.getInstance(context.getApplicationContext());
        final ActivityDao activityDao = db.activityDao();

        SharedPreferences sharedPreferences = context.getSharedPreferences("DatabaseInitializerPrefs", Context.MODE_PRIVATE);
        boolean isUpcomingInitialized = sharedPreferences.getBoolean("isUpcomingInitialized", false);
        if (isUpcomingInitialized) {
            Log.d(TAG, "Upcoming activities already initialized. Skipping load.");
            return;
        }

        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Check if upcoming activities already exist
            if (activityDao.getUpcomingActivities().isEmpty()) {
                try {
                    InputStream is = context.getAssets().open("upcoming_activities.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String jsonString = new String(buffer, StandardCharsets.UTF_8);

                    JSONArray jsonArray = new JSONArray(jsonString);
                    List<Activity> upcomingList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject activityJson = jsonArray.getJSONObject(i);

                        String name = activityJson.getString("name");
                        String category = activityJson.getString("category");
                        String description = activityJson.getString("description");
                        String location = activityJson.getString("location");
                        double price = activityJson.getDouble("pricePerPerson");
                        int duration = activityJson.getInt("duration");
                        String imageName = activityJson.optString("imageName", "default_image");

                        Activity activity = new Activity(name, category, description, location, price, duration, imageName, true);
                        upcomingList.add(activity);
                    }

                    if (!upcomingList.isEmpty()) {
                        activityDao.insertAll(upcomingList);
                        Log.d(TAG, "Upcoming activities inserted. Count: " + upcomingList.size());
                    } else {
                        Log.d(TAG, "No upcoming activities found in the JSON.");
                    }

                } catch (IOException | JSONException e) {
                    Log.e(TAG, "Error loading upcoming activities: " + e.getMessage(), e);
                }
            } else {
                Log.d(TAG, "Upcoming activities already exist in the database. Skipping JSON load. Count: " + activityDao.getUpcomingActivities().size());
            }
        });
    }

}
