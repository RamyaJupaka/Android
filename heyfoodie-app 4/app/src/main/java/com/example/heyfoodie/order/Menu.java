package com.example.heyfoodie.order;

public class Menu {
    private String dishName;
    private String estimatedTime;
    String type;
    String price;
    private String pic;

    public String getImage() {
        return pic;
    }

    public void setImage(String image) {
        this.pic = image;
    }

    public Menu(String dishName, String estimatedTime, String type, String price, String pic) {
        this.dishName = dishName;
        this.estimatedTime = estimatedTime;
        this.type = type;
        this.price = price;
        this.pic = pic;
    }

    public Menu() {
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
