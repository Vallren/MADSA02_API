package com.example.madsa023;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

// below line is for setting table name.
@Entity(tableName = "Location_table")
public class LocationModal {

    // below line is to auto increment
    // id for each Location.
    @PrimaryKey(autoGenerate = true)

    // variable for our id.
    private int id;

    // below line is a variable
    // for Location name.
    private String LocationName;

    // below line is use for
    // Location description.
    private String LocationDescription;

    // below line is use
    // for Location duration.
    private String LocationDuration;

    // below line we are creating constructor class.
    // inside constructor class we are not passing
    // our id because it is incrementing automatically
    public LocationModal(String LocationName, String LocationDescription, String LocationDuration) {
        this.LocationName = LocationName;
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.openweathermap.org/geo/1.0/direct").newBuilder();
        urlBuilder.addQueryParameter("q", LocationName + ",GB");
        //urlBuilder.addQueryParameter("country code", "UK");
        urlBuilder.addQueryParameter("appid", "30650154e6ae10e253938fd2a1eb4480"); //api key
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    //Log.i("data", responseBody.string());
                    //Log.i("data", responseBody.string());
                    String json = responseBody.string();
                    System.out.println(json);
                    //String lat = json.();
                    //return responseBody.string();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        this.LocationDescription = LocationDescription;
        this.LocationDuration = LocationDuration;
    }

    // on below line we are creating
    // getter and setter methods.
    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String LocationName) {
        this.LocationName = LocationName;
    }

    public String getLocationDescription() {
        return LocationDescription;
    }

    public void setLocationDescription(String LocationDescription) {
        this.LocationDescription = LocationDescription;
    }

    public String getLocationDuration() {
        return LocationDuration;
    }

    public void setLocationDuration(String LocationDuration) {
        this.LocationDuration = LocationDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
