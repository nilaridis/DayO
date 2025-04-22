package com.example.dayo.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-increment ID
    val username: String,
    val email: String,
    @TypeConverters(CategoryConverter::class) val preferences: List<Category>, // Προτιμήσεις ως λίστα
    val budget: Double,
    val distancePreference: Double, // Απόσταση σε χιλιόμετρα ή μέτρα
    val passwordHash: String // Hash του password για ασφάλεια
)