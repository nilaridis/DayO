package com.example.dayo.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dayo.R;

import java.util.Calendar;
import java.util.Random;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupHeaderAndNavbar();

        // WELCOME MESSAGE
        TextView welcomeText = findViewById(R.id.welcomeText2);
        String massage = getGreetingMessage();
        welcomeText.setText(massage);

        ImageButton btnLiveMusic = findViewById(R.id.btnLiveMusic);
        ImageButton btnNatureOutdoors = findViewById(R.id.btnNatureOutdoors);
        ImageButton btnArtsCulture = findViewById(R.id.btnArtCulture);
        ImageButton btnAdrenalineRush = findViewById(R.id.btnAdrenalineRush);

        // Set onClickListeners for each button
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

        if (hour >= 0 && hour < 10 ){
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
        } else {
            messages = new String[] {
                    "Make today count - try something different!",
                    "No plans ? No problem .",
                    "Evenings are for unforgettable experiences",
                    "Who said the day is over ?"
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


