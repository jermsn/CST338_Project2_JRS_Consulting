package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.Users;
import com.example.cst338_tracktournament.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TrackTournamentRepository repository;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.cst338_tracktournament.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.cst338_tracktournament.SHARED_PREFERENCE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.cst338_tracktournament.SHARED_PREFERENCE_USERID_VALUE";

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
                // insertTrackLogRecord();
                verifyUser();
                /*if(verifyUser()){
                    Intent intent = UserTraining.userTrainingActivityIntentFactory(getApplicationContext());
                    startActivity(intent);
                }
                else {

                    // 2024-07-31 Jeremy - Right now this is hardcoded to always jump to the Coach activities
                    //TODO: This needs to differentiate between user types in the future.
                    Intent intent = Coach_Activity.coachActivityIntentFactory(getApplicationContext());
                    startActivity(intent);


                }*/
            }
        });

        binding.newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewUserActivity.newUserActivityFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void insertTrackLogRecord(){
        Users log = new Users(mUserName,mPassword, "Runner");
        repository.insertTrackTournamentLog(log);
    }

    private void getInformationFromDisplay(){
        mUserName = binding.userNameInputEditText.getText().toString();
        mPassword = binding.passwordInputEditText.getText().toString();
    }

    private void verifyUser(){
        String username = binding.userNameInputEditText.getText().toString();
        if(username.isEmpty()){
            toastMaker("Username may not be blank");
            return;
        }
        LiveData<Users> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null){
                String password = binding.passwordInputEditText.getText().toString();
                if(password.equals(user.getPassword())){
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
                    sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY,user.getUserId());
                    sharedPrefEditor.apply();
                    startActivity(UserTraining.userTrainingActivityIntentFactory(getApplicationContext()));
                    toastMaker(String.format("Valid password for user id: %s", user.getUserId()));
                    binding.passwordInputEditText.setSelection(0);
                }else{
                    toastMaker("Invalid credentials");
                }
            }else {
                toastMaker(String.format("%s is not a valid username.", username));
                binding.userNameInputEditText.setSelection(0);
            }
        });
    }

    /**
     * This is the intent (a messaging object to request action from another app
     * component) factory for the Main Activity (log on screen) portion of application.
     * @param context the state of the active application
     * @return the intent of the main application
     */
    static Intent mainActivityFactory (Context context, int userId) {
        // return new Intent(context, MainActivity.class);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}