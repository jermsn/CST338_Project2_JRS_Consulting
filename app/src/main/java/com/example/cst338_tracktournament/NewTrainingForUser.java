package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.LiveData;
import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.Users;
import com.example.cst338_tracktournament.databinding.ActivityNewTrainingForUserBinding;

public class NewTrainingForUser extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.cst338_tracktournament.MAIN_ACTIVITY_USER_ID";
    private ActivityNewTrainingForUserBinding binding;
    private TrackTournamentRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewTrainingForUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TrackTournamentRepository.getRepository(getApplication());
        displayUserNameInWindowTitle();

        binding.newTrainingForUserQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserTraining.userTrainingActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.newTrainingForUserLogItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implement save new training information to database.
            }
        });


    }

    private void displayUserNameInWindowTitle(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);

        // Launch the logon intent factory
        int userId = sharedPreferences.getInt(MainActivity.SHARED_PREFERENCE_USERID_KEY,0);

        LiveData<Users> userObserver = repository.getUserById(userId);

        userObserver.observe(this, user -> {
            if(user != null) {

                binding.newTrainingForUserLabelTextView.setText(String.format("New Training for\n%s", user.getName()));
            }
        });
    }

    /**
     * This is the intent (a messaging object to request action from another app
     * component) factory for the New Training for User Activity portion of application.
     * @param context the state of the active application
     * @return the intent of the main application
     */
    static Intent newTrainingForUserActivityIntentFactory (Context context) {
        return new Intent(context, NewTrainingForUser.class);
    }
}