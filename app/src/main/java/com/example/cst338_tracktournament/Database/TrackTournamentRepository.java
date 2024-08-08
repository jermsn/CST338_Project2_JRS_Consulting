package com.example.cst338_tracktournament.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import com.example.cst338_tracktournament.Database.entities.RaceTypes;
import com.example.cst338_tracktournament.Database.entities.UserTrainingLog;
import com.example.cst338_tracktournament.Database.entities.Users;
import com.example.cst338_tracktournament.MainActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TrackTournamentRepository {
    private final UserDAO userDAO;
    private final RaceTypesDAO raceTypesDAO;
    private final UserTrainingDAO userTrainingDAO;
    private ArrayList<Users> allLogs;

    private static TrackTournamentRepository repository;

    public TrackTournamentRepository(Application application) {
        Log.i(MainActivity.Tag, "TrackTournamentRepository is calling getDatabase.");
        TrackTournamentDatabase db = TrackTournamentDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.raceTypesDAO = db.raceTypesDAO();
        this.userTrainingDAO = db.trainingLogDAO();
        this.allLogs = (ArrayList<Users>) this.userDAO.getAllLogInRecords();
    }

    public static TrackTournamentRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<TrackTournamentRepository> future = TrackTournamentDatabase.databaseWriteExecutor.submit(
                new Callable<TrackTournamentRepository>() {
                    @Override
                    public TrackTournamentRepository call() throws Exception {
                        return new TrackTournamentRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.Tag,"Problem getting TrackTournamentRepository, thread error.");
        }
        return null;
    }

    public ArrayList<Users> getAllLogs(){
        Future<ArrayList<Users>> future = TrackTournamentDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Users>>() {

            @Override
            public ArrayList<Users> call() throws Exception {
                return (ArrayList<Users>) userDAO.getAllLogInRecords();
            }
        });
        try{
            return  future.get();
        }catch (InterruptedException | ExecutionException e){
            // e.printStackTrace();
            Log.i(MainActivity.Tag,"Problem when getting all TrackTournamentLog in the repository");
        }

        return null;
    }

    public void insertTrackTournamentLog(Users user){
        TrackTournamentDatabase.databaseWriteExecutor.execute(() ->
                userDAO.insert(user));
    }

    public void insertUserTrainingLog(UserTrainingLog userTraining){
        TrackTournamentDatabase.databaseWriteExecutor.execute(() ->
               userTrainingDAO.insert(userTraining));
    }



    /**
     * Retrieves user from table.
     * @param name user name
     * @return TrackTournamentLog
     */
    public LiveData<Users> getUserByUserName(String name) {
        return userDAO.getUserByUserName(name);
    }

    public Users getUserByName(String name){
        return userDAO.getUserByName(name);
    }

    public LiveData<Users> getUserById(int userId){
        return userDAO.getUserByUserId(userId);
    }


    /**
     * This is our method to insert a new RaceType into the database
     * @param raceTypes a race name and length definitions to be added
     */
    public void insertRaceType(RaceTypes raceTypes) {
        TrackTournamentDatabase.databaseWriteExecutor.execute(()->
        {
            raceTypesDAO.insert(raceTypes);
        });
    }

    /**
     * Method to return logs for a single user ID
     * @param loggedInUserId the a userId which you want to retrieve the logs for
     * @return a LiveData list of GymLogs
     */
    public LiveData<List<UserTrainingLog>> getAllLogsByUserIdLiveData(int loggedInUserId) {
        return userTrainingDAO.getRecordsByUserIdLiveData(loggedInUserId);
    }

    /**
     * Method to a race matching the supplied parameters
     * @param userId the user for the matching run
     * @param trainingDate the date of the training run
     * @param distance the distance in miles of the training run
     * @param time the time (in seconds) of the training run
     * @param isCompetition an indicator if the run was training or competition
     * @return the logged runs for that user
     */
    public UserTrainingLog getMatchingTrainingLog(int userId, LocalDateTime trainingDate, double distance, Integer time, boolean isCompetition) {
        return userTrainingDAO.getMatchingTrainingLog(userId, trainingDate, distance, time, isCompetition);
    }

    /**
     * This uses a Future to and returns a list of race types and racers sorted by pace
     * @return ArrayList of type RaceTypesDAO.paceResults
     */
    public ArrayList<RaceTypesDAO.paceResults> getRacesByPace() {
        Future<ArrayList<RaceTypesDAO.paceResults>> future = TrackTournamentDatabase.databaseWriteExecutor.submit(
                // This is an interface, we are calling it in-line, which is called an
                // anonymous inner class (note the call right below)
                new Callable<ArrayList<RaceTypesDAO.paceResults>>() {
                    @Override
                    public ArrayList<RaceTypesDAO.paceResults> call() throws Exception {
                        return (ArrayList<RaceTypesDAO.paceResults>) raceTypesDAO.getRacesByPace();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.Tag, "Problem when getting races by pace from the repository." );
        }
        return null;
    }

    /**
     * This uses a Future to and returns a list of race types and racers sorted by pace
     * @return ArrayList of type RaceTypesDAO.countResults
     */
    public ArrayList<RaceTypesDAO.countResults> getRacesByTrainingCount() {
        Future<ArrayList<RaceTypesDAO.countResults>> future = TrackTournamentDatabase.databaseWriteExecutor.submit(
                // This is an interface, we are calling it in-line, which is called an
                // anonymous inner class (note the call right below)
                new Callable<ArrayList<RaceTypesDAO.countResults>>() {
                    @Override
                    public ArrayList<RaceTypesDAO.countResults> call() throws Exception {
                        return (ArrayList<RaceTypesDAO.countResults>) raceTypesDAO.getRacesByTrainingCount();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.Tag, "Problem when getting races by pace from the repository." );
        }
        return null;
    }

    /**
     * Retrieves a race from the table that matches the supplied name.
     * @param name race name
     * @return a RaceType
     */
    public RaceTypes getRaceByName(String name) {
        return raceTypesDAO.getRaceByName(name);
    }

    /**
     * Retrieves a race from the table that matches the supplied name.
     * @param oldName the race name to be replaced
     * @param newName the new race name to be assigned in its place
     */
    public void updateRaceByName(String oldName, String newName) {}

    /**
     * Deletes a race from the table that matches the supplied name.
     * @param name the race name to be deleted
     */
    public void deleteRaceByName(String name) {}


}
