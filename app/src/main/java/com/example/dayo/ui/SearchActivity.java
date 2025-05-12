package com.example.dayo.ui;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dayo.R;


public class SearchActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupHeaderAndNavbar();

    }
}