package com.example.cst338_tracktournament.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = "trainingLogTable")
public class UserTrainingLog {
    @PrimaryKey(autoGenerate = true)
    private int trainingId;

    private int userId;
    private LocalDateTime date;
    private float distance;
    private Timestamp time;
    private boolean isCompetition;


    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
     * <a href="https://developer.android.com/reference/java/sql/Timestamp">...</a>
     * @return Timestamp
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * Sets this Timestamp object to represent a point in time that is
     * time milliseconds after January 1, 1970 00:00:00 GMT
     * <a href="https://developer.android.com/reference/java/sql/Timestamp">...</a>
     * @param time in milliseconds
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isCompetition() {
        return isCompetition;
    }

    public void setCompetition(boolean competition) {
        isCompetition = competition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTrainingLog that = (UserTrainingLog) o;
        return getTrainingId() == that.getTrainingId() && getUserId() == that.getUserId() && Float.compare(getDistance(), that.getDistance()) == 0 && isCompetition() == that.isCompetition() && Objects.equals(getDate(), that.getDate()) && Objects.equals(getTime(), that.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainingId(), getUserId(), getDate(), getDistance(), getTime(), isCompetition());
    }
}
