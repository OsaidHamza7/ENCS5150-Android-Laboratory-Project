package com.example.andriodlabproject;

import java.util.ArrayList;
import java.util.List;

public class Reservation {
//
    private int reservationID;
    private String userEmail;
    private int carID;
    private String reservationDate;
    private String reservationTime;

    public Reservation() {

    }

    public Reservation(int reservationID, String userEmail, int carID, String reservationDate, String reservationTime) {
        this.reservationID = reservationID;
        this.userEmail = userEmail;
        this.carID = carID;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public int getReservationID() {
        return reservationID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getCarID() {
        return carID;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }


}
