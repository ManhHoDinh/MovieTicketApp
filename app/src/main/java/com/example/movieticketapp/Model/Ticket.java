package com.example.movieticketapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.protobuf.TimestampProto;

import java.sql.Time;

public class Ticket implements Parcelable {
    //protected String name;
    protected Timestamp time;
    protected String cinemaID;
    //protected String poster;
   // protected double rate;
   // protected String kind;
    //protected String duration;
    protected String seat;
    protected String paid;
    protected String filmID;
    protected String idorder;
    protected String voucherID;
    protected String userID;
    protected String ID;
//    public Ticket(String name, Timestamp time, String cinema, String poster,double rate, String kind, String duration, String seat, String paid, String idorder)
//    {
//        this.name = name;
//        this.time = time;
//        this.cinema = cinema;
//        this.poster = poster;
//        this.kind = kind;
//        this.seat = seat;
//        this.paid = paid;
//        this.idorder = idorder;
//        this.duration = duration;
//        this.rate = rate;
//    }
public Ticket( Timestamp time, String cinemaID, String seat, String paid, String idorder, String filmID, String userID, String voucherID, String ID)
{
    this.filmID = filmID;
    this.time = time;
    this.cinemaID = cinemaID;
    this.seat = seat;
    this.paid = paid;
    this.idorder = idorder;
    this.userID = userID;
    this.voucherID = voucherID;
    this.ID = ID;
}

    protected Ticket(Parcel in) {
        time = in.readParcelable(Timestamp.class.getClassLoader());
        cinemaID = in.readString();
        seat = in.readString();
        paid = in.readString();
        filmID = in.readString();
        idorder = in.readString();
        voucherID = in.readString();
        userID = in.readString();
        ID = in.readString();
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    public void setCinemaID(String cinemaID) {

        this.cinemaID = cinemaID;
    }

    public String getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(String voucherID) {
        this.voucherID = voucherID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Ticket(){}

    public Timestamp getTime() {
        return time;
    }

//    public String getPoster() {
//        return poster;
//    }

    public String getCinemaID() {
        return cinemaID;
    }

//    public String getName() {
//        return name;
//    }
//    public String getKind()  {return kind;}
//    public String getDuration() {return duration;}
    public String getSeat() {return  seat;}
    public String getPaid() {return paid;}
//    public double getRate() {return  rate;}
    public String getIdorder() {return idorder;}

    public void setTime(Timestamp time) {
        this.time = time;
    }

//    public void setPoster(String poster) {
//        this.poster = poster;
//    }

    public String getFilmID() {
        return filmID;
    }

    public void setFilmID(String filmID) {
        this.filmID = filmID;
    }

    public void setCinema(String cinema) {
        this.cinemaID = cinema;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
//    public void setKind(String kind) {this.kind = kind;}
//    public void setDuration(String duration) {this.duration = duration;}
//    public void setRate(double rate) {this.rate = rate;}
    public void setSeat(String seat) {this.seat =seat;}
    public void setPaid(String paid) {this.paid = paid;}
    public void setIdorder(String idorder) {this.idorder = idorder;}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(time, i);
        parcel.writeString(cinemaID);
        parcel.writeString(seat);
        parcel.writeString(paid);
        parcel.writeString(filmID);
        parcel.writeString(idorder);
        parcel.writeString(voucherID);
        parcel.writeString(userID);
        parcel.writeString(ID);
    }
}
