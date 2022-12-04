package com.example.testapp.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.FoodMartApp;
import com.example.testapp.R;
import com.example.testapp.adapters.AdapterFoodItemVendor;
import com.example.testapp.model.ModelFoodItem;
import com.example.testapp.model.ModelVendor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ActivityVendorFoodItems extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private TextInputLayout tilFoodName, tilFoodPrice, tilFoodDesc;
    private RecyclerView rcvFoodItems;
    private FoodMartApp foodMartApp;
    private ImageView imgFood;
    private Uri imgPath;
    private Button btn_create_food_item;
    private StorageReference storageReference;
    private AdapterFoodItemVendor adapterVendor;

    private ArrayList<ModelFoodItem> arrayListFoodItems;

//    public ArrayList<ModelFoodItem> getArrayListFoodItems() {
//        return arrayListFoodItems;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_vendor_food_items);

    }

    //onPostCreate Method for initialize the objects
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        btn_create_food_item = findViewById(R.id.BTN_CREATE_FOOD_ITEM);
        foodMartApp = (FoodMartApp) getApplication();
        imgFood = findViewById(R.id.IMG_FOOD);
        tilFoodName = findViewById(R.id.TIL_FOOD_NAME);
        tilFoodPrice = findViewById(R.id.TIL_FOOD_PRICE);
        tilFoodDesc = findViewById(R.id.TIL_FOOD_DESC);

        arrayListFoodItems = new ArrayList<>();
        rcvFoodItems = findViewById(R.id.RCV_FOOD_ITEMS);

        adapterVendor = new AdapterFoodItemVendor(arrayListFoodItems,this);
        rcvFoodItems.setLayoutManager(new LinearLayoutManager(this));
        rcvFoodItems.setAdapter(adapterVendor);


        /**
         * Adding a items in vendor table on button click (create food item)
         */
        btn_create_food_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();

            }
        });


        storageReference = foodMartApp.getFirebaseStorage().getReference("ImageData/");

        imgFood.setOnClickListener(view -> {

//            if (checkAndRequestPermissions(ActivityVendorFoodItems.this)) {
//                chooseImage(ActivityVendorFoodItems.this);
//            }

            Intent intentImgPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intentImgPicker.setType("image/*");
            intentImgPicker.putExtra("return-data", true);
            startActivityForResult(intentImgPicker, 12);

        });

        findViewById(R.id.BTN_ADD).setOnClickListener(view -> {
            BottomSheetBehavior.from(findViewById(R.id.BTMSHEET)).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        foodMartApp.getDbFoodMartFoodItems().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListFoodItems.clear();
                for (DataSnapshot foodItem : snapshot.getChildren()) {
                    arrayListFoodItems.add(foodItem.getValue(ModelFoodItem.class));

                    adapterVendor.abcd();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });


/*
        foodMartApp.getDbRefFoodItems().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot foodItem : snapshot.getChildren()) {
                    arrayListFoodItems.add(foodItem.getValue(ModelFoodItem.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
*/

    }
    // we will choose the items from the device
    private void chooseImage(Context context) {

        final CharSequence[] optionsMenu = {
                "Take Photo", "Choose from Gallery"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (optionsMenu[i].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                }
            }
        });
        builder.show();
    }

    //Function to check Permission
    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(ActivityVendorFoodItems.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(), "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT).show();
                } else if (ContextCompat.checkSelfPermission(ActivityVendorFoodItems.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(), "FlagUp Requires Access to Your Storage.", Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(ActivityVendorFoodItems.this);
                }
                break;
        }

    }

    //Handle Permission

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgPath = data.getData();
        imgFood.setImageURI(imgPath);

