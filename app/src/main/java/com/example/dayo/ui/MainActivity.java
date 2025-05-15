package com.example.dayo.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayo.R;
import com.example.dayo.data.database.AppDatabase;
import com.example.dayo.data.database.Activity;
import com.example.dayo.data.database.ActivityDao;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Βεβαιώσου ότι φορτώνεται το σωστό layout
        setupHeaderAndNavbar(); // Ρύθμισε το header και το navbar
    }


}