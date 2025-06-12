package com.example.dayo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dayo.R;

import java.util.ArrayList;

public class FiltersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filters);

        Button applyFiltersButton = findViewById(R.id.apply_filters_button);
        ImageView closeFilters = findViewById(R.id.close_filters);
        applyFiltersButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            double budgetFrom = 0, budgetTo = Double.MAX_VALUE;
            try {
                String fromStr = ((EditText) findViewById(R.id.budget_from)).getText().toString();
                if (!fromStr.isEmpty()) budgetFrom = Double.parseDouble(fromStr);

                String toStr = ((EditText) findViewById(R.id.budget_to)).getText().toString();
                if (!toStr.isEmpty()) budgetTo = Double.parseDouble(toStr);
            } catch (NumberFormatException ignored) {}

            int durationFrom = 0, durationTo = Integer.MAX_VALUE;
            try {
                String fromStr = ((EditText) findViewById(R.id.duration_from)).getText().toString();
                if (!fromStr.isEmpty()) durationFrom = Integer.parseInt(fromStr);

                String toStr = ((EditText) findViewById(R.id.duration_to)).getText().toString();
                if (!toStr.isEmpty()) durationTo = Integer.parseInt(toStr);
            } catch (NumberFormatException ignored) {}

            ArrayList<String> categories = new ArrayList<>();
            if (((CheckBox) findViewById(R.id.category_live_music)).isChecked())
                categories.add("LIVE_MUSIC");
            if (((CheckBox) findViewById(R.id.category_art_culture)).isChecked())
                categories.add("ART_CULTURE");
            if (((CheckBox) findViewById(R.id.category_nature_outdoors)).isChecked())
                categories.add("NATURE_OUTDOORS");
            if (((CheckBox) findViewById(R.id.adrenalineRush)).isChecked())
                categories.add("ADRENALINE_RUSH");

            resultIntent.putExtra("budgetFrom", budgetFrom);
            resultIntent.putExtra("budgetTo", budgetTo);
            resultIntent.putExtra("durationFrom", durationFrom);
            resultIntent.putExtra("durationTo", durationTo);
            resultIntent.putStringArrayListExtra("categories", categories);

            setResult(RESULT_OK, resultIntent);
            finish();



        });

        closeFilters.setOnClickListener(v -> {
            Intent intent = new Intent(FiltersActivity.this , SearchActivity.class);
            startActivity(intent);
        });
        }

}