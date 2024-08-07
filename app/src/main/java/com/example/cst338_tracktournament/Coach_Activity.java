package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338_tracktournament.Database.RaceTypesDAO;
import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.UserTrainingLog;
import com.example.cst338_tracktournament.databinding.ActivityCoachBinding;

import java.util.ArrayList;


public class Coach_Activity extends AppCompatActivity {

    //Bind this code to our activity_coach.xml interface
    private ActivityCoachBinding binding;
    private TrackTournamentRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoachBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // This creates an instance which gives us access to our database
        // Note that we built this incorrectly at first and we need a dedicated
        // function to retrieve it without interrupting the main thread
        repository = TrackTournamentRepository.getRepository(getApplication());

        // Set an action to move from the new distance button to the new distance activity
        binding.buttonNewDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the relevant intent factory
                Intent intent = NewDistanceActivity.newDistanceIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        // Display our runners sorted by best pace for each race type
        binding.buttonListPace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display our results
                updateDisplayByPace();
            }
        });

        // Display our runners sorted by count of times trained
        binding.buttonListTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display our results
                updateDisplayByCount();
            }
        });

        // This is our return back to training logs.
        binding.buttonCoachQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the logon intent factory
                Intent intent = UserTraining.userTrainingActivityIntentFactory(getApplicationContext());
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

    /**
     * A method to return our pace results in an easily readible format
     * @param results object of type RaceTypesDAO.paceResults, which tells us the race type, runner, and pace
     * @return a string of the above, but more readable
     */
    public String paceToString (RaceTypesDAO.paceResults results) {
        String racePace = UserTrainingLog.secondsToStringTime((int) results.avgPace);
        return "Race type: " + results.raceName + '\n' +
                "Runner: " + results.name + "    (" +
                "Average Pace: " + racePace + " per mile)" + '\n';
    }

    /**
     * A method to return our pace results in an easily readible format
     * @param results object of type RaceTypesDAO.paceResults, which tells us the race type, runner, and pace
     * @return a string of the above, but more readable
     */
    public String countToString (RaceTypesDAO.countResults results) {
        return "Race type: " + results.raceName + '\n' +
                "Runner: " + results.name + "    (" +
                "Training Count: " + results.trainingCount + '\n';
    }

    /**
     * This method takes the information entered and appends it to the existing log string
     * it consists of a text string showing the runners sorted by race type and pace
     */
    private void updateDisplayByPace() {
        // First, we use the repository to get all logs from the database and read them into an array
        ArrayList<RaceTypesDAO.paceResults> paceLogs = repository.getRacesByPace();

        // Edge case message to display in the event no exercises are in the log array
        if (paceLogs.isEmpty()) {
            binding.teamRunTextView.setText(R.string.no_logs);
        }

        // Next we iterate through the logs and append them to a long string
        StringBuilder sb = new StringBuilder();
        for (RaceTypesDAO.paceResults log : paceLogs) {
            sb.append(paceToString(log));
        }

        // Lastly, we display our string
        binding.teamRunTextView.setText(sb);
    }


    /**
     * This method takes the information entered and appends it to the existing log string
     * it consists of a text string showing the runners sorted by race type and pace
     */
    private void updateDisplayByCount() {
        // First, we use the repository to get all logs from the database and read them into an array
        ArrayList<RaceTypesDAO.countResults> countLogs = repository.getRacesByTrainingCount();

        // Edge case message to display in the event no exercises are in the log array
        if (countLogs.isEmpty()) {
            binding.teamRunTextView.setText(R.string.no_logs);
        }

        // Next we iterate through the logs and append them to a long string
        StringBuilder sb = new StringBuilder();
        for (RaceTypesDAO.countResults log : countLogs) {
            sb.append(countToString(log));
        }

        // Lastly, we display our string
        binding.teamRunTextView.setText(sb);
    }
}