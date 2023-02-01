package com.example.madsa023;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // creating a variables for our recycler view.
    private RecyclerView LocationsRV;
    private static final int ADD_Location_REQUEST = 1;
    private static final int EDIT_Location_REQUEST = 2;
    private ViewModal viewmodal;
    private TextView mTextViewJSONName;
    private TextView mTextViewJSONTheme;
    private TextView mTextViewJSONData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our variable for our recycler view and fab.
        LocationsRV = findViewById(R.id.idRVLocations);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);

        // adding on click listener for floating action button.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starting a new activity for adding a new Location
                // and passing a constant value in it.
                Intent intent = new Intent(MainActivity.this, NewLocationActivity.class);
                startActivityForResult(intent, ADD_Location_REQUEST);
            }
        });

        // setting layout manager to our adapter class.
        LocationsRV.setLayoutManager(new LinearLayoutManager(this));
        LocationsRV.setHasFixedSize(true);

        // initializing adapter for recycler view.
        final LocationRVAdapter adapter = new LocationRVAdapter();

        // setting adapter class for recycler view.
        LocationsRV.setAdapter(adapter);

        // passing a data from view modal.
        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        // below line is use to get all the Locations from view modal.
        viewmodal.getAllLocations().observe(this, new Observer<List<LocationModal>>() {
            @Override
            public void onChanged(List<LocationModal> models) {
                // when the data is changed in our models we are
                // adding that list to our adapter class.
                adapter.submitList(models);
            }
        });
        // below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
                viewmodal.delete(adapter.getLocationAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Location deleted", Toast.LENGTH_SHORT).show();
            }
        }).
                // below line is use to attach this to recycler view.
                        attachToRecyclerView(LocationsRV);
        // below line is use to set item click listener for our item of recycler view.
        adapter.setOnItemClickListener(new LocationRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LocationModal model) {
                // after clicking on item of recycler view
                // we are opening a new activity and passing
                // a data to our activity.
                Intent intent = new Intent(MainActivity.this, NewLocationActivity.class);
                intent.putExtra(NewLocationActivity.EXTRA_ID, model.getId());
                intent.putExtra(NewLocationActivity.EXTRA_Location_NAME, model.getLocationName());
                intent.putExtra(NewLocationActivity.EXTRA_DESCRIPTION, model.getLocationDescription());
                intent.putExtra(NewLocationActivity.EXTRA_DURATION, model.getLocationDuration());

                // below line is to start a new activity and
                // adding a edit Location constant.
                startActivityForResult(intent, EDIT_Location_REQUEST);
            }
        });/*
        try {
            getHTTPData();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_Location_REQUEST && resultCode == RESULT_OK) {
            String LocationName = data.getStringExtra(NewLocationActivity.EXTRA_Location_NAME);
            String LocationDescription = data.getStringExtra(NewLocationActivity.EXTRA_DESCRIPTION);
            String LocationDuration = data.getStringExtra(NewLocationActivity.EXTRA_DURATION);
            LocationModal model = new LocationModal(LocationName, LocationDescription, LocationDuration);
            viewmodal.insert(model);
            Toast.makeText(this, "Location saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_Location_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewLocationActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Location can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String LocationName = data.getStringExtra(NewLocationActivity.EXTRA_Location_NAME);
            String LocationDesc = data.getStringExtra(NewLocationActivity.EXTRA_DESCRIPTION);
            String LocationDuration = data.getStringExtra(NewLocationActivity.EXTRA_DURATION);
            LocationModal model = new LocationModal(LocationName, LocationDesc, LocationDuration);
            model.setId(id);
            viewmodal.update(model);
            Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Location not saved", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    void getHTTPData() throws IOException {
        //this need changing so it takes user city name, checks geolocation API and uses that for coords
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder();
        urlBuilder.addQueryParameter("lat", "53.230606408229466");
        urlBuilder.addQueryParameter("lon", "-0.5407005174034509");
        urlBuilder.addQueryParameter("appid", "30650154e6ae10e253938fd2a1eb4480"); //api key
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // Grab the data from the API response as a string
                final String myResponse = response.body().string();

                response.close();

                // Run this part of the code on the UI thread so it can update the TextViews
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // output the response text to logcat
                        Log.d("OkHTTPResponse",myResponse);
                        // Decode the string to a JSON object and extract specific parts
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            JSONArray oAttributes = json.getJSONArray("weather");
                            //mTextViewJSONTheme.setText(oAttributes.getJSONObject(0).getString("idTVLocationDuration"));
                            //mTextViewJSONName.setText(oAttributes.getJSONObject(0).getString("idTVLocationDescription"));

                        } catch (JSONException e) {
                            // Something went wrong when processing the JSON data output the error
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }*/

}
