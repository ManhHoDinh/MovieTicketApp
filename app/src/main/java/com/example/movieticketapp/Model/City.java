package com.example.movieticketapp.Model;

public class City {
    private String ID;
    private String Name;

    public City(String ID, String name) {
        this.ID = ID;
        Name = name;
    }
    public City(){};
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
