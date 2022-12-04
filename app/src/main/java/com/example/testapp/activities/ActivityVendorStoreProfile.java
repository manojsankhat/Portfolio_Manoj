package com.example.testapp.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testapp.FoodMartApp;
import com.example.testapp.R;
import com.example.testapp.fragments.FragmentStoreProfile;

public class ActivityVendorStoreProfile extends AppCompatActivity {

    private FragmentStoreProfile fragmentStoreProfile;
    private FoodMartApp foodMartApp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_profile);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        foodMartApp = (FoodMartApp) getApplication();

        fragmentStoreProfile = new FragmentStoreProfile();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadFragment(fragmentStoreProfile, false);

    }

    public void loadFragment(Fragment fragment, boolean goBack) {
        if (goBack)
            getSupportFragmentManager().beginTransaction().replace(R.id.CONTAINER, fragment).addToBackStack(fragment.getClass().getName()).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.CONTAINER, fragment).commit();
    }


}
