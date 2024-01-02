package com.example.andriodlabproject;

public class Car {
    private String price;
    private int imageResourceId; // Assuming drawable IDs will be used for car images
    private int imgFav;
    private int ID;
    private String type;
    public Car() {

    }

//    public Car(String name, String price, int imageResourceId) {
//        this.name = name;
//        this.price = price;
//        this.imageResourceId = imageResourceId;
//    }

    public String getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getImageFavResourceId() {
        return imgFav;
    }

    public int getID() {
        return ID;
    }

    public String getType() {
        return type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageResourceId(int imageResourceId) { this.imageResourceId = imageResourceId;}

    public void setImageFavResourceId(int imageFavResourceId) { this.imgFav = imageFavResourceId;}




}
