package com.example.andriodlabproject;

import android.view.View;

public class Car {
    private int ID;
    private String type;
    private String factoryName;
    private String price;
    private String fuelType;
    private String transmission;
    private String mileage;
    private int imgCar; // Assuming drawable IDs will be used for car images
    private int imgFavButton;
    private int visibleReserveButton= View.VISIBLE;
    private String date;
    private int visibleDate=View.INVISIBLE;
    private String offer;
    public Car() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public int getImgCar() {
        return imgCar;
    }

    public void setImgCar(int imgCar) {
        this.imgCar = imgCar;
    }

    public int getImgFavButton() {
        return imgFavButton;
    }

    public void setImgFavButton(int imgFavButton) {
        this.imgFavButton = imgFavButton;
    }

    public int getVisibleReserveButton() {
        return visibleReserveButton;
    }

    public void setVisibleReserveButton(int visibleReserveButton) {
        this.visibleReserveButton = visibleReserveButton;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVisibleDate() {
        return visibleDate;
    }

    public void setVisibleDate(int visibleDate) {
        this.visibleDate = visibleDate;
    }

    public String getOffer() {return offer;}

    public void setOffer(String offer) {this.offer = offer;}

}
