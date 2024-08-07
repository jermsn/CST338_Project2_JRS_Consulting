package com.example.cst338_tracktournament.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338_tracktournament.Database.entities.UserTrainingLog;

import java.util.List;

@Dao
public interface UserTrainingDAO {

    /**
     * Our primary insert method into the table. Replace record if it exists.
     * This is set up as a 'varargs' since we insert several default records on create
     * @param trainingLog one or more training logs to be added to the user database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserTrainingLog... trainingLog);

    /**
     * Stored method to clear all records from the table
     */
    @Query("DELETE FROM " + TrackTournamentDatabase.TRAINING_LOG_TABLE)
    void deleteAll();

    /**
     * This returns a LiveData object of our training runs for the supplied user
     * @param loggedInUserId the user you wish to display
     * @return the logged runs for that user
     */
    @Query("SELECT * FROM " + TrackTournamentDatabase.TRAINING_LOG_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    LiveData<List<UserTrainingLog>> getRecordsByUserIdLiveData(int loggedInUserId);

    //TODO Create remaining select statements to query the TrainingLog and RaceTypes tables.
}
