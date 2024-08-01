package com.example.cst338_tracktournament.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338_tracktournament.Database.TrackTournamentDatabase;

import java.util.Objects;


/**
 * This class defines race types with a minimum and maximum distance
 * which will be used to define training runs for displayed metrics
 * @author Jeremy Norvel
 * Date: 2024-07-30
 */
@Entity(tableName = TrackTournamentDatabase.RACE_TYPE_TABLE)
public class RaceTypes {

    @PrimaryKey(autoGenerate = true)
    private int raceId;

    private String raceName;
    private double minimumDistance;
    private double maximumDistance;

    /**
     * This is our primary constructor which will be used to insert records into the database
     * @param raceName a name for the specific race or class of race (e.g. NY Marathon, 10k, etc.)
     * @param minimumDistance the minimum training distance to be considered relevant for that race
     * @param maximumDistance the maximum training distance to be considered relevant for that race
     */
    public RaceTypes(String raceName, double minimumDistance, double maximumDistance) {
        this.raceName = raceName;
        this.minimumDistance = minimumDistance;
        this.maximumDistance = maximumDistance;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public double getMinimumDistance() {
        return minimumDistance;
    }

    public void setMinimumDistance(double minimumDistance) {
        this.minimumDistance = minimumDistance;
    }

    public double getMaximumDistance() {
        return maximumDistance;
    }

    public void setMaximumDistance(double maximumDistance) {
        this.maximumDistance = maximumDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RaceTypes raceTypes = (RaceTypes) o;
        return raceId == raceTypes.raceId && Double.compare(minimumDistance, raceTypes.minimumDistance) == 0 && Double.compare(maximumDistance, raceTypes.maximumDistance) == 0 && Objects.equals(raceName, raceTypes.raceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raceId, raceName, minimumDistance, maximumDistance);
    }
}
