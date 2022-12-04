package com.example.testapp;

import android.app.Application;

import com.example.testapp.model.ModelCustomer;
import com.example.testapp.model.ModelVendor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FoodMartApp extends Application {
//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
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRefCustomer;
    private DatabaseReference dbRefVendor;
    private DatabaseReference dbFoodMartFoodItems;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference dbRefFoodItems;


    public FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }

    public void setFirebaseStorage(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }


    private StorageReference storageReferenceFoodItems;


    // Getter and Setter for StorageReference:
    public StorageReference getStorageReferenceFoodItems() {
        return storageReferenceFoodItems;
    }

    public void setStorageReferenceFoodItems(StorageReference storageReferenceFoodItems) {
        this.storageReferenceFoodItems = storageReferenceFoodItems;
    }


    public DatabaseReference getDbRefFoodItems() {
        return dbRefFoodItems;
    }



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


    public DatabaseReference getDbFoodMartFoodItems() {
        return dbFoodMartFoodItems;
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

    @Override
    public void onCreate() {
        super.onCreate();
        //init firebase auth & DB
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRefCustomer = firebaseDatabase.getReference("users").child("customer");
        dbRefVendor = firebaseDatabase.getReference("users").child("vendor");
        dbRefFoodItems=firebaseDatabase.getReference("food_items");
        firebaseStorage = FirebaseStorage.getInstance();
        dbFoodMartFoodItems=firebaseDatabase.getReference("food_list");

    }





}
