package com.example.dayo.data.database;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {
    public static List<Activity> loadActivitiesFromJson(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("activities.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<List<Activity>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}