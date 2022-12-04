package com.example.testapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testapp.FoodMartApp;
import com.example.testapp.R;
import com.example.testapp.activities.ActivityAccount;
import com.example.testapp.activities.ActivityVendorDashBoard;
import com.example.testapp.model.ModelCustomer;
import com.example.testapp.model.ModelVendor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;

public class FragmentSignup extends Fragment {

    private ActivityAccount activityParent;
    private View fragmentView;
    private TextInputLayout tilName, tilPhone, tilEmail, tilPassWord;
    private RadioGroup rgUserType;
    private DatabaseReference dbRefSignup;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityParent = (ActivityAccount) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_signup, container, false);
        initUI();
        return fragmentView;
    }

    private void initUI() {
        tilName = fragmentView.findViewById(R.id.TIL_NAME);
        tilPhone = fragmentView.findViewById(R.id.TIL_PHONE);
        tilEmail = fragmentView.findViewById(R.id.TIL_EMAIL);
        tilPassWord = fragmentView.findViewById(R.id.TIL_PASSWORD);
        rgUserType = fragmentView.findViewById(R.id.RG_USER_TYPE);

        fragmentView.findViewById(R.id.BTN_SIGNUP).setOnClickListener(view -> {
            activityParent.getFoodMartApp().getFirebaseAuth().createUserWithEmailAndPassword(tilEmail.getEditText().getText().toString(), tilPassWord.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        switch (rgUserType.getCheckedRadioButtonId()) {
                            case R.id.RB_CUSTOMER:
                                activityParent.setRedirectIntent(new Intent(activityParent, ActivityVendorDashBoard.class));
                                dbRefSignup=activityParent.getFoodMartApp().getDbRefCustomer();
                                activityParent.getFoodMartApp().setFoodMartCustomer(new ModelCustomer(tilName.getEditText().getText().toString(), tilPhone.getEditText().getText().toString(), tilEmail.getEditText().getText().toString(), tilPassWord.getEditText().getText().toString()));
                                break;
                            case R.id.RB_VENDOR:
                                activityParent.setRedirectIntent(new Intent(activityParent, ActivityVendorDashBoard.class));
                                dbRefSignup=activityParent.getFoodMartApp().getDbRefVendor();
                                activityParent.getFoodMartApp().setFoodMartVendor(new ModelVendor(tilName.getEditText().getText().toString(), tilPhone.getEditText().getText().toString(), tilEmail.getEditText().getText().toString(), tilPassWord.getEditText().getText().toString()));
                                break;
                        }


                        dbRefSignup.child(activityParent.getFoodMartApp().getFirebaseAuth().getCurrentUser().getUid()).setValue(rgUserType.getCheckedRadioButtonId()==R.id.RB_CUSTOMER?activityParent.getFoodMartApp().getFoodMartCustomer():activityParent.getFoodMartApp().getFoodMartVendor()).addOnCompleteListener((OnCompleteListener<Void>) task1 -> {
                            if (task1.isSuccessful()) {
                                startActivity(activityParent.getRedirectIntent());
                            } else {
                                Toast.makeText(activityParent, "Failed To Create New Account", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(activityParent, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


    }

}
