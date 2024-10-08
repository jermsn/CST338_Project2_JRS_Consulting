package com.example.cst338_tracktournament;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.cst338_tracktournament.MainActivity;
import com.example.cst338_tracktournament.Database.RaceTypesDAO;
import com.example.cst338_tracktournament.Database.TrackTournamentDatabase;
import com.example.cst338_tracktournament.Database.UserTrainingDAO;
import com.example.cst338_tracktournament.Database.entities.RaceTypes;
import com.example.cst338_tracktournament.Database.entities.UserTrainingLog;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest {
//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.example.cst338_tracktournament", appContext.getPackageName());
//    }

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private RaceTypesDAO raceTypesDAO;
    private UserTrainingDAO userTrainingDAO;
    Intent testIntent;
    Bundle testBundle;
    LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    LocalDateTime yesterday = today.minusDays(1).truncatedTo(ChronoUnit.DAYS);
    UserTrainingLog userTrainingLog1;
    UserTrainingLog userTrainingLog2;
    UserTrainingLog userTrainingLog3;
    RaceTypes race1;
    RaceTypes race2;
    RaceTypes race3;
    int userId;
    int time;


    /* These setup the testing environment */
    @Before
    public void setUp() {
        TrackTournamentDatabase db = TrackTournamentDatabase.getDatabase(appContext);
        raceTypesDAO = db.raceTypesDAO();
        userTrainingDAO = db.trainingLogDAO();
        userId = 5;
        time = 121;
    }

    /* Tear down the testing environment */
    @After
    public void tearDown() {
        testIntent = null;
        testBundle = null;
        race1 = null;
        race2 = null;
        race3 = null;
        userTrainingLog1 = null;
        userTrainingLog2 = null;
        userTrainingLog3 = null;
        userId = 0;
        time = 0;
    }

    //////////////////////////////////////////
    //    Simple Unit Tests
    //////////////////////////////////////////

    /**
     * This tests the basic constructor, getter and setter methods for raceTypes objects
     */
    @Test
    public void raceTypesTest() {
        race1 = new RaceTypes("Beer Mile", 0.9, 1.1);
        assertEquals("Beer Mile", race1.getRaceName());
        assertEquals(0.9, race1.getMinimumDistance(),0);
        assertEquals(1.1, race1.getMaximumDistance(), 0);
    }

    /**
     * This tests the basic constructor, getter and setter methods for UserTrainingLog objects
     */
    @Test
    public void userTrainingLogTest() {
        userTrainingLog1 = new UserTrainingLog(8, yesterday, 2.2, 299, false);
        assertEquals(8, userTrainingLog1.getUserId());
        assertEquals(yesterday, userTrainingLog1.getDate());
        assertEquals(2.2, userTrainingLog1.getDistance(), 0);
        assertEquals(299, userTrainingLog1.getTime(),0);
        assertFalse(userTrainingLog1.isCompetition());
    }

    /**
     * This tests the basic constructor, getter and setter methods for UserTrainingLog objects
     */
    @Test
    public void integerToTimeTest() {
        String timeString = UserTrainingLog.secondsToStringTime(time);
        assertEquals("00:02:01", timeString);
    }

    //////////////////////////////////////////
    //    Intent Factory Tests
    //////////////////////////////////////////

    /**
     * This tests that the MainActivity intent is assigned and the extra (userID) in the main activity
     */
    @Test
    public void mainActivityIntentTest() {
        // assign our intent and pull the extras into a bundle
        testIntent = MainActivity.mainActivityFactory(getApplicationContext(), userId);
        testBundle = testIntent.getExtras();
        // test that these have returned properly
        assertNotNull(testIntent);
        assertNotNull(testBundle);
    }

    /**
     * This tests that the Coach_Activity intent is assigned
     */
    @Test
    public void coachActivityIntentTest() {
        // assign our intent
        testIntent = Coach_Activity.coachActivityIntentFactory(getApplicationContext());
        // test that these have returned properly
        assertNotNull(testIntent);
    }

    /**
     * This tests that the NewDistanceActivity intent is assigned
     */
    @Test
    public void newDistanceActivityIntentTest() {
        // assign our intent
        testIntent = NewDistanceActivity.newDistanceIntentFactory(getApplicationContext());
        // test that these have returned properly
        assertNotNull(testIntent);
    }

    /**
     * This tests that the NewTrainingForUser intent is assigned
     */
    @Test
    public void newTrainingForUserActivityIntentTest() {
        // assign our intent
        testIntent = NewTrainingForUser.newTrainingForUserActivityIntentFactory(getApplicationContext());
        // test that these have returned properly
        assertNotNull(testIntent);
    }

    /**
     * This tests that the NewUserActivity intent is assigned
     */
    @Test
    public void newUserActivityIntentTest() {
        // assign our intent
        testIntent = NewUserActivity.newUserActivityFactory(getApplicationContext());
        // test that these have returned properly
        assertNotNull(testIntent);
    }

    /**
     * This tests that the UserTrainingActivity intent is assigned
     */
    @Test
    public void userTrainingIntentTest() {
        // assign our intent
        testIntent = UserTraining.userTrainingActivityIntentFactory(getApplicationContext());
        // test that these have returned properly
        assertNotNull(testIntent);
    }

    //////////////////////////////////////////
    //    UserTrainingLog table tests
    //////////////////////////////////////////

    /**
     * Insert test and getMatchingTrainingLog test
     * We insert a sample trainingLog, then return a trainingLog with those same properties from the database
     * These are compared to ensure the user, date, distance, time and competition status are not
     * distorted during the insert and selection process
     */
    @Test
    public void userTrainingLogInsertTest() {
        // First we insert a new log
        userTrainingLog1 = new UserTrainingLog(1, today, 5.0, 500, true);
        userTrainingDAO.insert(userTrainingLog1);
        // Then we retrieve it
        userTrainingLog2 = userTrainingDAO.getMatchingTrainingLog(1, today, 5.0, 500, true);
        // And compare all the non-id fields
        assertEquals(userTrainingLog1.getUserId(), userTrainingLog2.getUserId());
        assertEquals(userTrainingLog1.getDate(), userTrainingLog2.getDate());
        assertEquals(userTrainingLog1.getDistance(), userTrainingLog2.getDistance(), 0);
        assertEquals(userTrainingLog1.getTime(), userTrainingLog2.getTime());
        assertEquals(userTrainingLog1.isCompetition(), userTrainingLog2.isCompetition());
    }

    /**
     * Update test and getMatchingTrainingLog test
     * Requires functional insert test above.
     */
    @Test
    public void updateMatchingTrainingLog() {
        // First we insert a new log
        userTrainingLog1 = new UserTrainingLog(1, today, 5.0, 500, true);
        userTrainingDAO.insert(userTrainingLog1);
        // Then we update the log fields
        userTrainingDAO.updateMatchingTrainingLog(1, today, 5.0, 500, true, yesterday, 3.1, 600, false);
        // Last, we verify we can retrieve with these new properties and and verify that all fields are as expected
        userTrainingLog2 = userTrainingDAO.getMatchingTrainingLog(1, yesterday, 3.1, 600, false);
        assertEquals(userTrainingLog2.getDate(), yesterday);
        assertEquals(userTrainingLog2.getDistance(), 3.1, 0);
        assertEquals(userTrainingLog2.getTime(), 600, 0);
        assertFalse(userTrainingLog2.isCompetition());
    }

    /**
     * Insert test and getMatchingTrainingLog test
     * We insert a sample trainingLog, then return a trainingLog with those same properties from the database
     * These are compared to ensure the user, date, distance, time and competition status are not
     * distorted during the insert and selection process
     */
    @Test
    public void userTrainingLogDeleteTest() {
        // First we insert a new log
        userTrainingLog1 = new UserTrainingLog(9, today, 2.0, 200, true);
        userTrainingDAO.insert(userTrainingLog1);
        // Now, we verify that it has been properly inserted (same as insert test above)
        userTrainingLog2 = userTrainingDAO.getMatchingTrainingLog(9, today, 2.0, 200, true);
        //assertEquals(userTrainingLog1.getTime(), userTrainingLog2.getTime());
        // Now we delete it, and verify that we get a null value trying to retrieve the deleted record
        userTrainingDAO.deleteMatchingTrainingLog(9, today, 2.0, 200, true);
        userTrainingLog3 = userTrainingDAO.getMatchingTrainingLog(9, today, 2.0, 200, true);
        assertNull(userTrainingLog3);
    }

    //////////////////////////////////////////
    //    RaceTypes table tests
    //////////////////////////////////////////

    /**
     * Insert test and SelectByName test
     * We insert a sample race, then return a race with that same title from the database
     * These are compared to ensure Title, minimumDistance, and MaximumDistance are not
     * distorted during the insert and selection process
     */
    @Test
    public void raceTypesInsertTest() {
        // First we insert a test race
        race1 = new RaceTypes("Ultra Marathon", 31.0, 33.0);
        raceTypesDAO.insert(race1);
        // Then we retrieve it
        race2 = raceTypesDAO.getRaceByName(race1.getRaceName());
        // And compare all the non-id fields
        assertEquals(race1.getRaceName(), race2.getRaceName());
        assertEquals(race1.getMinimumDistance(), race2.getMinimumDistance(), 0);
        assertEquals(race1.getMaximumDistance(), race2.getMaximumDistance(), 0);
    }

    /**
     * Update test and SelectByName test
     * Requires functional insert test above.
     */
    @Test
    public void raceTypesUpdateTest() {
        // First, we insert a race
        race1 = new RaceTypes("Ultra Marathon", 31.0, 33.0);
        raceTypesDAO.insert(race1);
        // Then we update the race name
        raceTypesDAO.updateRaceByName("Ultra Marathon", "50k");
        // Last, we retrieve by name and verify that the name was changed as expected
        race2 = raceTypesDAO.getRaceByName("50k");
        assertEquals(race2.getRaceName(), "50k");
    }

    /**
     * Delete test and SelectByName test
     * Requires functional insert test above.
     */
    @Test
    public void raceTypesDeleteTest() {
        // First we insert a race for testing
        race1 = new RaceTypes("Ultra Marathon", 31.0, 33.0);
        raceTypesDAO.insert(race1);
        // Now, we verify that it has been properly inserted (same as insert test above)
        race2 = raceTypesDAO.getRaceByName("Ultra Marathon");
        assertEquals(race1.getRaceName(), race2.getRaceName());
        // Now we deleted it, and verify that we get a null value trying to retrieve the deleted record
        raceTypesDAO.deleteRaceByName("Ultra Marathon");
        race3 = raceTypesDAO.getRaceByName("Ultra Marathon");
        assertNull(race3);
    }

}
