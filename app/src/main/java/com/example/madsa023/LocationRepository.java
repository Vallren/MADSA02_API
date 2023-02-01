package com.example.madsa023;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LocationRepository {

    // below line is the create a variable
    // for dao and list for all Locations.
    private Dao dao;
    private LiveData<List<LocationModal>> allLocations;

    // creating a constructor for our variables
    // and passing the variables to it.
    public LocationRepository(Application application) {
        LocationDatabase database = LocationDatabase.getInstance(application);
        dao = database.Dao();
        allLocations = dao.getAllLocations();
    }

    // creating a method to insert the data to our database.
    public void insert(LocationModal model) {
        new InsertLocationAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(LocationModal model) {
        new UpdateLocationAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(LocationModal model) {
        new DeleteLocationAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the Locations.
    public void deleteAllLocations() {
        new DeleteAllLocationsAsyncTask(dao).execute();
    }

    // below method is to read all the Locations.
    public LiveData<List<LocationModal>> getAllLocations() {
        return allLocations;
    }

    // we are creating a async task method to insert new Location.
    private static class InsertLocationAsyncTask extends AsyncTask<LocationModal, Void, Void> {
        private Dao dao;

        private InsertLocationAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(LocationModal... model) {
            // below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our Location.
    private static class UpdateLocationAsyncTask extends AsyncTask<LocationModal, Void, Void> {
        private Dao dao;

        private UpdateLocationAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(LocationModal... models) {
            // below line is use to update
            // our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete Location.
    private static class DeleteLocationAsyncTask extends AsyncTask<LocationModal, Void, Void> {
        private Dao dao;

        private DeleteLocationAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(LocationModal... models) {
            // below line is use to delete
            // our Location modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all Locations.
    private static class DeleteAllLocationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private DeleteAllLocationsAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all Locations.
            dao.deleteAllLocations();
            return null;
        }
    }
}

