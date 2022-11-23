package com.example.project;

public class Destination {
    String location_name;
    String imageUrl;
    String price;

    public Destination() {

    }

    public Destination(String location_name, String imageUrl, String price) {
        this.location_name = location_name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "location_name='" + location_name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
