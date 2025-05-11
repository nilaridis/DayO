package com.example.dayo.ui.profile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.R;
import com.example.dayo.ui.base.BaseActivity;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupHeaderAndNavbar();

    }
}