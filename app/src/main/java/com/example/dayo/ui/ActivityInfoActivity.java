package com.example.dayo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dayo.R;
import com.example.dayo.data.database.Activity;
import com.example.dayo.data.database.DatabaseInstance;

import java.util.concurrent.Executors;

public class ActivityInfoActivity extends AppCompatActivity {

    private ImageView activityImage;
    private ImageView closeIcon;
    private TextView activityTitle, activityLocation, activityPrice, activityDuration, activityDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        activityImage = findViewById(R.id.activityImage);
        activityTitle = findViewById(R.id.activityTitle);
        activityLocation = findViewById(R.id.activityLocation);
        activityPrice = findViewById(R.id.activityPrice);
        activityDuration = findViewById(R.id.activityDuration);
        activityDescription = findViewById(R.id.activityDescription);
        closeIcon = findViewById(R.id.closeButton);

        Intent intent = getIntent();
        int activityId = intent.getIntExtra("ACTIVITY_ID", -1);

        if (activityId != -1) {
            Executors.newSingleThreadExecutor().execute(() -> {
                Activity activity = DatabaseInstance.getInstance(getApplicationContext())
                        .activityDao().getActivityById(activityId);

                if (activity != null) {
                    runOnUiThread(() -> bindActivity(activity));
                }
            });
        } else {
            finish();
        }
        closeIcon.setOnClickListener(v -> finish());
    }

    private void bindActivity(Activity activity) {
        activityTitle.setText(activity.getName());
        activityLocation.setText("Location: " + activity.getLocation());
        activityPrice.setText(String.format("Price: %.2f€", activity.getPrice()));
        activityDuration.setText("Duration: " + activity.getDuration() + " minutes");
        activityDescription.setText(activity.getDescription());

        String imageName = activity.getImageName();
        if (imageName != null && !imageName.isEmpty()) {
            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (imageResId != 0) {
                activityImage.setImageResource(imageResId);
            } else {
                activityImage.setImageResource(R.drawable.exampleimage);
            }
        } else {
            activityImage.setImageResource(R.drawable.exampleimage);
        }
    }
}