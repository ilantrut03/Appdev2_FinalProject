package com.example.project;

public class Destination {

    private String name, imageUrl, coordinates, price, nights, people;

    public Destination() {
    }

    public Destination(String name, String imageUrl, String coordinates, String price, String nights, String people) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.coordinates = coordinates;
        this.price = price;
        this.nights = nights;
        this.people = people;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNights() {
        return nights;
    }

    public void setNights(String nights) {
        this.nights = nights;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}
