package com.example.cst338_tracktournament.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338_tracktournament.Database.entities.Users;

import java.util.List;

@Dao
public interface UserDAO {
    /**
     * Our primary insert method into the database. Replace record if it exists.
     * This is set up as a 'varargs' since we insert several default records on create
     * @param user one or more users to be added to the user database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Users... user);

    /**
     * Stored method to clear all records from the table
     */
    @Query("DELETE FROM " + TrackTournamentDatabase.LOG_IN_TABLE)
    void deleteAll();

    /**
     * Return a user object that matches the supplied name
     * @param name the name of the user to return
     * @return the user matching the supplied name
     */
    @Query("SELECT * FROM " + TrackTournamentDatabase.LOG_IN_TABLE + " WHERE name == :name")
    Users getUserByName(String name);

    @Query("SELECT * FROM " + TrackTournamentDatabase.LOG_IN_TABLE + " WHERE name == :name")
    LiveData<Users> getUserByUserName(String name);

    /**
     * Stored method to return all users in a list
     * @return a list of users
     */
    @Query("Select * from " + TrackTournamentDatabase.LOG_IN_TABLE)
    List<Users> getAllLogInRecords();

    @Query("SELECT * FROM " + TrackTournamentDatabase.LOG_IN_TABLE + " WHERE userId == :userId")
    LiveData<Users> getUserByUserId(int userId);
}
