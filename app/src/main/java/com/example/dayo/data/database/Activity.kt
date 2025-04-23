package com.example.dayo.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-increment ID
    val name: String,
    @TypeConverters(CategoryConverter::class) val category: Category, // Χρήση του enum Category
    val description: String,
    val location: String,
    val pricePerPerson: Double, // Τιμή ανά άτομο
    val duration: Int, // Διάρκεια σε λεπτά
    val distance: Double // Απόσταση σε χιλιόμετρα
)