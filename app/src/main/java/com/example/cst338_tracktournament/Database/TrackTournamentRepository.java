package com.example.cst338_tracktournament.Database;

import android.app.Application;
import android.util.Log;
import com.example.cst338_tracktournament.Database.entities.TrackTournamentLog;
import com.example.cst338_tracktournament.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TrackTournamentRepository {
    private TrackTournamentDAO trackTournamentDAO;
    private ArrayList<TrackTournamentLog> allLogs;

    private static TrackTournamentRepository repository;

    public TrackTournamentRepository(Application application) {
        TrackTournamentDatabase db = TrackTournamentDatabase.getDatabase(application);
        this.trackTournamentDAO = db.trackTournamentDAO();
        this.allLogs = (ArrayList<TrackTournamentLog>) this.trackTournamentDAO.getAllLogInRecords();
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
            Log.i(MainActivity.Tag,"Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public ArrayList<TrackTournamentLog> getAllLogs(){
        Future<ArrayList<TrackTournamentLog>> future = TrackTournamentDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<TrackTournamentLog>>() {

            @Override
            public ArrayList<TrackTournamentLog> call() throws Exception {
                return (ArrayList<TrackTournamentLog>) trackTournamentDAO.getAllLogInRecords();
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

    public void insertTrackTournamentLog(TrackTournamentLog tournamentLog){
        TrackTournamentDatabase.databaseWriteExecutor.execute(() ->
                trackTournamentDAO.insert(tournamentLog));
    }

}
