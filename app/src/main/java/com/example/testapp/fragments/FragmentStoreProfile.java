package com.example.testapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testapp.FoodMartApp;
import com.example.testapp.R;
import com.example.testapp.activities.ActivityVendorStoreProfile;

public class FragmentStoreProfile extends Fragment {
    private ActivityVendorStoreProfile activityParent;
    private View fragmentView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityParent = (ActivityVendorStoreProfile) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_store_profile, container, false);
        initUI();
        return fragmentView;
    }

    private void initUI() {


    }

}
