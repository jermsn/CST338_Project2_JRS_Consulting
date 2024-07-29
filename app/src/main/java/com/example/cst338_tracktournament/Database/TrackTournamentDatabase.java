package com.example.cst338_tracktournament.Database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.cst338_tracktournament.Database.entities.TrackTournamentLog;
import com.example.cst338_tracktournament.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TrackTournamentLog.class}, version = 1, exportSchema = false)
public abstract class TrackTournamentDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "TrackTournament_database";
    public static final String LOG_IN_TABLE = "logInTable";

    private static volatile TrackTournamentDatabase INSTANCE;
    //Only permit # threads of database
    private static final int NUMBER_OF_THREADS = 4;
    //Create the DB threads at startup
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Check DB Instances
    static TrackTournamentDatabase getDatabase(final Context context){
        if(INSTANCE == null) {
            //make sure nothing else is working on thread
            synchronized (TrackTournamentDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TrackTournamentDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.Tag, "DATABASE CREATED");
            //Lamda function to add default users
            //TODO: add databaseWriteExecutor.execute(() -> {..}
        }
    };

}
