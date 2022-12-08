package com.example.project;

public class Destination {

    private String name, imageUrl, price, coordinates;
    private int nights, people;

    public Destination(String name, String coordinates, String imageUrl, String price, int number_nights, int number_people) {
        this.name = name;
        this.coordinates = coordinates;
        this.imageUrl = imageUrl;
        this.price = price;
        this.nights = number_nights;
        this.people = number_people;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCoordinates() { return coordinates; }
    public void setCoordinates(String coordinates) { this.coordinates = coordinates; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public int getNights() { return nights; }
    public void setNumber_nights(int number_nights) { this.nights = number_nights; }

    public int getPeople() { return people; }
    public void setNumber_people(int number_people) { this.people = number_people; }

    @Override
    public String toString() {
        return "Destination{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price='" + price + '\'' +
                ", number_nights=" + nights +
                ", number_people=" + people +
                '}';
    }
}
