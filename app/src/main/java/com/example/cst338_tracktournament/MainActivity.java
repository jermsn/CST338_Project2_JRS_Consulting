package com.example.cst338_tracktournament;

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
}