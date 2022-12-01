package com.example.testapp.activities;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.FoodMartApp;
import com.example.testapp.R;
import com.example.testapp.adapters.AdapterFoodItemsVendor;
import com.example.testapp.model.ModelFoodItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class ActivityVendorFoodItems extends AppCompatActivity {

    private RecyclerView rcvFoodItems;
    private FoodMartApp foodMartApp;
    private ImageView imgFood;
    private ArrayList<ModelFoodItem> arrayListFoodItems;
    private Uri imgURI;
    private TextInputLayout tilFoodName, tilFoodDesc, tilFoodPrice;
    private AdapterFoodItemsVendor adapterFoodItemsVendor;
    private ProgressDialog progressDialog;
    private BottomSheetBehavior bottomSheetManageFood;
    //0 - new 1-edit
    private int bottomSheetMode = 0;
    private String editFoodItemId;
    private String editFoodItemImageUrl;

    public String getEditFoodItemImageUrl() {
        return editFoodItemImageUrl;
    }

    public void setEditFoodItemImageUrl(String editFoodItemImageUrl) {
        this.editFoodItemImageUrl = editFoodItemImageUrl;
    }

    public String getEditFoodItemId() {
        return editFoodItemId;
    }

    public void setEditFoodItemId(String editFoodItemId) {
        this.editFoodItemId = editFoodItemId;
    }

    public BottomSheetBehavior getBottomSheetManageFood() {
        return bottomSheetManageFood;
    }

    public void setBottomSheetManageFood(BottomSheetBehavior bottomSheetManageFood) {
        this.bottomSheetManageFood = bottomSheetManageFood;
    }

    public int getBottomSheetMode() {
        return bottomSheetMode;
    }

    public void setBottomSheetMode(int bottomSheetMode) {
        this.bottomSheetMode = bottomSheetMode;
    }

    public FoodMartApp getFoodMartApp() {
        return foodMartApp;
    }

    public ArrayList<ModelFoodItem> getArrayListFoodItems() {
        return arrayListFoodItems;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_vendor_food_items);
    }

    public ImageView getImgFood() {
        return imgFood;
    }

    public TextInputLayout getTilFoodName() {
        return tilFoodName;
    }

    public TextInputLayout getTilFoodDesc() {
        return tilFoodDesc;
    }

    public TextInputLayout getTilFoodPrice() {
        return tilFoodPrice;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        foodMartApp = (FoodMartApp) getApplication();
        arrayListFoodItems = new ArrayList<>();
        rcvFoodItems = findViewById(R.id.RCV_FOOD_ITEMS);
        imgFood = findViewById(R.id.IMG_FOOD);
        tilFoodName = findViewById(R.id.TIL_FOOD_NAME);
        tilFoodDesc = findViewById(R.id.TIL_FOOD_DESC);
        tilFoodPrice = findViewById(R.id.TIL_FOOD_PRICE);
        bottomSheetManageFood = BottomSheetBehavior.from(findViewById(R.id.BTMSHEET));

        rcvFoodItems.setLayoutManager(new LinearLayoutManager(ActivityVendorFoodItems.this));
        adapterFoodItemsVendor = new AdapterFoodItemsVendor(ActivityVendorFoodItems.this);
        rcvFoodItems.setAdapter(adapterFoodItemsVendor);

        findViewById(R.id.BTN_ADD).setOnClickListener(view -> {
            bottomSheetMode = 0;
            changeBottomSheetUI();
            bottomSheetManageFood.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        imgFood.setOnClickListener(view -> {
            Intent intentImgPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intentImgPicker.setType("image/*");
            intentImgPicker.putExtra("return-data", true);
            startActivityForResult(intentImgPicker, 12);
        });

        findViewById(R.id.BTN_MANAGE_FOOD_ITEM).setOnClickListener(view -> {

            if (bottomSheetMode == 0) {
                createFoodItem();
            } else {
                editFoodItem();
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            imgURI = data.getData();
            imgFood.setImageURI(imgURI);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        foodMartApp.getFoodMartVendor().setFoodItemIds(foodMartApp.getFoodMartVendor().getFoodItemIds()==null?new ArrayList<>():foodMartApp.getFoodMartVendor().getFoodItemIds());

        foodMartApp.getDbRefVendor().child(foodMartApp.getFirebaseAuth().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodMartApp.getDbRefFoodItems().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayListFoodItems.clear();
                        for (DataSnapshot foodItem : snapshot.getChildren()) {
                            if (foodMartApp.getFoodMartVendor().getFoodItemIds().contains(foodItem.getKey())) {
                                arrayListFoodItems.add(foodItem.getValue(ModelFoodItem.class));
                            }
                        }
                        adapterFoodItemsVendor.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }


    public Uri getImgURI() {
        return imgURI;
    }

    public void setImgURI(Uri imgURI) {
        this.imgURI = imgURI;
    }

    private void createFoodItem() {


        progressDialog = new ProgressDialog(ActivityVendorFoodItems.this);
        progressDialog.setTitle("Creating Food Item");
        progressDialog.setMessage("Please wait while we add your food item");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String imgPath = "foodMartImgs/" + UUID.randomUUID().toString();

        if (imgURI != null) {

            foodMartApp.getFirebaseStorage().getReference().child(imgPath).putFile(imgURI).addOnSuccessListener(runnable -> {
                foodMartApp.getFirebaseStorage().getReference().child(imgPath).getDownloadUrl().addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        ModelFoodItem foodItem = new ModelFoodItem(tilFoodName.getEditText().getText().toString(), tilFoodDesc.getEditText().getText().toString(), tilFoodPrice.getEditText().getText().toString(), task.getResult().toString());
                        String foodItemKey = foodMartApp.getDbRefFoodItems().push().getKey();

                        foodMartApp.getDbRefFoodItems().child(foodItemKey).setValue(foodItem).addOnCompleteListener(task1 -> {

                            if (task1.isSuccessful()) {
                                if (foodMartApp.getFoodMartVendor().getFoodItemIds() == null) {
                                    foodMartApp.getFoodMartVendor().setFoodItemIds(new ArrayList<>());
                                }

                                //added food Item locally
                                foodMartApp.getFoodMartVendor().getFoodItemIds().add(foodItemKey);

                                foodMartApp.getDbRefVendor().child(foodMartApp.getFirebaseAuth().getUid()).child("foodItemIds").setValue(foodMartApp.getFoodMartVendor().getFoodItemIds()).addOnCompleteListener(task11 -> {
                                    if (task11.isSuccessful()) {
                                        Toast.makeText(ActivityVendorFoodItems.this, "Food Item Added Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ActivityVendorFoodItems.this, "Food Item Added Partially", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();

                                });

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(foodMartApp, "Failed To Write Food Item", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(foodMartApp, "Failed To Get Image URL", Toast.LENGTH_SHORT).show();
                    }
                });

            }).addOnFailureListener(runnable -> {
                progressDialog.dismiss();
                Toast.makeText(foodMartApp, "Failed To Upload Image", Toast.LENGTH_SHORT).show();
            });

        } else {
            Toast.makeText(foodMartApp, "Please Pick an Image", Toast.LENGTH_SHORT).show();
        }

    }

    private void editFoodItem() {
        progressDialog = new ProgressDialog(ActivityVendorFoodItems.this);
        progressDialog.setTitle("Creating Food Item");
        progressDialog.setMessage("Please wait while we edit your food item");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ModelFoodItem modelFoodItem = new ModelFoodItem();
        modelFoodItem.setName(tilFoodName.getEditText().getText().toString());
        modelFoodItem.setDescription(tilFoodDesc.getEditText().getText().toString());
        modelFoodItem.setPrice(tilFoodPrice.getEditText().getText().toString());
        modelFoodItem.setImageURL(getEditFoodItemImageUrl());

        if (getImgURI() != null) {
            String imgPath = "foodMartImgs/" + UUID.randomUUID().toString();

            foodMartApp.getFirebaseStorage().getReference().child(imgPath).putFile(imgURI).addOnSuccessListener(taskSnapshot -> {
                foodMartApp.getFirebaseStorage().getReference().child(imgPath).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            modelFoodItem.setImageURL(task.getResult().toString());
                            //upload the changes
                            pushEditFoodItem(modelFoodItem);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(foodMartApp, "Failed To Get URL", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }).addOnFailureListener(e -> {
                progressDialog.dismiss();
                Toast.makeText(foodMartApp, "Failed To Upload new File", Toast.LENGTH_SHORT).show();
            });
        } else {
            pushEditFoodItem(modelFoodItem);
        }
    }

    private void pushEditFoodItem(ModelFoodItem modelFoodItem) {
        foodMartApp.getDbRefFoodItems().child(getEditFoodItemId()).setValue(modelFoodItem).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(foodMartApp, "edited", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(foodMartApp, "Failed To push changes", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        });
    }


    public void changeBottomSheetUI() {
        if (bottomSheetMode == 0) {
            ((Button) findViewById(R.id.BTN_MANAGE_FOOD_ITEM)).setText("Create Food Item");
        } else {
            ((Button) findViewById(R.id.BTN_MANAGE_FOOD_ITEM)).setText("Edit Food Item");
        }
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetManageFood.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetManageFood.setState(STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}
