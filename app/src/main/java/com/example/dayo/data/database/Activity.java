package com.example.dayo.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "activities")
public class Activity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "price_per_person")
    private double price;

    @ColumnInfo(name = "duration_minutes")
    private int duration;

    @ColumnInfo(name = "image")
    private String imageName;

    @ColumnInfo(name = "is_upcoming")
    private boolean isUpcoming;



    // Default Constructor
    public Activity() {}

    // Parameterized Constructor
    public Activity(String name, String category, String description, String location, double price, int duration, String imageName, boolean isUpcoming) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.location = location;
        this.price = price;
        this.duration = duration;
        this.imageName = imageName;
        this.isUpcoming = isUpcoming;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isUpcoming() { return isUpcoming; }

    public void setUpcoming(boolean upcoming) { isUpcoming = upcoming; }

    // toString method for debugging
    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}