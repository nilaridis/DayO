package com.example.dayo.ui.notifications;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.R;
import com.example.dayo.ui.base.BaseActivity;

public class NotificationsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        setupHeaderAndNavbar();
        // Any additional logic for notifications can go here
    }
}