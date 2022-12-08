package com.example.project;

public class Destination {

    private String coordinates, imageUrl, name;
    private int nights, people, price;

    public Destination() {  }

    public Destination(String coordinates, String imageUrl, String name, int nights, int people, int price) {
        this.coordinates = coordinates;
        this.imageUrl = imageUrl;
        this.name = name;
        this.nights = nights;
        this.people = people;
        this.price = price;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
