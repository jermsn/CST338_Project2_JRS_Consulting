package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338_tracktournament.databinding.ActivityCoachBinding;

public class Coach_Activity extends AppCompatActivity {

    //Bind this code to our activity_coach.xml interface
    private ActivityCoachBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoachBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set an action to move from the new distance button to the new distance activity
        binding.buttonNewDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the relevant intent factory
                Intent intent = NewDistanceActivity.newDistanceIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        // This is our Quit/Logout button. Right now it just moves back to the logon screen
        //TODO: Needs to actually log the user out
        binding.buttonCoachQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the logon intent factory
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
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
    static Intent coachActivityIntentFactory (Context context) {
        return new Intent(context, Coach_Activity.class);
    }
}