package com.example.cst338_tracktournament.Database;

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

    public TrackTournamentRepository(TrackTournamentDAO trackTournamentDAO, ArrayList<TrackTournamentLog> allLogs) {
        this.trackTournamentDAO = trackTournamentDAO;
        this.allLogs = allLogs;
    }

    public ArrayList<TrackTournamentLog> getAllLogs(){
        Future<ArrayList<TrackTournamentLog>> future = TrackTournamentDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<TrackTournamentLog>>() {

            @Override
            public ArrayList<TrackTournamentLog> call() throws Exception {
                return trackTournamentDAO.getAllLogInRecords();
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

}
