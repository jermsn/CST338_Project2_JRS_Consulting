package com.example.cst338_tracktournament.Database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

@Entity(tableName = "trainingLogTable")
public class UserTrainingLog {
    @PrimaryKey(autoGenerate = true)
    private int trainingId;

    private int userId;
    private LocalDateTime date;
    private double distance;
    private Integer time;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Primary constructor for this entity
     * @param userId the numeric user ID from the Users table
     * @param date the date of the run
     * @param distance the miles run; accepts decimals
     * @param time the amount of time the run took in seconds
     * @param isCompetition boolean indicator of whether the run was a race or not.
     */
    public UserTrainingLog(int userId, LocalDateTime date, double distance, Integer time, boolean isCompetition) {
        this.userId = userId;
        this.date = date;
        this.distance = distance;
        this.time = time;
        this.isCompetition = isCompetition;
    }

    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
     * <a href="https://developer.android.com/reference/java/sql/Timestamp">...</a>
     * @return Timestamp
     */
    public Integer getTime() {
        return time;
    }

    /**
     * Sets this Timestamp object to represent a point in time that is
     * time milliseconds after January 1, 1970 00:00:00 GMT
     * <a href="https://developer.android.com/reference/java/sql/Timestamp">...</a>
     * @param time in milliseconds
     */
    public void setTime(Integer time) {
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
        return trainingId == that.trainingId && userId == that.userId && Double.compare(distance, that.distance) == 0 && isCompetition == that.isCompetition && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainingId(), getUserId(), getDate(), getDistance(), getTime(), isCompetition());
    }


    @NonNull
    @Override
    public String toString() {
        String runType = isCompetition ? "Competition" : "Training";
        Integer pace = (int) (time/distance);
        return  date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) + '\n' +
                "Distance: " + distance + '\n' +
                "Time: " + secondsToStringTime(time) + " Pace: " + secondsToStringTime(pace) + '\n' +
                runType + '\n';
    }


    /**
     * This method is used to display the number of seconds as a HH:MM:SS variable
     * @param totalSecs an Integer representing the number of seconds
     * @return a string showing the elapsed time in HH:MM:SS format
     */
    private String secondsToStringTime (Integer totalSecs) {
        Integer hours = totalSecs / 3600;
        Integer minutes = (totalSecs % 3600) / 60;
        Integer seconds = totalSecs % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
