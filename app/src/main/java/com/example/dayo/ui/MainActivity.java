package com.example.dayo.ui;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.os.Bundle;
import com.example.dayo.R;



public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupHeaderAndNavbar();
        ImageButton btnLiveMusic = findViewById(R.id.btnLiveMusic);
        ImageButton btnNatureOutdoors = findViewById(R.id.btnNatureOutdoors);
        ImageButton btnArtsCulture = findViewById(R.id.btnArtCulture);
        ImageButton btnAdrenalineRush = findViewById(R.id.btnAdrenalineRush);

        // Set onClickListeners for each button
        btnLiveMusic.setOnClickListener(v -> openSearchActivity("LIVE_MUSIC"));
        btnNatureOutdoors.setOnClickListener(v -> openSearchActivity("NATURE_OUTDOORS"));
        btnArtsCulture.setOnClickListener(v -> openSearchActivity("ART_CULTURE"));
        btnAdrenalineRush.setOnClickListener(v -> openSearchActivity("ADRENALINE_RUSH"));
    }
        private void openSearchActivity(String category) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
        }
    }


