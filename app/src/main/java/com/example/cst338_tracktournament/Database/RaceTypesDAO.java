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

    /**
     * This is a simple query to records matching single race name
     * @return a list of RaceTypes
     */
    @Query("Select * from " + TrackTournamentDatabase.RACE_TYPE_TABLE + " WHERE raceName == :name")
    RaceTypes getRaceByName(String name);

    /**
     * This is a simple query to update the race name
     *
     * @return
     */
    @Query("UPDATE " + TrackTournamentDatabase.RACE_TYPE_TABLE + " SET raceName = :newName WHERE raceName == :oldName")
    Void updateRaceByName(String oldName, String newName);

    /**
     * This is a simple query to delete races by name
     *
     * @return
     */
    @Query("DELETE FROM " + TrackTournamentDatabase.RACE_TYPE_TABLE + " WHERE raceName == :name")
    Void deleteRaceByName(String name);


    /**
     * We're going to define an inner class as a container for our race results.
     */
    class paceResults {
        public String raceName;
        public String name;
        public double avgPace;
    }

    /**
     * We're going to define an inner class as a container for our training counts.
     */
    class countResults {
        public String raceName;
        public String name;
        public Integer trainingCount;
    }


    /**
     * This is a simple query to return all records from the table
     * This sorts first by minimum race distance and then the average pace.
     * @return a list of RaceTypes
     */
    @Query("SELECT raceName, name, avg(pace) as avgPace " +
            "FROM (" +
            "   SELECT users.name, race.raceName, race.minimumDistance, train.time/train.distance as pace " +
            "   FROM " + TrackTournamentDatabase.TRAINING_LOG_TABLE + " as train " +
            "   INNER JOIN " + TrackTournamentDatabase.LOG_IN_TABLE +" as users " +
            "       on train.userId = users.userId " +
            "   INNER JOIN " + TrackTournamentDatabase.RACE_TYPE_TABLE + " as race " +
            "       on race.minimumDistance <= train.distance and race.maximumDistance >= train.distance" +
            "       ) " +
            "GROUP BY raceName, name ORDER BY minimumDistance, avgPace " )
    List<paceResults> getRacesByPace();


    /**
     * This is a simple query to return all records from the table
     * This sorts first by minimum race distance and by the descending number of races trained.
     * @return a list of RaceTypes
     */
    @Query("SELECT raceName, name, COUNT(minimumDistance) as trainingCount " +
            "FROM (" +
            "   SELECT users.name, race.raceName, race.minimumDistance " +
            "   FROM " + TrackTournamentDatabase.TRAINING_LOG_TABLE + " as train " +
            "   INNER JOIN " + TrackTournamentDatabase.LOG_IN_TABLE +" as users " +
            "       on train.userId = users.userId " +
            "   INNER JOIN " + TrackTournamentDatabase.RACE_TYPE_TABLE + " as race " +
            "       on race.minimumDistance <= train.distance and race.maximumDistance >= train.distance" +
            "       ) " +
            "GROUP BY raceName, name ORDER BY minimumDistance, trainingCount DESC " )
    List<countResults> getRacesByTrainingCount();
}
