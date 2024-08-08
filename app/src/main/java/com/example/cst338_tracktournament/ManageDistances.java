package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
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
        return new Intent(context, Coach_Activity.class);
    }
}