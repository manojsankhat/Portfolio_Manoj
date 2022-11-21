package com.example.testapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.FoodMartApp;
import com.example.testapp.R;
import com.example.testapp.model.ModelFoodItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityVendorFoodItems extends AppCompatActivity {

    private RecyclerView rcvFoodItems;
    private FoodMartApp foodMartApp;
    private ImageView imgFood;
    private ArrayList<ModelFoodItem> arrayListFoodItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_vendor_food_items);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        foodMartApp = (FoodMartApp) getApplication();
        arrayListFoodItems=new ArrayList<>();
        rcvFoodItems = findViewById(R.id.RCV_FOOD_ITEMS);
        imgFood=findViewById(R.id.IMG_FOOD);

        findViewById(R.id.BTN_ADD).setOnClickListener(view -> {
            BottomSheetBehavior.from(findViewById(R.id.BTMSHEET)).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        imgFood.setOnClickListener(view -> {
            Intent intentImgPicker=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intentImgPicker.setType("image/*");
            intentImgPicker.putExtra("return-data", true);
            startActivityForResult(intentImgPicker,12);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imgPath=data.getData();
        imgFood.setImageURI(imgPath);

    }

    @Override
    protected void onResume() {
        super.onResume();


        foodMartApp.getDbRefFoodItems().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot foodItem : snapshot.getChildren()) {
                    arrayListFoodItems.add( foodItem.getValue(ModelFoodItem.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });

    }
}
