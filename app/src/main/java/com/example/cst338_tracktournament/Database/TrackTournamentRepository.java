package com.example.cst338_tracktournament.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import com.example.cst338_tracktournament.Database.entities.RaceTypes;
import com.example.cst338_tracktournament.Database.entities.Users;
import com.example.cst338_tracktournament.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TrackTournamentRepository {
    private final UserDAO userDAO;
    private final RaceTypesDAO raceTypesDAO;
    private ArrayList<Users> allLogs;

    private static TrackTournamentRepository repository;

    public TrackTournamentRepository(Application application) {
        Log.i(MainActivity.Tag, "TrackTournamentRepository is calling getDatabase.");
        TrackTournamentDatabase db = TrackTournamentDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.raceTypesDAO = db.raceTypesDAO();
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

    /**
     * Retrieves user from table.
     * @param name user name
     * @return TrackTournamentLog
     */
    public LiveData<TrackTournamentLog> getUserByUserName(String name) {
        return trackTournamentDAO.getUserByUserName(name);
    }

    public TrackTournamentLog getUserByName(String name){
        return trackTournamentDAO.getUserByName(name);
    }

    public LiveData<TrackTournamentLog> getUserById(int userId){
        return  trackTournamentDAO.getUserByUserId(userId);
    }

    /**
     * Retrieves user from table.
     * @param name user name
     * @return TrackTournamentLog
     */
    public LiveData<TrackTournamentLog> getUserByUserName(String name) {
        return trackTournamentDAO.getUserByUserName(name);
    }

    public TrackTournamentLog getUserByName(String name){
        return trackTournamentDAO.getUserByName(name);
    }

    public LiveData<TrackTournamentLog> getUserById(int userId){
        return  trackTournamentDAO.getUserByUserId(userId);
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

}
