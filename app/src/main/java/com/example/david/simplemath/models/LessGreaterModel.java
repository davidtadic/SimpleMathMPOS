package com.example.david.simplemath.models;

/**
 * Created by david
 */

public class LessGreaterModel {
    private int id;
    private String expression;
    private String correctAnswer;


    public LessGreaterModel(int id, String expression, String correctAnswer) {
        this.id = id;
        this.expression = expression;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "LessGreaterModel{" +
                "id=" + id +
                ", expression='" + expression + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }


}
