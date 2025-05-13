package com.example.dayo.data.database;

import android.content.Context;
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

                        Activity activity = new Activity(name, category, description, location, price, duration);
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
}