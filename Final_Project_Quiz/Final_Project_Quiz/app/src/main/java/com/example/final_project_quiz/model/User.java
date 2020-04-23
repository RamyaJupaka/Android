package com.example.final_project_quiz.model;

import androidx.annotation.NonNull;

public class User {
    private String name;
    private String password;




    public User(String name, String password) {
        this.name = name;
        this.password = password;

    }

    public User(){}

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



    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
