package com.example.cst338_tracktournament.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338_tracktournament.Database.entities.UserTrainingLog;

import java.time.LocalDateTime;
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

    /**
     * This returns a LiveData object of our training runs for the supplied user
     * @param userId the user for the matching run
     * @param trainingDate the date of the training run
     * @param distance the distance in miles of the training run
     * @param time the time (in seconds) of the training run
     * @param isCompetition an indicator if the run was training or competition
     * @return the logged runs for that user
     */
    @Query("SELECT * FROM " + TrackTournamentDatabase.TRAINING_LOG_TABLE +
            " WHERE userId == :userId AND date == :trainingDate AND distance == :distance " +
            "   AND time == :time AND isCompetition == :isCompetition")
    UserTrainingLog getMatchingTrainingLog(int userId, LocalDateTime trainingDate, double distance, Integer time, boolean isCompetition);

    /**
     * This updates the fields of an existing training log
     * @param userId the user for the matching run
     * @param trainingDate the date of the training run
     * @param distance the distance in miles of the training run
     * @param time the time (in seconds) of the training run
     * @param isCompetition an indicator if the run was training or competition
     * @param newTrainingDate the date you wish to update the run to
     * @param newDistance the distance you wish to update to
     * @param newTime the time you wish to update to
     * @param newCompetition the new competition indicator you wish to update to
     */
    @Query("UPDATE " + TrackTournamentDatabase.TRAINING_LOG_TABLE +
            " SET date = :newTrainingDate, distance = :newDistance, time = :newTime, isCompetition = :newCompetition" +
            " WHERE userId == :userId AND date == :trainingDate AND distance == :distance " +
            "   AND time == :time AND isCompetition == :isCompetition")
    void updateMatchingTrainingLog(int userId,
                                              LocalDateTime trainingDate, double distance, Integer time, boolean isCompetition,
                                              LocalDateTime newTrainingDate, double newDistance, Integer newTime, boolean newCompetition);


    /**
     * This deletes a matching training log
     * @param userId the user for the matching run
     * @param trainingDate the date of the training run
     * @param distance the distance in miles of the training run
     * @param time the time (in seconds) of the training run
     * @param isCompetition an indicator if the run was training or competition
     */
    @Query("DELETE FROM " + TrackTournamentDatabase.TRAINING_LOG_TABLE +
            " WHERE userId == :userId AND date == :trainingDate AND distance == :distance " +
            "   AND time == :time AND isCompetition == :isCompetition")
    void deleteMatchingTrainingLog(int userId, LocalDateTime trainingDate, double distance, Integer time, boolean isCompetition);
}
