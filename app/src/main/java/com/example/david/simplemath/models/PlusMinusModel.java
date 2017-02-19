package com.example.david.simplemath.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by david on 19.2.2017..
 */

public class PlusMinusModel {
    private int id;
    private String expression;
    private int correctAnswer;
    private int wrongAnswer1;
    private int wrongAnswer2;
    private int wrongAnswer3;

    public PlusMinusModel(int id, String expression, int correctAnswer, int wrongAnswer1, int wrongAnswer2, int wrongAnswer3) {
        this.id = id;
        this.expression = expression;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
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

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(int wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public int getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(int wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public int getWrongAnswer3() {
        return wrongAnswer3;
    }

    public void setWrongAnswer3(int wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

    @Override
    public String toString() {
        return "PlusMinusModel{" +
                "id=" + id +
                ", expression='" + expression + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", wrongAnswer1=" + wrongAnswer1 +
                ", wrongAnswer2=" + wrongAnswer2 +
                ", wrongAnswer3=" + wrongAnswer3 +
                '}';
    }

    public List<Integer> getMixedPlusMinusQuestions(){
        List<Integer> shuffle = new ArrayList<Integer>();

        shuffle.add(correctAnswer);
        shuffle.add(wrongAnswer1);
        shuffle.add(wrongAnswer2);
        shuffle.add(wrongAnswer3);

        Collections.shuffle(shuffle);

        return shuffle;

    }
}
