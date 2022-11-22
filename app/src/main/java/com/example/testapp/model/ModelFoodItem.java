package com.example.testapp.model;

public class ModelFoodItem {

    private String name;
    private String price;
    private String imageURL;
    private String foodDescription;

    public ModelFoodItem(String name, String price, String imageURL,String foodDescription) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.foodDescription=foodDescription;
    }

    public ModelFoodItem() {
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }
}
