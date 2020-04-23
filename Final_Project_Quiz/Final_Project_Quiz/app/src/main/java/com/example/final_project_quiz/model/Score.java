package com.example.final_project_quiz.model;

public class Score {
    private String name;
    private int score1;

    public Score(String name, int score1) {
        this.name = name;
        this.score1 = score1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }
}