//
//        if (resultCode != RESULT_CANCELED) {
//            switch (requestCode) {
//                case 0:
//                    if (resultCode == RESULT_OK && data != null) {
//                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
//                        imgFood.setImageBitmap(selectedImage);
//                    }
//                    break;
//                case 1:
//                    if (resultCode == RESULT_OK && data != null) {
//
//
//
//
////                        Uri selectedImage = data.getData();
////                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
////                        if (selectedImage != null) {
////                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
////                            if (cursor != null) {
////                                cursor.moveToFirst();
////                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
////                                String picturePath = cursor.getString(columnIndex);
////                                imgFood.setImageBitmap(BitmapFactory.decodeFile(picturePath));
////                                cursor.close();
////                            }
////                        }
//                    }
//                    break;
//            }
//        }
        //  Uri imgPath=data.getData();
        //  imgFood.setImageURI(imgPath);
    }

    private void uploadData() {
        Log.i("--->dataaoo:: ", " called "  );
        //UUID.randomUUID();
        String Image_id = UUID.randomUUID().toString()+ "_" + System.currentTimeMillis() ;

        StorageReference imageRef = storageReference.child(Image_id);
        imageRef.putFile(imgPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {


                        ModelFoodItem modelFoodItem = new ModelFoodItem(tilFoodName.getEditText().getText().toString(), tilFoodPrice.getEditText().getText().toString(), tilFoodDesc.getEditText().getText().toString(), task.getResult().toString());

                        String foodItemKey = foodMartApp.getDbFoodMartFoodItems().push().getKey();
                        Log.i("--->data_0::12 ", " foodItemKey: " + foodItemKey);
                        foodMartApp.getDbFoodMartFoodItems().child(foodItemKey).setValue(modelFoodItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i("--->data_successfull:: ", " " + task);
                                    if (foodMartApp.getFoodMartVendor().getFoodListId() == null) {
                                        foodMartApp.getFoodMartVendor().setFoodListId(new ArrayList<>());
                                    }

                                    //added food Item locally
                                    foodMartApp.getFoodMartVendor().getFoodListId().add(foodItemKey);//local
                                    //temporary start

                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHY8cb31fpdWDe9rMLW");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYBwtJplmYALb55Eyk");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYC6GTymZSDWGmQWcm");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYCWXGnSnAccrqMAwz");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYDyqYJt-QPhfNhe6J");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYE1e8_mP0oMsZ_z1t");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYFe_eOT0pcPyUTVd1");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYFn34d9c29KHrEDtR");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYIZ8gblwuw2c18Xj0");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYIk_e-K4xOh5S390Y");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYcrKKJcorMmC8FfqC");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYd7j9lNQ8jgMhJm_w");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYdIeL1W1UsT28AjY8");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYdOw2-l0js-GatZ-b");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYdulSSDAa4mgSXq1V");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYdzEFfSK-TQkfHgMA");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYe2y3D0zsBXfMm1G3");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYe8bl6A4ZiN2J5Tt2");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYeEMmXUCniFzSl2qI");
                                    foodMartApp.getFoodMartVendor().getFoodListId().add("-NHYeK0SEw2ZklWvorU5");

                                    //temporary end

                                    ModelVendor user = foodMartApp.getFoodMartVendor();

                                    HashMap<String, Object> result = new HashMap<>();
                                    result.put(foodMartApp.getFirebaseAuth().getUid(), user);
                                    foodMartApp.getDbRefVendor().child(foodMartApp.getFirebaseAuth().getUid()).updateChildren(result);

                                  /*  HashMap<String, Object> data = new HashMap<>();
                                    data.put("food-items-ids",foodMartApp.getFoodMartVendor().getFoodListId());
                                    foodMartApp.getDbRefVendor().child(foodMartApp.getFirebaseAuth().getUid()).updateChildren(data);
                                    foodMartApp.getDbRefVendor().child(foodMartApp.getFirebaseAuth().getUid()).child("foodItemIds").setValue(foodMartApp.getFoodMartVendor().getFoodItemIds()).addOnCompleteListener(task11 -> {
*/

                                    //  foodMartApp.getDbRefVendor().child(foodMartApp.getFirebaseAuth().getUid()).child("foodItemIds").updateChildren(foodMartApp.getFoodMartVendor().getFoodItemIds())


                                } else {
                                    Log.i("--->data_1234:: ", " " + task.getException());
                                }
                            }
                        });

      /*                  foodMartApp.getDbFoodMartFoodItems().push().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String key = snapshot.getKey();
                                Log.i("--->data_1:: ", " " + key);

                                foodMartApp.getDbFoodMartFoodItems().child(key).setValue(modelFoodItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.i("--->dataaoo:: ", " " + task.isSuccessful());
                                        //foodMartApp.getFirebaseAuth().getUid()

                                        foodMartApp.getDbRefVendor().child(foodMartApp.getFirebaseAuth().getUid()).child("food-items-id").setValue(key);

//                                        foodMartApp.getDbRefVendor().updateChildren();
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.i("--->dataaL:: ", " " + error);
                            }
                        });*/

                    }
                });

                Toast.makeText(foodMartApp, "Successfull", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("--->dataaL ", " " + e);
            }
        });
    }


}
