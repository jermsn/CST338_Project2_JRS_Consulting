package com.example.cst338_tracktournament.Database.viewHolders;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst338_tracktournament.Database.TrackTournamentRepository;
import com.example.cst338_tracktournament.Database.entities.UserTrainingLog;

import java.util.List;

/**
 * This follows the viewModel guide at
 * <a href="https://developer.android.com/codelabs/android-room-with-a-view#9">...</a>
 */
public class TrackTournamentViewModel extends AndroidViewModel {

    private final TrackTournamentRepository repository;

    public TrackTournamentViewModel (Application application) {
        super(application);
        repository = TrackTournamentRepository.getRepository(application);
    }

    public LiveData<List<UserTrainingLog>> getAllLogsById(int userId) {
        return repository.getAllLogsByUserIdLiveData(userId);
    }

}
