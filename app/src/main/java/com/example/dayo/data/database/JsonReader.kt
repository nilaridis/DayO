package com.example.dayo.data.database

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun loadActivitiesFromJson(context: Context): List<Activity> {
    val jsonString = context.assets.open("activities.json").bufferedReader().use { it.readText() }
    val gson = Gson()
    val listType = object : TypeToken<List<Activity>>() {}.type
    return gson.fromJson(jsonString, listType)
}