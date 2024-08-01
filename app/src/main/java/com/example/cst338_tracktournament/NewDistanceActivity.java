package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338_tracktournament.databinding.ActivityNewDistanceBinding;

public class NewDistanceActivity extends AppCompatActivity {

    //Bind this code to our activity_coach.xml interface
    private ActivityNewDistanceBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewDistanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // This is our main button
        binding.buttonCreateNewDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2024-07-31 Jeremy - Right now this is hardcoded to return to the Coach activities
                //TODO: This needs to insert the entered information into the database.
                Intent intent = Coach_Activity.coachActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

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