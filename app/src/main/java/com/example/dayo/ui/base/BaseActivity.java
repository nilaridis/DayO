package com.example.dayo.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.R;
import com.example.dayo.ui.main.MainActivity;
import com.example.dayo.ui.profile.ProfileActivity;
import com.example.dayo.ui.notifications.NotificationsActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", "onCreate called in BaseActivity");
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

    }
}

