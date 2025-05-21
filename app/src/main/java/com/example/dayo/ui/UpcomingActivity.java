package com.example.dayo.ui;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dayo.data.database.Activity;
import com.example.dayo.R;
import com.example.dayo.data.database.DatabaseInstance;
import com.example.dayo.data.database.AppDatabase;
import com.example.dayo.data.database.ActivityDao;

import java.util.ArrayList;
import java.util.List;

public class UpcomingActivity extends BaseActivity {

    private RecyclerView recyclerViewActivities;
    private ActivityAdapter activityAdapter;
    private List<Activity> activitiesList = new ArrayList<>(); // Initialize to avoid NullPointerException

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        setupHeaderAndNavbar();

        recyclerViewActivities = findViewById(R.id.recyclerViewUpcoming);
        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list initially
        activityAdapter = new ActivityAdapter(this, activitiesList);
        recyclerViewActivities.setAdapter(activityAdapter);

        // Ensure this is only called once
        if (activitiesList.isEmpty()) {
            loadActivitiesFromDb();  // Load upcoming activities
        }
    }

    private void loadActivitiesFromDb() {
        AppDatabase db = DatabaseInstance.getInstance(getApplicationContext()); // Use the DatabaseInstance
        ActivityDao activityDao = db.activityDao();

        // Execute data fetching in a background thread
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Fetch upcoming activities
            List<Activity> fetchedActivities = activityDao.getUpcomingActivities();

            // Update the UI on the main thread
            runOnUiThread(() -> {
                if (fetchedActivities != null && !fetchedActivities.isEmpty()) {
                    Log.d("UpcomingActivity", "Fetched " + fetchedActivities.size() + " upcoming activities from DB.");

                    // Clear the old list to avoid duplicates
                    activitiesList.clear();
                    activitiesList.addAll(fetchedActivities); // Add the fetched activities

                    // Notify the adapter to update the RecyclerView
                    activityAdapter.notifyDataSetChanged();
                } else {
                    Log.d("UpcomingActivity", "No activities found in DB or fetched list is null.");
                    activitiesList.clear(); // Clear the list if no data is found
                    activityAdapter.notifyDataSetChanged(); // Notify the adapter to reflect the changes
                }
            });
        });
    }

}
