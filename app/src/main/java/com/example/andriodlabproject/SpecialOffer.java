package com.example.andriodlabproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class SpecialOffer {
    private int ID;
    private int CarID;
    private String StartDate;
    private String EndDate;
    private String Discount;

    public SpecialOffer() {
    }

    public SpecialOffer(int ID, int CarID, String StartDate, String EndDate, String Discount) {
        this.ID = ID;
        this.CarID = CarID;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Discount = Discount;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCarID() {
        return CarID;
    }

    public void setCarID(int carID) {
        CarID = carID;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
