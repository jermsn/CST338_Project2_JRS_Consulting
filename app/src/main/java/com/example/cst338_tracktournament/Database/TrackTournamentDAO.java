package com.example.cst338_tracktournament.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338_tracktournament.Database.entities.RaceTypes;
import com.example.cst338_tracktournament.Database.entities.TrackTournamentLog;

import java.util.List;

@Dao
public interface TrackTournamentDAO {
    /**
     * Our primary insert method into the database. Replace record if it exists.
     * This is set up as a 'varargs' since we insert several default records on create
     * @param trackTournament one or more users to be added to the user database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrackTournamentLog... trackTournament);

    /**
     * Stored method to clear all records from the table
     */
    @Query("DELETE FROM " + TrackTournamentDatabase.LOG_IN_TABLE)
    void deleteAll();

    /**
     * Stored method to return all users in a list
     * @return a list of users
     */
    @Query("Select * from " + TrackTournamentDatabase.LOG_IN_TABLE)
    List<TrackTournamentLog> getAllLogInRecords();

    @Query("SELECT * FROM " + TrackTournamentDatabase.LOG_IN_TABLE + " WHERE name == :name")
    LiveData<TrackTournamentLog> getUserByUserName(String name);

    @Query("SELECT * FROM " + TrackTournamentDatabase.LOG_IN_TABLE + " WHERE name == :name")
    TrackTournamentLog getUserByName(String name);

    @Query("SELECT * FROM " + TrackTournamentDatabase.LOG_IN_TABLE + " WHERE userId == :userId")
    LiveData<TrackTournamentLog> getUserByUserId(int userId);

}
