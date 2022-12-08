package com.example.project;

public class MyReview {

    private String name, review, score;

    public MyReview() { }

    public MyReview(String name, String review, String score) {
        this.name = name;
        this.review = review;
        this.score = score;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }
}
