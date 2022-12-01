package com.example.testapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.activities.ActivityVendorFoodItems;
import com.example.testapp.adapters.viewholders.ViewHolderFoodItemVendor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

public class AdapterFoodItemsVendor extends RecyclerView.Adapter<ViewHolderFoodItemVendor> {


    private ActivityVendorFoodItems activityVendorFoodItems;

    public AdapterFoodItemsVendor(ActivityVendorFoodItems activityVendorFoodItems) {
        this.activityVendorFoodItems = activityVendorFoodItems;
    }

    @NonNull
    @Override
    public ViewHolderFoodItemVendor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderFoodItemVendor(LayoutInflater.from(activityVendorFoodItems).inflate(R.layout.raw_food_item_vendor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFoodItemVendor holder, int position) {

        holder.getTvFoodName().setText(activityVendorFoodItems.getArrayListFoodItems().get(position).getName());
        holder.getTvFoodDesc().setText(activityVendorFoodItems.getArrayListFoodItems().get(position).getDescription());
        holder.getTvFoodPrice().setText(activityVendorFoodItems.getArrayListFoodItems().get(position).getPrice());

        Picasso.get().load(activityVendorFoodItems.getArrayListFoodItems().get(position).getImageURL()).into(holder.getImgFood());

        holder.getImgDelete().setOnClickListener(view -> {
            activityVendorFoodItems.getFoodMartApp().getFoodMartVendor().getFoodItemIds().remove(position);
            //push the changes
            activityVendorFoodItems.getFoodMartApp().getDbRefVendor().child(activityVendorFoodItems.getFoodMartApp().getFirebaseAuth().getCurrentUser().getUid()).child("foodItemIds").setValue(activityVendorFoodItems.getFoodMartApp().getFoodMartVendor().getFoodItemIds()).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(activityVendorFoodItems, "Deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(activityVendorFoodItems, "Failed To Delete", Toast.LENGTH_SHORT).show();
                }
            });


        });

        holder.getImgEdit().setOnClickListener(view -> {
            activityVendorFoodItems.setBottomSheetMode(1);
            activityVendorFoodItems. changeBottomSheetUI();
            activityVendorFoodItems.getBottomSheetManageFood().setState(BottomSheetBehavior.STATE_EXPANDED);


            activityVendorFoodItems.getTilFoodName().getEditText().setText(activityVendorFoodItems.getArrayListFoodItems().get(position).getName());
            activityVendorFoodItems.getTilFoodDesc().getEditText().setText(activityVendorFoodItems.getArrayListFoodItems().get(position).getDescription());
            activityVendorFoodItems.getTilFoodPrice().getEditText().setText(activityVendorFoodItems.getArrayListFoodItems().get(position).getPrice());
            Picasso.get().load( activityVendorFoodItems.getArrayListFoodItems().get(position).getImageURL()).into(activityVendorFoodItems.getImgFood());

            activityVendorFoodItems.setEditFoodItemId(activityVendorFoodItems.getFoodMartApp().getFoodMartVendor().getFoodItemIds().get(position));
            activityVendorFoodItems.setEditFoodItemImageUrl(activityVendorFoodItems.getArrayListFoodItems().get(position).getImageURL());

            activityVendorFoodItems.setImgURI(null);

        });


    }

    @Override
    public int getItemCount() {
        return activityVendorFoodItems.getArrayListFoodItems().size();
    }
}
