package com.example.testapp.model;

public class ModelVendor {

    private String name;
    private String phone;
    private String email;

    public ModelVendor(String name, String phone, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    private String password;
    private String stallLocation;
    private String stallImageUrl;

    public ModelVendor() {
    }

    public ModelVendor(String name, String phone, String email, String password, String stallLocation, String stallImageUrl) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.stallLocation = stallLocation;
        this.stallImageUrl = stallImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStallLocation() {
        return stallLocation;
    }

    public void setStallLocation(String stallLocation) {
        this.stallLocation = stallLocation;
    }

    public String getStallImageUrl() {
        return stallImageUrl;
    }

    public void setStallImageUrl(String stallImageUrl) {
        this.stallImageUrl = stallImageUrl;
    }
}
