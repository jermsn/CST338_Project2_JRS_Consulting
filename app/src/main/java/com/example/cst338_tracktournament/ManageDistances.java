package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338_tracktournament.Database.RaceTypesDAO;
import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.RaceTypes;
import com.example.cst338_tracktournament.databinding.ActivityManageDistancesBinding;

public class ManageDistances extends AppCompatActivity {

    //Bind this code to our activity_manage_distances.xml interface
    private ActivityManageDistancesBinding binding;
    private TrackTournamentRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageDistancesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // This creates an instance which gives us access to our database
        // Note that we built this incorrectly at first and we need a dedicated
        // function to retrieve it without interrupting the main thread
        repository = TrackTournamentRepository.getRepository(getApplication());

        // Set actions to update the table and move from the edit distance button back to the coach activity
        binding.buttonGetRaceDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Take the supplied information and create a temporary race object
                String raceRetrieve = binding.editRaceNameEditText.getText().toString();
                Log.i(MainActivity.Tag, "Coach wants a " + raceRetrieve + " race.");

                // If we didn't find a race, let the user know
                if(raceRetrieve.isEmpty()) {
                    Toast.makeText(ManageDistances.this, "Please enter a race name to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Pull back our race details
                RaceTypes tempRace = repository.getRaceByName(raceRetrieve);

                // If we didn't find a race, let the user know
                if(tempRace == null) {
                    Toast.makeText(ManageDistances.this, "No race found for that name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Otherwise, use this to update the display
                String tempMin = Double.toString(tempRace.getMinimumDistance());
                String tempMax = Double.toString(tempRace.getMaximumDistance());
                binding.raceNameEchoEditText.setText(tempRace.getRaceName());
                binding.minDistanceEchoEditText.setText(tempMin);
                binding.maxDistanceEchoEditText.setText(tempMax);
            }
        });


        // Set actions to delete the race and move from the edit distance button back to the coach activity
        binding.updateDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the relevant intent factory
                Intent intent = Coach_Activity.coachActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        // Set actions to update the table and move from the edit distance button back to the coach activity
        binding.deleteDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the relevant intent factory
                Intent intent = Coach_Activity.coachActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    /**
     * This is the intent (a messaging object to request action from another app
     * component) factory for the Coach Activity portion of application.
     * @param context the state of the active application
     * @return the intent of the main application
     */
    static Intent manageDistancesActivityIntentFactory (Context context) {
        return new Intent(context, ManageDistances.class);
    }
}