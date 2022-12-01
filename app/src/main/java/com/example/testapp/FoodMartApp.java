package com.example.testapp;

import android.app.Application;

import com.example.testapp.model.ModelCustomer;
import com.example.testapp.model.ModelVendor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


public class FoodMartApp extends Application {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRefCustomer;
    private DatabaseReference dbRefVendor;
    private FirebaseStorage firebaseStorage;

    public DatabaseReference getDbRefFoodItems() {
        return dbRefFoodItems;
    }

    private DatabaseReference dbRefFoodItems;

    private ModelCustomer foodMartCustomer;
    private ModelVendor foodMartVendor;

    public ModelCustomer getFoodMartCustomer() {
        return foodMartCustomer;
    }

    public void setFoodMartCustomer(ModelCustomer foodMartCustomer) {
        this.foodMartCustomer = foodMartCustomer;
    }

    public ModelVendor getFoodMartVendor() {
        return foodMartVendor;
    }

    public void setFoodMartVendor(ModelVendor foodMartVendor) {
        this.foodMartVendor = foodMartVendor;
    }

    public DatabaseReference getDbRefVendor() {
        return dbRefVendor;
    }


    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public DatabaseReference getDbRefCustomer() {
        return dbRefCustomer;
    }

    public FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //init firebase auth & DB
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRefCustomer = firebaseDatabase.getReference("users").child("customer");
        dbRefVendor = firebaseDatabase.getReference("users").child("vendor");
        dbRefFoodItems=firebaseDatabase.getReference("food_items");

        firebaseStorage=FirebaseStorage.getInstance();


    }


}
