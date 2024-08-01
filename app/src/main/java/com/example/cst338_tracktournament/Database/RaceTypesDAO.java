package com.example.cst338_tracktournament.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338_tracktournament.Database.entities.RaceTypes;

import java.util.List;

/**
 * This defines the Data Access Object (DAO) for Race Types.
 * The DAO is used to extract defined race lengths from the
 * raceTypes entity.
 * @author Jeremy Norvell
 * Date: 2024-07-31
 */
@Dao
public interface RaceTypesDAO {

    /**
     * Our primary insert method into the database. Replace record if it exists.
     * This is set up as a 'varargs' since we insert several default records on create
     * @param raceTypes one or more raceTypes object
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RaceTypes... raceTypes);

    /**
     * Stored method to clear all records from the table
     */
    @Query("DELETE FROM " + TrackTournamentDatabase.RACE_TYPE_TABLE)
    void deleteAll();

    /**
     * This is a simple query to return all records from the table
     * @return a list of RaceTypes
     */
    @Query("Select * from " + TrackTournamentDatabase.RACE_TYPE_TABLE)
    List<RaceTypes> getRaceTypes();
}
