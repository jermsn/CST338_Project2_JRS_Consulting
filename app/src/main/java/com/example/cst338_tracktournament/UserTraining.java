package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.LiveData;
import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.TrackTournamentLog;
import com.example.cst338_tracktournament.databinding.ActivityUserTrainingBinding;

import java.util.List;

public class UserTraining extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.cst338_tracktournament.MAIN_ACTIVITY_USER_ID";
    private ActivityUserTrainingBinding binding;
    private TrackTournamentRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserTrainingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TrackTournamentRepository.getRepository(getApplication());
        displayUserNameInWindowTitle();

        binding.userTrainingQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check shared preference for logged in user
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,
                        Context.MODE_PRIVATE);
                // Launch the logon intent factory
                int userId = sharedPreferences.getInt(MainActivity.SHARED_PREFERENCE_USERID_KEY,0);
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext(),userId);
                startActivity(intent);
            }
        });
        binding.EnterTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO open the training window
            }
        });

        binding.userTrainingLabelTextView.setMovementMethod(new ScrollingMovementMethod());

    }

    private void displayUserNameInWindowTitle(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);

        // Launch the logon intent factory
        int userId = sharedPreferences.getInt(MainActivity.SHARED_PREFERENCE_USERID_KEY,0);

        LiveData<TrackTournamentLog> userObserver = repository.getUserById(userId);

        userObserver.observe(this, user -> {
            if(user != null) {

                binding.userTrainingLabelTextView.setText(String.format("%s's\nTrainings", user.getName()));
            }
        });
    }


    /**
     * This is the intent (a messaging object to request action from another app
     * component) factory for the User Training Activity portion of application.
     * @param context the state of the active application
     * @return the intent of the main application
     */
    static Intent userTrainingActivityIntentFactory (Context context) {
        return new Intent(context, UserTraining.class);
    }
}