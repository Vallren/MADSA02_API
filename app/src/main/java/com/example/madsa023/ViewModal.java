package com.example.madsa023;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModal extends AndroidViewModel {

    // creating a new variable for Location repository.
    private LocationRepository repository;

    // below line is to create a variable for live
    // data where all the Locations are present.
    private LiveData<List<LocationModal>> allLocations;

    // constructor for our view modal.
    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new LocationRepository(application);
        allLocations = repository.getAllLocations();
    }

    // below method is use to insert the data to our repository.
    public void insert(LocationModal model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(LocationModal model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(LocationModal model) {
        repository.delete(model);
    }

    // below method is to delete all the Locations in our list.
    public void deleteAllLocations() {
        repository.deleteAllLocations();
    }

    // below method is to get all the Locations in our list.
    public LiveData<List<LocationModal>> getAllLocations() {
        return allLocations;
    }
}

