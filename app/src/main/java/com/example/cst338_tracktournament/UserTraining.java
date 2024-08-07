package com.example.cst338_tracktournament;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.Users;
import com.example.cst338_tracktournament.Database.viewHolders.TrackTournamentAdapter;
import com.example.cst338_tracktournament.Database.viewHolders.TrackTournamentViewModel;
import com.example.cst338_tracktournament.databinding.ActivityUserTrainingBinding;

import java.util.List;

public class UserTraining extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.cst338_tracktournament.MAIN_ACTIVITY_USER_ID";
    private ActivityUserTrainingBinding binding;
    private TrackTournamentRepository repository;
    private TrackTournamentViewModel trackTournamentViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserTrainingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        trackTournamentViewModel = new ViewModelProvider(this).get(TrackTournamentViewModel.class);

        // This sets up our recycler
        RecyclerView recyclerView = binding.logDisplayRecyclerView;
        final TrackTournamentAdapter adapter = new TrackTournamentAdapter(new TrackTournamentAdapter.TrainingLogDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));


        repository = TrackTournamentRepository.getRepository(getApplication());
        displayUserNameInWindowTitle();

        // This changes the color and enables our admin/coach functions
        setCoachButton();

        // Display historical training in the recycler window
        // TODO: User ID is currently hardcoded. This needs to be derived from the logged in user ID
        int loggedInUserId = 1;
        trackTournamentViewModel.getAllLogsById(loggedInUserId).observe(this, trainingLogs -> {
            adapter.submitList(trainingLogs);
        });

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
                Intent intent =  NewTrainingForUser.newTrainingForUserActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        /*
          This configures the coach/admin button to take us to our privileged activities
         */
        binding.coachTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the logon intent factory
                Intent intent = Coach_Activity.coachActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.userTrainingLabelTextView.setMovementMethod(new ScrollingMovementMethod());

    }

    private void displayUserNameInWindowTitle(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);

        // Launch the logon intent factory
        int userId = sharedPreferences.getInt(MainActivity.SHARED_PREFERENCE_USERID_KEY,0);

        LiveData<Users> userObserver = repository.getUserById(userId);

        userObserver.observe(this, user -> {
            if(user != null) {

                binding.userTrainingLabelTextView.setText(String.format("%s's\nTrainings", user.getName()));
            }
        });
    }

    /**
     * This method makes our coach/admin button available for privileged users
     * by enabling and using visible colors. Otherwise, it's faded into the background
     * and disabled.
     */
    private void setCoachButton(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);

        // Launch the logon intent factory
        int userId = sharedPreferences.getInt(MainActivity.SHARED_PREFERENCE_USERID_KEY,0);

        LiveData<Users> userObserver = repository.getUserById(userId);

        userObserver.observe(this, user -> {
            Log.i(MainActivity.Tag, "User " + user.getName() + " is a " + user.getUserType());
            if(user.getUserType().equals("Coach")) {
                Log.i(MainActivity.Tag, "Drawing the admin button.");
                // We are going to use Color.parseColor to get us the integer
                // value for the colors specified in colors.xml
                int textColor = Color.parseColor("#000000");
                int backColor = Color.parseColor("#fff2cc");
                binding.coachTrainingButton.setEnabled(true);
                binding.coachTrainingButton.setTextColor(textColor);
                binding.coachTrainingButton.setBackgroundTintList(ColorStateList.valueOf(backColor));
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