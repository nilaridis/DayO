package com.example.dayo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.dayo.R;
import com.example.dayo.data.database.DatabaseInstance;
import com.example.dayo.data.database.User;

import java.util.concurrent.Executors;


public class ProfileActivity extends BaseActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupHeaderAndNavbar();

        TextView userNameText = findViewById(R.id.userName);
        Button signOutBtn = findViewById(R.id.btnSignOut);

        Executors.newSingleThreadExecutor().execute(() -> {
            int userId = UserSessionHelper.getLoggedInUserId(this);
            User user = DatabaseInstance.getInstance(getApplicationContext()).userDao().getUserById(userId);
            final String username = user.getName();
            userNameText.setText(username);
        });

        signOutBtn.setOnClickListener(v -> {
            UserSessionHelper.logout(this);

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        });

    }
}