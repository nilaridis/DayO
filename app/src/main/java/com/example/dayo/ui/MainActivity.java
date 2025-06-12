package com.example.dayo.ui;


import android.content.Intent;
import android.util.Log;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayo.R;
import com.example.dayo.data.database.Activity;
import com.example.dayo.data.database.DatabaseInstance;
import com.example.dayo.data.database.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupHeaderAndNavbar();

        TextView welcomeText = findViewById(R.id.welcomeText2);
        String massage = getGreetingMessage();
        welcomeText.setText(massage);

        RecyclerView recyclerViewPicked = findViewById(R.id.recyclerViewPicked);
        recyclerViewPicked.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ActivityAdapter pickedAdapter = new ActivityAdapter(
                this,
                new ArrayList<>(),
                new OnMoreInfoClickListener() {
                    @Override
                    public void onMoreInfoClick(Activity activity) {
                        Intent intent = new Intent(MainActivity.this, ActivityInfoActivity.class);
                        intent.putExtra("ACTIVITY_ID", activity.getId());
                        startActivity(intent);
                    }
                }
        );
        recyclerViewPicked.setAdapter(pickedAdapter);


        Executors.newSingleThreadExecutor().execute(() -> {
            int userId = UserSessionHelper.getLoggedInUserId(this);
            Log.d("MAIN", "userId: " + userId);
            if (userId == -1) {
                Log.d("MAIN", "No userId, returning");
                return;
            }
            User user = DatabaseInstance.getInstance(getApplicationContext()).userDao().getUserById(userId);
            Log.d("MAIN", "user: " + (user != null ? user.getName() : "null"));
            if (user == null) {
                Log.d("MAIN", "No user, returning");
                return;
            }
            String category = user.getCategory();
            Log.d("MAIN", "user category: " + category);
            List<Activity> acts;
            if ("EVERYTHING".equalsIgnoreCase(category)) {
                acts = DatabaseInstance.getInstance(getApplicationContext()).activityDao().getAllActivities();
            } else {
                acts = DatabaseInstance.getInstance(getApplicationContext()).activityDao().getActivitiesByCategory(category);
            }
            Log.d("MAIN", "acts size: " + (acts != null ? acts.size() : "null"));
            if (acts == null || acts.isEmpty()) {
                Log.d("MAIN", "No acts, returning");
                return;
            }
            Activity randomActivity = acts.get(new Random().nextInt(acts.size()));
            List<Activity> pickedList = new ArrayList<>();
            pickedList.add(randomActivity);

            runOnUiThread(() -> {
                Log.d("MAIN", "activities found: " + pickedList.size());
                pickedAdapter.setActivities(pickedList);
            });
        });

        ImageButton btnLiveMusic = findViewById(R.id.btnLiveMusic);
        ImageButton btnNatureOutdoors = findViewById(R.id.btnNatureOutdoors);
        ImageButton btnArtsCulture = findViewById(R.id.btnArtCulture);
        ImageButton btnAdrenalineRush = findViewById(R.id.btnAdrenalineRush);

        btnLiveMusic.setOnClickListener(v -> openSearchActivity("LIVE_MUSIC"));
        btnNatureOutdoors.setOnClickListener(v -> openSearchActivity("NATURE_OUTDOORS"));
        btnArtsCulture.setOnClickListener(v -> openSearchActivity("ART_CULTURE"));
        btnAdrenalineRush.setOnClickListener(v -> openSearchActivity("ADRENALINE_RUSH"));

    }

    private String getGreetingMessage() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        Random random = new Random();
        String[] messages;

        if (hour >= 7 && hour < 12 ){
            messages = new String[]{
                    "Ready to try something new today?",
                    "A new day, a new adventure",
                    "Suns out - let’s make the most of it!",
                    "Discover what your city has to offer today!"
            };
        } else if (hour >=12 && hour<17) {
            messages = new String[]{
                    "Take a break - Something fun is just around the corner!",
                    "Your next experience is just a tap away",
                    "Need a plan for the day? We’ve got ideas.",
                    "Let’s find something spontaneous to do"
            };
        } else if (hour >=17 && hour<23) {
            messages = new String[] {
                    "Make today count - try something different!",
                    "No plans ? No problem .",
                    "Evenings are for unforgettable experiences",
                    "Who said the day is over ?"
            };
        } else{
            messages = new String[] {
                    "The city wakes up with you.",
                    "Start your day inspired!",
                    "The night is still young – planning an early adventure?",
                    "No matter the hour, there’s always something new to discover."
            };
            }

        int index = random.nextInt(messages.length);
        return messages [index];
    }
        private void openSearchActivity(String category) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
        }
    }


