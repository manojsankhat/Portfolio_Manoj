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

import com.example.testapp.R;
import com.example.testapp.activities.ActivityAccount;
import com.example.testapp.activities.ActivityVendorDashBoard;
import com.example.testapp.model.ModelCustomer;
import com.example.testapp.model.ModelVendor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FragmentLogin extends Fragment {

    private ActivityAccount activityParent;
    private View fragmentView;
    private TextInputLayout tilEmail, tilPassWord;
    private RadioGroup rgUserType;
    private DatabaseReference dbRefLogin;
    private Runnable runnableFetchDetails;
    private DataSnapshot snapShotLoggedInUser;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityParent = (ActivityAccount) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        initUI();
        return fragmentView;
    }

    private void initUI() {
        tilEmail = fragmentView.findViewById(R.id.TIL_EMAIL);
        tilPassWord = fragmentView.findViewById(R.id.TIL_PASSWORD);
        rgUserType = fragmentView.findViewById(R.id.RG_USER_TYPE);


        fragmentView.findViewById(R.id.BTN_LOGIN).setOnClickListener(view -> {

                activityParent.getFoodMartApp().getFirebaseAuth().signInWithEmailAndPassword(tilEmail.getEditText().getText().toString(), tilPassWord.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            switch (rgUserType.getCheckedRadioButtonId()) {
                                case R.id.RB_CUSTOMER:
                                    activityParent.setRedirectIntent(new Intent(activityParent, ActivityVendorDashBoard.class));
                                    dbRefLogin=activityParent.getFoodMartApp().getDbRefCustomer();
                                    activityParent.setRedirectIntent(new Intent(activityParent, ActivityVendorDashBoard.class));
                                    runnableFetchDetails= () -> {
                                        //customer
                                        activityParent.getFoodMartApp().setFoodMartCustomer(snapShotLoggedInUser.getValue(ModelCustomer.class));
                                    };
                                    break;
                                case R.id.RB_VENDOR:
                                    dbRefLogin=activityParent.getFoodMartApp().getDbRefVendor();
                                    activityParent.setRedirectIntent(new Intent(activityParent, ActivityVendorDashBoard.class));


                                    runnableFetchDetails= () -> {
                                        //vendor
                                        activityParent.getFoodMartApp().setFoodMartVendor(snapShotLoggedInUser.getValue(ModelVendor.class));



                                    };
                                    break;
                            }

                            dbRefLogin.child(activityParent.getFoodMartApp().getFirebaseAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapShotLoggedInUser=snapshot;
                                    runnableFetchDetails.run();
                                    startActivity(activityParent.getRedirectIntent());


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(activityParent, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(activityParent, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


        });

        fragmentView.findViewById(R.id.BTN_SIGNUP).setOnClickListener(view -> {
            activityParent.loadFragment(activityParent.getFragmentSignup(), true);
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (activityParent.getFoodMartApp().getFirebaseAuth().getCurrentUser() != null) {
            //activityParent.redirectToDashBoard();
        }
    }
}
