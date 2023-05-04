package com.example.movieticketapp.Model;

public class MovieBooked {
    private String name;
    private int price;
    private String timeBooked;
    private String imageMovie;
    public MovieBooked(String name, int price, String timeBooked, String imageMovie) {
        this.name = name;
        this.price = price;
        this.timeBooked = timeBooked;
        this.imageMovie = imageMovie;
    }
    public MovieBooked(){}

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getTimeBooked() {
        return timeBooked;
    }

    public String getImageMovie() {
        return imageMovie;
    }
}
