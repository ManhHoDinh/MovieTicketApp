package com.example.movieticketapp.Model;

public class InforBooked {
    private static InforBooked instance;
    public String nameCinema;
    public String dateBooked;
    public String timeBooked;
    public static InforBooked getInstance(){
        if(instance == null){
            instance = new InforBooked();
        }
        return  instance;
    }

}
