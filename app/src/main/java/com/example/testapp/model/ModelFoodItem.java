package com.example.testapp.model;

public class ModelFoodItem {
    public ModelFoodItem(String name, String description, String price, String imageURL) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
    }

    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    private String price;
    private String imageURL;



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

}
