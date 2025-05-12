package com.example.dayo.ui;

import android.os.Bundle;

import com.example.dayo.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Βεβαιώσου ότι φορτώνεται το σωστό layout
        setupHeaderAndNavbar(); // Ρύθμισε το header και το navbar
    }

        // Εδώ μπορείς να προσθέσεις λογική αν χρειαστεί




}