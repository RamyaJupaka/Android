package com.example.heyfoodie.Model;

public class AddItem {


    String name;
    String price;
    String quantity;
    String threshold;

    public AddItem(String s, String toString, String string, String s1, String toString1, String string1) {

    }

    public AddItem(String name, String price, String quantity, String threshold) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }
}
