package com.example.cst338_tracktournament;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.LiveData;
import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.UserTrainingLog;
import com.example.cst338_tracktournament.Database.entities.Users;
import com.example.cst338_tracktournament.Database.typeConverters.LocalDateTimeConverter;
import com.example.cst338_tracktournament.databinding.ActivityNewTrainingForUserBinding;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

/**
 *  Defines the functionality to insert a user's training into the
 *  UserTrainingLog table
 *  @author Steven Jackson
 *  Date: 2024-08-06
 */
public class NewTrainingForUser extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.cst338_tracktournament.MAIN_ACTIVITY_USER_ID";
    private ActivityNewTrainingForUserBinding binding;
    private TrackTournamentRepository repository;
    private int userId;

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
                addTrainingRecord();
            }
        });


    }

    /**
     * Displays the user name in the pages title.
     */
    private void displayUserNameInWindowTitle(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);

        // Launch the logon intent factory
        userId = sharedPreferences.getInt(MainActivity.SHARED_PREFERENCE_USERID_KEY,0);

        LiveData<Users> userObserver = repository.getUserById(userId);

        userObserver.observe(this, user -> {
            if(user != null) {

                binding.newTrainingForUserLabelTextView.setText(String.format("New Training for\n%s", user.getName()));
            }
        });
    }

    /**
     * Insert user training record in to UserTrainingLog table.
     */
    private void addTrainingRecord(){
        LocalDateTime trainingDate;// = LocalDateTime.parse(String.format("%sT00:00:00.000",binding.newTrainingDateInputEditText.getText().toString()));
        double trainingDistance;
        int trainingTime;
        boolean trainingCompetition = binding.newTrainingCompetitionCheckBox.isChecked();

        try{
            trainingDate = LocalDateTime.parse(String.format("%sT00:00:00.000",binding.newTrainingDateInputEditText.getText().toString()));
        }catch(DateTimeException e){
            //Log.i(MainActivity.Tag,String.format("Date must be enter as %s",binding.newTrainingDateInputEditText.getHint()));
            showMessageDialog(String.format("Date must be enter as %s",binding.newTrainingDateInputEditText.getHint()));
            return;
        }

        try{
            trainingDistance = Double.parseDouble(binding.newTrainingDistanceInputEditText.getText().toString());
         }catch (NumberFormatException e){
            //Log.i(MainActivity.Tag,"Distance field is empty");
            showMessageDialog("Distance field is empty");
            return;
        }

        try{
            trainingTime = Integer.parseInt(binding.newTrainingTimeInputEditText.getText().toString());
        }catch (Exception e){
            //Log.i(MainActivity.Tag,"Time field is empty");
            showMessageDialog("Time field is empty");
            return;
        }

        UserTrainingLog trainingLog = new UserTrainingLog(userId, trainingDate,trainingDistance,trainingTime,trainingCompetition);
        repository.insertUserTrainingLog(trainingLog);

    }

    /**
     * Display message box to user when invalid entry is performed
     * @param message to display to user
     */
    private void showMessageDialog(String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder( NewTrainingForUser.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage(message);

        alertBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
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