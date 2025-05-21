package com.example.dayo.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.R;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", "onCreate called in BaseActivity");

        if (!isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    protected void setupHeaderAndNavbar() {
        ImageButton profileButton = findViewById(R.id.profileButton);
        if (profileButton != null) {
            profileButton.setOnClickListener(v -> {
                Log.d("BaseActivity", "Profile button clicked");
                Intent intent = new Intent(BaseActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
        } else {
            Log.e("BaseActivity", "Profile button is null");
        }

        // Logo Button
        ImageView logoButton = findViewById(R.id.logoImage);
        if (logoButton != null) {
            logoButton.setOnClickListener(v -> {
                Log.d("BaseActivity", "Logo button clicked");
                if (!(BaseActivity.this instanceof MainActivity)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("BaseActivity", "Logo button is null");
        }

        ImageButton notificationBtn = findViewById(R.id.notificationButton);
        if (notificationBtn != null) {
            notificationBtn.setOnClickListener(v -> {
                Log.d("BaseActivity", "Notification button clicked");
                if (!(BaseActivity.this instanceof NotificationsActivity)) {
                    Intent intent = new Intent(BaseActivity.this, NotificationsActivity.class);
                    startActivity(intent);
                }
            });
        }

        ImageButton homeBtn = findViewById(R.id.nav_home);
        if (homeBtn != null) {
            homeBtn.setOnClickListener(v -> {
                if(!(BaseActivity.this instanceof MainActivity)){
                    Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }

        ImageButton searchBtn = findViewById(R.id.nav_search);
        if (searchBtn != null) {
            searchBtn.setOnClickListener(v -> {
                if (!(BaseActivity.this instanceof SearchActivity)) {
                    Intent intent = new Intent(BaseActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            });
        }

        ImageButton matchBtn = findViewById(R.id.nav_match);
        if (matchBtn != null) {
            matchBtn.setOnClickListener(v -> {
                if (!(BaseActivity.this instanceof MatchActivity)) {
                    Intent intent = new Intent(BaseActivity.this, MatchActivity.class);
                    startActivity(intent);
                }
            });
        }

        ImageButton upcomingBtn = findViewById(R.id.nav_events);
        if (upcomingBtn != null) {
            upcomingBtn.setOnClickListener(v -> {
                if (!(BaseActivity.this instanceof UpcomingActivity)) {
                    Intent intent = new Intent(BaseActivity.this, UpcomingActivity.class);
                    startActivity(intent);
                }
            });
        }


    }

    private boolean isTablet() {
        return (getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}

