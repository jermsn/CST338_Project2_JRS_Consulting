package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.TrackTournamentLog;
import com.example.cst338_tracktournament.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TrackTournamentRepository repository;

    public static final String Tag = "JRS_TRACKLOG";

    String mUserName = "";
    String mPassword = "";
    String mUserType = "Coach";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TrackTournamentRepository.getRepository(getApplication());

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInformationFromDisplay();
                insertTrackLogRecord();

                // 2024-07-31 Jeremy - Right now this is hardcoded to always jump to the Coach activities
                //TODO: This needs to differentiate between user types in the future.
                Intent intent = Coach_Activity.coachActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void insertTrackLogRecord(){
        TrackTournamentLog log = new TrackTournamentLog(mUserName,mPassword, mUserType);
        repository.insertTrackTournamentLog(log);
    }

    private void getInformationFromDisplay(){
        mUserName = binding.userNameInputEditText.getText().toString();
        mPassword = binding.passwordInputEditText.getText().toString();
    }

    /**
     * This is the intent (a messaging object to request action from another app
     * component) factory for the Main Activity (log on screen) portion of application.
     * @param context the state of the active application
     * @return the intent of the main application
     */
    static Intent mainActivityFactory (Context context) {
        return new Intent(context, MainActivity.class);
    }
}