package com.example.testapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testapp.R;
import com.example.testapp.activities.ActivityAccount;

public class FragmentSplash extends Fragment {

    private ActivityAccount activityParent;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
         activityParent =(ActivityAccount) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(() -> activityParent.loadFragment(activityParent.getFragmentLogin(),false),3000);
    }

}
