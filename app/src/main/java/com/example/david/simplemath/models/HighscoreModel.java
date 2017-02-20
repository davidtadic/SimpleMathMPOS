package com.example.david.simplemath.models;

/**
 * Created by david on 20.2.2017..
 */

public class HighscoreModel {
    private int id;
    private String score;
    private String date;

    public HighscoreModel(String score, String date) {
        this.score = score;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "HighscoreModel{" +
                "score='" + score + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
