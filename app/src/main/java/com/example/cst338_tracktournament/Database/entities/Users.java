package com.example.cst338_tracktournament.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "logInTable")
public class Users {
    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String name;
    private String password;
    private String userType;

    public Users(String name, String password, String userType) {
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users that = (Users) o;
        return getUserId() == that.getUserId() && Objects.equals(getName(), that.getName()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getUserType(), that.getUserType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName(), getPassword(), getUserType());
    }
}
