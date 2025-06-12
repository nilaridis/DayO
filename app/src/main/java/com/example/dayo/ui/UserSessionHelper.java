package com.example.dayo.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionHelper {
    public static int getLoggedInUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getInt("user_id", -1);
    }

    public static void logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        prefs.edit().remove("userId").apply();
    }
}