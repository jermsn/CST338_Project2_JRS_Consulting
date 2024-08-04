package com.example.cst338_tracktournament;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Observable;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.Users;
import com.example.cst338_tracktournament.databinding.ActivityNewUserBinding;

import java.util.ArrayList;

public class NewUserActivity extends AppCompatActivity {
    private ActivityNewUserBinding binding;
    private TrackTournamentRepository repository;
    private ArrayList<Users> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TrackTournamentRepository.getRepository(getApplication());
        assert repository != null;
        userList = repository.getAllLogs();

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Invalid entry detected
                if(isValidUserName() || !passwordsMatch()){
                    return;
                }
                //Add user to the database;
                createNewUser();
            }
        });

        binding.cancelNewUserButton.setOnClickListener(new View.OnClickListener() {
            int userId = 0;
            @Override
            public void onClick(View v) {
                // Launch the logon intent factory
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext(),userId);
                startActivity(intent);
            }
        });

    }

    /**
     * Check to see if user name is already exists in the database
     * @return boolean
     */
    private boolean isValidUserName(){
        final ObservableBoolean userExists = new ObservableBoolean();

        //Get user name from the New User interface
         String newUserName = binding.newUserNameInputEditText.getText().toString();
         //User has not entered a username
         if(newUserName.isEmpty()){
             showMessageDialog("Enter a user name.");
             //toastMaker(String.format("User %s already exists.", newUserName));
             return false;
         }

         for(Users log : userList)
         {
             if(log.getName().equals(newUserName)) {
                 showMessageDialog(String.format("User %s already exists.", newUserName));
                 return true;
             }

         }
         return false;

    }

    /**
     * Verify user entered correct values in password and verify password fields
     * @return boolean
     */
    private boolean passwordsMatch(){
        String newPassword = binding.newPasswordInputEditText.getText().toString();
        String verifyPassword = binding .verifyPasswordInputEditText.getText().toString();

        if(!newPassword.equals(verifyPassword)){
            showMessageDialog("Passwords do not match!");
            return false;
        }

        return true;
    }

    private void createNewUser(){
        //Get user name and password from the New User interface
        String newUserName = binding.newUserNameInputEditText.getText().toString();
        String newPassword = binding.newPasswordInputEditText.getText().toString();

        Users log = new Users(newUserName,newPassword, "runner");
        repository.insertTrackTournamentLog(log);
        //Go to login screen for user to proceed.
        binding.cancelNewUserButton.callOnClick();
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Display message box to user when invalid entry is performed
     * @param message to display to user
     */
    private void showMessageDialog(String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder( NewUserActivity.this);
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
     * component) factory for the New User Activity (log on screen) portion of application.
     * @param context the state of the active application
     * @return the intent of the main application
     */
    static Intent newUserActivityFactory (Context context) {
        return new Intent(context, NewUserActivity.class);
    }
}