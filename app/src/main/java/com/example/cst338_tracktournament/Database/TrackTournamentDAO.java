package com.example.cst338_tracktournament.Database;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.cst338_tracktournament.Database.entities.TrackTournamentLog;

import java.util.ArrayList;

public interface TrackTournamentDAO {
    //Replace record if it exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrackTournamentLog trackTournament);

    @Query("Select * from " + TrackTournamentDatabase.LOG_IN_TABLE)
    ArrayList<TrackTournamentLog> getAllLogInRecords();
}
