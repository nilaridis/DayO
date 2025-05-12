package com.example.dayo.ui;

import android.os.Bundle;

import com.example.dayo.R;
import com.example.dayo.data.database.AppDatabase;
import com.example.dayo.data.database.ActivityDao;
import com.example.dayo.data.database.Activity;
import com.example.dayo.data.database.DatabaseInstance;

import java.util.List;
import com.example.dayo.ui.LoginActivity;
import com.example.dayo.ui.ProfileActivity;
import com.example.dayo.ui.SignUpActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Βεβαιώσου ότι φορτώνεται το σωστό layout
        setupHeaderAndNavbar(); // Ρύθμισε το header και το navbar
    }

        // Δημιουργία instance της βάσης δεδομένων
        AppDatabase db = DatabaseInstance.getInstance(this);
        ActivityDao activityDao = db.activityDao();


}