package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewDistanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_distance);
    }


    /**
     * This is the intent (a messaging object to request action from another app
     * component) factory for the Add New Distance portion of application.
     * @param context the state of the active application
     * @return the intent of the main application
     */
    static Intent newDistanceIntentFactory (Context context) {
        return new Intent(context, MainActivity.class);
    }
}