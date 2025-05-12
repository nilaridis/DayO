package com.example.dayo.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.SearchActivity;
import com.example.dayo.R;
import com.example.dayo.data.database.AppDatabase;
import com.example.dayo.data.database.ActivityDao;
import com.example.dayo.data.database.Activity;
import com.example.dayo.data.database.JsonUtils;
import com.example.dayo.data.database.DatabaseInstance;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Δημιουργία instance της βάσης δεδομένων
        AppDatabase db = DatabaseInstance.getInstance(this);
        ActivityDao activityDao = db.activityDao();

        // Διαγραφή παλαιών δεδομένων (προαιρετικό)
        new Thread(() -> {
            activityDao.deleteAllActivities();

            // Φόρτωση δεδομένων από το JSON αρχείο
            List<Activity> activities = JsonUtils.loadActivitiesFromJson(this);

            // Εισαγωγή δεδομένων στη βάση δεδομένων
            if (activities != null) {
                activityDao.insertActivities(activities);
                Log.d("MainActivity", "Δεδομένα εισήχθησαν στη βάση δεδομένων: " + activities.size());
            }

            // Ανάκτηση δεδομένων από τη βάση δεδομένων
            List<Activity> allActivities = activityDao.getAllActivities();
            for (Activity activity : allActivities) {
                Log.d("MainActivity", "Activity: " + activity.getName() + ", Location: " + activity.getLocation());
            }
        }).start();
        // Πρόσθεσε OnClickListener για το κουμπί nav_search
        ImageButton navSearchButton = findViewById(R.id.nav_search); // Βεβαιώσου ότι το ID είναι σωστό
        navSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ξεκίνησε το SearchActivity
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}