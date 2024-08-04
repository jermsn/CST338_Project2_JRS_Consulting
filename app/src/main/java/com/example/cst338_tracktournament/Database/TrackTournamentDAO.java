package com.example.cst338_tracktournament.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.cst338_tracktournament.Database.entities.TrackTournamentLog;

import java.util.List;

@Dao
public interface TrackTournamentDAO {
    //Replace record if it exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrackTournamentLog trackTournament);

    @Query("Select * from " + TrackTournamentDatabase.LOG_IN_TABLE)
    List<TrackTournamentLog> getAllLogInRecords();

    @Query("SELECT * FROM " + TrackTournamentDatabase.LOG_IN_TABLE + " WHERE name == :name")
    LiveData<TrackTournamentLog> getUserByUserName(String name);

    @Query("SELECT * FROM " + TrackTournamentDatabase.LOG_IN_TABLE + " WHERE name == :name")
    TrackTournamentLog getUserByName(String name);

    @Query("SELECT * FROM " + TrackTournamentDatabase.LOG_IN_TABLE + " WHERE userId == :userId")
    LiveData<TrackTournamentLog> getUserByUserId(int userId);

}
