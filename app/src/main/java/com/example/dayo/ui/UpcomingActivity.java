package com.example.dayo.ui;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    private List<Activity> activitiesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        setupHeaderAndNavbar();

        recyclerViewActivities = findViewById(R.id.recyclerViewUpcoming);
        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));

        activityAdapter = new ActivityAdapter(
                this,
                activitiesList,
                new OnMoreInfoClickListener() {
                    @Override
                    public void onMoreInfoClick(Activity activity) {
                        Intent intent = new Intent(UpcomingActivity.this, ActivityInfoActivity.class);
                        intent.putExtra("ACTIVITY_ID", activity.getId());
                        startActivity(intent);
                    }
                }
        );
        recyclerViewActivities.setAdapter(activityAdapter);

        if (activitiesList.isEmpty()) {
            loadActivitiesFromDb();
        }
    }

    private void loadActivitiesFromDb() {
        AppDatabase db = DatabaseInstance.getInstance(getApplicationContext());
        ActivityDao activityDao = db.activityDao();

        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Activity> fetchedActivities = activityDao.getUpcomingActivities();

            runOnUiThread(() -> {
                if (fetchedActivities != null && !fetchedActivities.isEmpty()) {
                    Log.d("UpcomingActivity", "Fetched " + fetchedActivities.size() + " upcoming activities from DB.");

                    activitiesList.clear();
                    activitiesList.addAll(fetchedActivities);

                    activityAdapter.notifyDataSetChanged();
                } else {
                    Log.d("UpcomingActivity", "No activities found in DB or fetched list is null.");
                    activitiesList.clear();
                    activityAdapter.notifyDataSetChanged();
                }
            });
        });
    }

}
