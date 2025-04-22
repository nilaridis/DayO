package com.example.dayo.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryConverter {

    @TypeConverter
    fun fromCategoryList(categories: List<Category>): String {
        return Gson().toJson(categories) // Μετατροπή λίστας σε JSON string
    }

    @TypeConverter
    fun toCategoryList(data: String): List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type
        return Gson().fromJson(data, listType) // Μετατροπή JSON string σε λίστα
    }
}