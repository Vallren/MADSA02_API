package com.example.madsa023;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Adding annotation
// to our Dao class
@androidx.room.Dao
public interface Dao {

    // below method is use to
    // add data to database.
    @Insert
    void insert(LocationModal model);

    // below method is use to update
    // the data in our database.
    @Update
    void update(LocationModal model);

    // below line is use to delete a
    // specific Location in our database.
    @Delete
    void delete(LocationModal model);

    // on below line we are making query to
    // delete all Locations from our database.
    @Query("DELETE FROM Location_table")
    void deleteAllLocations();

    // below line is to read all the Locations from our database.
    // in this we are ordering our Locations in ascending order
    // with our Location name.
    @Query("SELECT * FROM Location_table ORDER BY LocationName ASC")
    LiveData<List<LocationModal>> getAllLocations();
}

