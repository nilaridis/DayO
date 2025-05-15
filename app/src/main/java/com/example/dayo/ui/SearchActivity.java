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


public class SearchActivity extends BaseActivity {

    private RecyclerView recyclerViewActivities;
    private ActivityAdapter activityAdapter;
    private List<Activity> activitiesList = new ArrayList<>(); // Αρχικοποίηση για αποφυγή NullPointerException

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupHeaderAndNavbar();

        recyclerViewActivities = findViewById(R.id.recyclerViewActivities);
        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));

        // Αρχικοποίησε τον adapter με μια κενή λίστα αρχικά
        activityAdapter = new ActivityAdapter(this, activitiesList);
        recyclerViewActivities.setAdapter(activityAdapter);

        loadActivitiesFromDb();

    }

    private void loadActivitiesFromDb() {
        AppDatabase db = DatabaseInstance.getInstance(getApplicationContext()); // Αν χρησιμοποιείς την DatabaseInstance
        ActivityDao activityDao = db.activityDao();

        // Εκτέλεσε την ανάκτηση δεδομένων σε ένα background thread
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Activity> fetchedActivities = activityDao.getAllActivities();

            // Ενημέρωσε το UI στο main thread
            runOnUiThread(() -> {
                if (fetchedActivities != null && !fetchedActivities.isEmpty()) {
                    Log.d("SearchActivity", "Fetched " + fetchedActivities.size() + " activities from DB.");
                    activityAdapter.setActivities(fetchedActivities);
                } else {
                    Log.d("SearchActivity", "No activities found in DB or fetched list is null.");
                    activityAdapter.setActivities(new ArrayList<>()); // Εμφάνισε κενή λίστα
                }
            });
        });
    }
}