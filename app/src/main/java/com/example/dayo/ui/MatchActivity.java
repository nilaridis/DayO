package com.example.dayo.ui;

import android.os.Bundle;

import com.example.dayo.R;



public class MatchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        setupHeaderAndNavbar();
    }
}