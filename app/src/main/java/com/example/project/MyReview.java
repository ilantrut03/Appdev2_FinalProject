package com.example.project;

public class MyReview {

    private String score;
    private String text;
    private String name;

    public MyReview() {

    }
    public MyReview(String score, String text, String name) {
        this.score = score;
        this.text = text;
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
