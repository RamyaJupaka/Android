package com.example.heyfoodie.Model;

public class MenuItems {

    public String dishName;
    public String ingredientName;
    String quantity;

    public MenuItems(String dishName, String ingredientName, String quantity) {
        this.dishName = dishName;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public MenuItems() {
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
