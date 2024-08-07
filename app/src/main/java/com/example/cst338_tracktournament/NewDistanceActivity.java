package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.RaceTypes;
import com.example.cst338_tracktournament.databinding.ActivityNewDistanceBinding;

/**
 * NewDistanceActivity handles the entry and logging of new race
 * distances in the TrackTrounament application
 * @author Jeremy Norvell
 * Date: 2024-07-30
 */
public class NewDistanceActivity extends AppCompatActivity {

    //Bind this code to our activity_coach.xml interface
    private ActivityNewDistanceBinding binding;
    private TrackTournamentRepository repository;

    String mRaceName = "";
    double mMinLength = 0.0;
    double mMaxLength = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewDistanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TrackTournamentRepository.getRepository(getApplication());

        // This is our button to add to the database
        binding.buttonCreateNewDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First, we get the distances entered in the display
                fetchNewDistance();
                // Then we insert our race
                insertRaceDistance();

                // Return to the Coach screen
                Intent intent = Coach_Activity.coachActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    /**
     * This reads the information in the text fields and logs any exceptions
     */
    private void fetchNewDistance(){
        //Get user name and password from the New User interface
        mRaceName = binding.nameDistanceEditText.getText().toString();

        // This line pulls in the text from the "@+id/weightInputText" editText and tries to convert it
        try {
            mMinLength = Double.parseDouble(binding.minDistanceEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(MainActivity.Tag, "Error reading value from new length minimum entry.");
        }

        // This line pulls in the text from the "@+id/weightInputText" editText and tries to convert it
        try {
            mMaxLength = Double.parseDouble(binding.maxDistanceEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(MainActivity.Tag, "Error reading value from new length maximum entry.");
        }
    }

    /**
     * This inserts records into our race distance table
     */
    private void insertRaceDistance() {
        // This ensure we won't enter an race with an empty exercise type field.
        if (mRaceName.isEmpty() || mMaxLength == 0) {
            Toast.makeText(NewDistanceActivity.this, "Invalid race. Name and max distance over 0 required.", Toast.LENGTH_SHORT).show();
            return;
        }
        // We create a new raceType object with the supplied info
        // received from getInformationFromDisplay
        RaceTypes race = new RaceTypes(mRaceName, mMinLength, mMaxLength);
        repository.insertRaceType(race);
    }

    /**
     * This is the intent (a messaging object to request action from another app
     * component) factory for the Add New Distance portion of application.
     * @param context the state of the active application
     * @return the intent of the main application
     */
    static Intent newDistanceIntentFactory (Context context) {
        return new Intent(context, NewDistanceActivity.class);
    }
}