package com.example.cst338_tracktournament.Database.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {
    Users users;
    @Before
    public void setUp() throws Exception {
         users = new Users("Lakers","Bryant", "Coach");
    }

    @After
    public void tearDown() throws Exception {
        users = null;
    }

    @Test
    public void getName() {
        assertEquals("Lakers",users.getName());
        assertNotEquals("User",users.getUserType());
    }

    @Test
    public void setName() {
        users.setName("Cleveland");
        assertEquals("Cleveland",users.getName());
    }

    @Test
    public void getPassword() {
        assertEquals("Bryant", users.getPassword());
    }

    @Test
    public void setPassword() {
        users.setPassword("James");
        assertNotEquals("Bryant", users.getPassword());
    }

    @Test
    public void getUserType() {
        assertEquals("Coach",users.getUserType());
    }

    @Test
    public void setUserType() {
        users.setUserType("User");
        assertEquals("User", users.getUserType());
    }
}