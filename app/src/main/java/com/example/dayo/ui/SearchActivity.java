package com.example.dayo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dayo.data.database.Activity;
import com.example.dayo.R;
import com.example.dayo.data.database.DatabaseInstance;
import com.example.dayo.data.database.AppDatabase;
import com.example.dayo.data.database.ActivityDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SearchActivity extends BaseActivity {

    private RecyclerView recyclerViewActivities;
    private ActivityAdapter activityAdapter;
    private List<Activity> activitiesList = new ArrayList<>();
    private EditText searchEditText;
    private LinearLayout searchBarContainer;
    private String category;
    private ActivityResultLauncher<Intent> filtersLauncher;
    private double budgetFrom = 0, budgetTo = Double.MAX_VALUE;
    private int durationFrom = 0, durationTo = Integer.MAX_VALUE;
    private List<String> filterCategories = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupHeaderAndNavbar();

        recyclerViewActivities = findViewById(R.id.recyclerViewActivities);
        ImageButton btnFilters = findViewById(R.id.btnFilters);
        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));
        activityAdapter = new ActivityAdapter(
                this,
                activitiesList,
                new OnMoreInfoClickListener() {
                    @Override
                    public void onMoreInfoClick(Activity activity) {
                        Intent intent = new Intent(SearchActivity.this, ActivityInfoActivity.class);
                        intent.putExtra("ACTIVITY_ID", activity.getId());
                        startActivity(intent);
                    }
                }
        );
        recyclerViewActivities.setAdapter(activityAdapter);

        searchEditText = findViewById(R.id.search);
        searchBarContainer = findViewById(R.id.searchBarContainer);
        category = getIntent().getStringExtra("CATEGORY");

        searchBarContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterActivities(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        filtersLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        budgetFrom = result.getData().getDoubleExtra("budgetFrom", 0);
                        budgetTo = result.getData().getDoubleExtra("budgetTo", Double.MAX_VALUE);
                        durationFrom = result.getData().getIntExtra("durationFrom", 0);
                        durationTo = result.getData().getIntExtra("durationTo", Integer.MAX_VALUE);
                        filterCategories = result.getData().getStringArrayListExtra("categories");
                        filterActivities(searchEditText.getText().toString());
                    }
                }
        );

        btnFilters.setOnClickListener(v ->{
            Intent intent = new Intent(SearchActivity.this , FiltersActivity.class);
            filtersLauncher.launch(intent);
        });





        loadActivitiesFromDb();
    }

    private void loadActivitiesFromDb() {
        AppDatabase db = DatabaseInstance.getInstance(getApplicationContext());
        ActivityDao activityDao = db.activityDao();

        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Activity> fetchedActivities;
            if (category != null && !category.isEmpty()) {
                fetchedActivities = activityDao.getActivitiesByCategory(category);
                Log.d("SearchActivity", "Fetched activities for category: " + category);
            } else {
                fetchedActivities = activityDao.getAllActivities();
                Log.d("SearchActivity", "Fetched all activities");
            }


            runOnUiThread(() -> {
                if (fetchedActivities != null && !fetchedActivities.isEmpty()) {
                    Log.d("SearchActivity", "Fetched " + fetchedActivities.size() + " activities from DB.");
                    activitiesList = fetchedActivities;
                    filterActivities("");
                } else {
                    Log.d("SearchActivity", "No activities found in DB or fetched list is null.");
                    activityAdapter.setActivities(new ArrayList<>());
                }
            });
        });
    }

    private void filterActivities(String searchText) {
        String searchTextTrimmed = searchText.trim();

        List<Activity> filteredList = activitiesList.stream()
                .filter(activity ->
                        (activity.getName().toLowerCase().contains(searchTextTrimmed.toLowerCase()) ||
                                activity.getLocation().toLowerCase().contains(searchTextTrimmed.toLowerCase()) ||
                                activity.getDescription().toLowerCase().contains(searchTextTrimmed.toLowerCase()))
                                && activity.getPrice() >= budgetFrom && activity.getPrice() <= budgetTo
                                && activity.getDuration() >= durationFrom && activity.getDuration() <= durationTo
                                && (filterCategories.isEmpty() || filterCategories.contains(activity.getCategory()))
                )
                .collect(Collectors.toList());

        activityAdapter.setActivities(filteredList);
        activityAdapter.notifyDataSetChanged();
    }
}