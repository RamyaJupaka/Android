package com.example.tableview__practice.model;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class Task {

    private  int taskId;
    private String taskDescription;
    private int taskTextColor;

    public Task(int taskId, String taskDescription, int taskTextColor) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.taskTextColor = taskTextColor;
    }

    public Task(int taskId, String taskDescription) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.taskTextColor = Color.BLACK;
    }

    public Task(String taskDescription, int taskTextColor) {
       
        this.taskDescription = taskDescription;
        this.taskTextColor = taskTextColor;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskTextColor() {
        return taskTextColor;
    }

    public void setTaskTextColor(int taskTextColor) {
        this.taskTextColor = taskTextColor;
    }



    @NonNull
    @Override
    public String toString() {
        return taskDescription;
    }
}
