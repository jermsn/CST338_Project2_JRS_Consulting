package com.example.cst338_tracktournament.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.cst338_tracktournament.Database.entities.UserLog;

import java.util.List;

@Dao
public interface TrackTournamentDAO {
    //Replace record if it exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserLog trackTournament);

    @Query("Select * from " + TrackTournamentDatabase.LOG_IN_TABLE)
    List<UserLog> getAllLogInRecords();
}
