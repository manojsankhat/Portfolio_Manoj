package com.example.testapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testapp.FoodMartApp;
import com.example.testapp.R;
import com.example.testapp.fragments.FragmentLogin;
import com.example.testapp.fragments.FragmentSignup;
import com.example.testapp.fragments.FragmentSplash;

public class ActivityAccount extends AppCompatActivity {

    private FragmentSplash fragmentSplash;
    private FragmentLogin fragmentLogin;
    private FragmentSignup fragmentSignup;
    private Intent redirectIntent;
    private FoodMartApp foodMartApp;

    public Intent getRedirectIntent() {
        return redirectIntent;
    }

    public void setRedirectIntent(Intent redirectIntent) {
        this.redirectIntent = redirectIntent;
    }

    public FoodMartApp getFoodMartApp() {
        return foodMartApp;
    }

    public FragmentSignup getFragmentSignup() {
        return fragmentSignup;
    }

    public FragmentLogin getFragmentLogin() {
        return fragmentLogin;
    }

    //View SET
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    //OBJECTS
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        foodMartApp = (FoodMartApp) getApplication();

        fragmentSplash = new FragmentSplash();
        fragmentLogin = new FragmentLogin();
        fragmentSignup = new FragmentSignup();
    }


    //VIEW BINDING , VALUES
    @Override
    protected void onResume() {
        super.onResume();
        loadFragment(fragmentSplash, false);
    }

    public void loadFragment(Fragment fragment, boolean goBack) {
        if (goBack)
            getSupportFragmentManager().beginTransaction().replace(R.id.CONTAINER, fragment).addToBackStack(fragment.getClass().getName()).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.CONTAINER, fragment).commit();
    }


}
