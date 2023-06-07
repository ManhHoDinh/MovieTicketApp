package com.example.movieticketapp.Model;

public class Service {
    private String image;
    private String name;
    private int price;
    private String detail;

    public Service(String image, String name, int price, String detail) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.detail = detail;
    }
    public Service(){}
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
