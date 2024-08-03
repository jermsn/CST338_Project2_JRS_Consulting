package com.example.cst338_tracktournament.Database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cst338_tracktournament.Database.entities.RaceTypes;
import com.example.cst338_tracktournament.Database.entities.Users;
import com.example.cst338_tracktournament.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Users.class, RaceTypes.class}, version = 4, exportSchema = false)
public abstract class TrackTournamentDatabase extends RoomDatabase {
    // Note to future self: none of these can have underscores. The database will not instantiate
    // but no error will be generated.
    private static final String DATABASE_NAME = "TrackTournamentDatabase";
    public static final String LOG_IN_TABLE = "logInTable";
    public static final String RACE_TYPE_TABLE = "raceTypeTable";

    private static volatile TrackTournamentDatabase INSTANCE;
    //Only permit # threads of database
    private static final int NUMBER_OF_THREADS = 4;
    //Create the DB threads at startup
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Check DB Instances
    static TrackTournamentDatabase getDatabase(final Context context){
        Log.i(MainActivity.Tag, "getDatabase has been called.");
        // The command below can be uncommented and run to fully rebuild the database, including default values
        //context.deleteDatabase(DATABASE_NAME);
        if(INSTANCE == null) {
            //make sure nothing else is working on thread
            synchronized (TrackTournamentDatabase.class) {
                if(INSTANCE == null) {
                    Log.i(MainActivity.Tag, "There is no instance, triggering the databaseBuilder.");
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TrackTournamentDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                    Log.i(MainActivity.Tag, "getDatabase has created a new instance which will be returned.");
                }
            }
        }
        return INSTANCE;
    }

    /**
     * The addDefaultValues method is called when the database must be rebuilt.
     * We use this to populate default values for our tables to assist with building
     * or demonstrating functionality. Default values for each table are called separately
     * so they may be individually commented out if needed.
     */
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.Tag, "Database Created");
            //Lambda function to add default records
            databaseWriteExecutor.execute(() -> {
                // Add the default users
                userDefaults();
                // Add the default raceTypes
                raceTypeDefaults();
            });
        }
    };

    /**
     * This populates four sample users (three runners and one coach) into the database
     * for build and demonstration purposes. We will assume that these are added in order
     * and the user DB will give these users 1,2,3,4 on insert.
     */
    private static void userDefaults() {
        // Assign the related DAO to our database instance
        UserDAO userDao = INSTANCE.userDAO();
        // Empty out any existing records
        userDao.deleteAll();
        Log.i(MainActivity.Tag,"Removed any existing records from the User table");
        // Create a few new users
        Users user1 = new Users("Jeremy", "password", "User");
        Users user2 = new Users("Rasna", "password", "User");
        Users user3 = new Users("Steven", "password", "User");
        Users coach1 = new Users("MrBuzzcut", "password", "Coach");
        userDao.insert(user1, user2, user3, coach1);
        Log.i(MainActivity.Tag, "Default users entered to the TrackTournamentLog table");
    }

    /**
     * This populates common race types into the RaceTypes table as a starting utility
     */
    private static void raceTypeDefaults() {
        // Assign the related DAO to our database instance
        RaceTypesDAO raceDao = INSTANCE.raceTypesDAO();
        // Empty out any existing records
        raceDao.deleteAll();
        Log.i(MainActivity.Tag, "Removed any existing records from the RaceTypes table.");
        // Create a few default race distances
        RaceTypes fiveK = new RaceTypes("5 Kilometer", 2.9, 3.3);
        RaceTypes tenK = new RaceTypes("10 Kilometer", 6.0, 6.4);
        RaceTypes halfMarathon = new RaceTypes("Half Marathon", 12.8, 13.5);
        RaceTypes marathon = new RaceTypes("Marathon", 25.0, 29.0);
        raceDao.insert(fiveK, tenK, halfMarathon, marathon);
        Log.i(MainActivity.Tag, "Default race types added to race type table.");
    }

    // Define an abstract method to tie our User DAO to the database
    public abstract UserDAO userDAO();

    // Define an abstract method to tie our Race Types DAO to the database
    public abstract RaceTypesDAO raceTypesDAO();
}
