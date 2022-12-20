package com.example.project;

public class Destination {

    private String name, host, description, imageUrl, coordinates, price, nights, people, country,
            latitude, longitude;

    public Destination() {
    }

    public Destination(String name, String host, String description, String imageUrl, String coordinates,
                       String price, String nights, String people, String country, String latitude,
                       String longitude) {
        this.name = name;
        this.host = host;
        this.description = description;
        this.imageUrl = imageUrl;
        this.coordinates = coordinates;
        this.price = price;
        this.nights = nights;
        this.people = people;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
