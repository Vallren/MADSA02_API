package com.example.madsa023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewLocationActivity extends AppCompatActivity {

    // creating a variables for our button and edittext.
    private EditText LocationNameEdt, LocationDescEdt, LocationDurationEdt;
    private Button LocationBtn;

    // creating a constant string variable for our
    // Location name, description and duration.
    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_Location_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_Location_NAME";
    public static final String EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_Location_DESCRIPTION";
    public static final String EXTRA_DURATION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_Location_DURATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);

        // initializing our variables for each view.
        LocationNameEdt = findViewById(R.id.idEdtLocationName);
        LocationDescEdt = findViewById(R.id.idEdtLocationDescription);
        LocationDurationEdt = findViewById(R.id.idEdtLocationDuration);
        LocationBtn = findViewById(R.id.idBtnSaveLocation);

        // below line is to get intent as we
        // are getting data via an intent.
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            // if we get id for our data then we are
            // setting values to our edit text fields.
            LocationNameEdt.setText(intent.getStringExtra(EXTRA_Location_NAME));
            LocationDescEdt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            LocationDurationEdt.setText(intent.getStringExtra(EXTRA_DURATION));
        }
        // adding on click listener for our save button.
        LocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if
                // the text fields are empty or not.
                String LocationName = LocationNameEdt.getText().toString();
                String LocationDesc = LocationDescEdt.getText().toString();
                String LocationDuration = LocationDurationEdt.getText().toString();
                if (LocationName.isEmpty() || LocationDesc.isEmpty() || LocationDuration.isEmpty()) {
                    Toast.makeText(NewLocationActivity.this, "Please enter the valid location details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our Location.
                saveLocation(LocationName, LocationDesc, LocationDuration);
            }
        });
    }

    private void saveLocation(String LocationName, String LocationDescription, String LocationDuration) {
        // inside this method we are passing
        // all the data via an intent.
        Intent data = new Intent();

        // in below line we are passing all our Location detail.
        data.putExtra(EXTRA_Location_NAME, LocationName);
        data.putExtra(EXTRA_DESCRIPTION, LocationDescription);
        data.putExtra(EXTRA_DURATION, LocationDuration);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data.
        setResult(RESULT_OK, data);

        // displaying a toast message after adding the data
        Toast.makeText(this, "Location has been saved to Room Database. ", Toast.LENGTH_SHORT).show();
    }
}
