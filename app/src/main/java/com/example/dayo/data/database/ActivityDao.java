package com.example.dayo.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActivityDao {

    // Εισαγωγή λίστας από δραστηριότητες στη βάση δεδομένων
    @Insert
    void insertAll(List<Activity> activities);

    // Εισαγωγή μίας μεμονωμένης δραστηριότητας
    @Insert
    void insert(Activity activity);

    // Ανάκτηση όλων των δραστηριοτήτων από τη βάση δεδομένων
    @Query("SELECT * FROM activities")
    List<Activity> getAllActivities();

    // Ανάκτηση δραστηριοτήτων βάσει κατηγορίας
    @Query("SELECT * FROM activities WHERE category = :category")
    List<Activity> getActivitiesByCategory(String category);

    // Αναζήτηση δραστηριότητας βάσει ονόματος
    @Query("SELECT * FROM activities WHERE name LIKE '%' || :name || '%'")
    List<Activity> searchActivitiesByName(String name);

    // Διαγραφή όλων των δραστηριοτήτων από τη βάση δεδομένων
    @Query("DELETE FROM activities")
    void deleteAll();
}