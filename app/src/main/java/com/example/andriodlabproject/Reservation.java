package com.example.andriodlabproject;

public class Reservation {
    private int reservationID;
    private int userID;
    private int carID;
    private String reservationDate;
    private String reservationTime;

    public Reservation() {

    }

    public Reservation(int reservationID, int userID, int carID, String reservationDate, String reservationTime) {
        this.reservationID = reservationID;
        this.userID = userID;
        this.carID = carID;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public int getReservationID() {
        return reservationID;
    }

    public int getUserID() {
        return userID;
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

    public void setUserID(int userID) {
        this.userID = userID;
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
