package com.example.testapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp.FoodMartApp;
import com.example.testapp.R;

public class ActivityVendorDashBoard extends AppCompatActivity {
    private FoodMartApp foodMartApp;

    public FoodMartApp getFoodMartApp() {
        return foodMartApp;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_vendor);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        foodMartApp = (FoodMartApp) getApplication();

        findViewById(R.id.CARD_FOOD_ITEMS).setOnClickListener(view -> {
            startActivity(new Intent(ActivityVendorDashBoard.this, ActivityVendorFoodItems.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TextView)findViewById(R.id.TV_VENDOR_NAME)).setText("Welcome "+getFoodMartApp().getFoodMartVendor().getName());
    }
}
