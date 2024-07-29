package com.example.cst338_tracktournament;

import android.os.Bundle;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cst338_tracktournament.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static final String Tag = "JRS_TRACKLOG";

    String mUserName = "";
    String mPassword = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
            }
        });
    }

    private void getInformationFromDisplay(){
        mUserName = binding.userNameInputEditText.getText().toString();
        mPassword = binding.passwordInputEditText.getText().toString();
    }
}