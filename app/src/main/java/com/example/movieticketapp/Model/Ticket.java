package com.example.movieticketapp.Model;

public class Ticket {
    protected String name;
    protected String time;
    protected String cinema;
    protected int poster;
    protected double rate;
    protected String kind;
    protected String duration;
    protected String seat;
    protected String paid;
    protected String idorder;
    public Ticket(String ten, String time, String cinema, int poster,double rate, String kind, String duration, String seat, String paid, String idorder)
    {
        this.name = ten;
        this.time = time;
        this.cinema = cinema;
        this.poster = poster;
        this.kind = kind;
        this.seat = seat;
        this.paid = paid;
        this.idorder = idorder;
        this.duration = duration;
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public int getPoster() {
        return poster;
    }

    public String getCinema() {
        return cinema;
    }

    public String getName() {
        return name;
    }
    public String getKind()  {return kind;}
    public String getDuration() {return duration;}
    public String getSeat() {return  seat;}
    public String getPaid() {return paid;}
    public double getRate() {return  rate;}
    public String getIdorder() {return idorder;}

    public void setTime(String time) {
        this.time = time;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setKind(String kind) {this.kind = kind;}
    public void setDuration(String duration) {this.duration = duration;}
    public void setRate(double rate) {this.rate = rate;}
    public void setSeat(String seat) {this.seat =seat;}
    public void setPaid(String paid) {this.paid = paid;}
    public void setIdorder(String idorder) {this.idorder = idorder;}
}
