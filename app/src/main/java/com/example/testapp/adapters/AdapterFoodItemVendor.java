package com.example.testapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testapp.R;
import com.example.testapp.activities.ActivityVendorFoodItems;
import com.example.testapp.adapters.viewholderes.ViewHolderFoodItemVendor;
import com.example.testapp.model.ModelFoodItem;

import java.util.ArrayList;
import java.util.List;


public class AdapterFoodItemVendor extends RecyclerView.Adapter<ViewHolderFoodItemVendor> {


    private ActivityVendorFoodItems activityVendorFoodItems;



    private ArrayList<ModelFoodItem> mModelFoodItem = new ArrayList<>();

    public AdapterFoodItemVendor(ArrayList<ModelFoodItem> arrayListFoodItems, ActivityVendorFoodItems activityVendorFoodItems) {
        this.mModelFoodItem = arrayListFoodItems;
        this.activityVendorFoodItems = activityVendorFoodItems;
    }


 /*   public AdapterFoodItemVendor(ActivityVendorFoodItems activityVendorFoodItems) {
        this.activityVendorFoodItems = activityVendorFoodItems;
    }*/


    @NonNull
    @Override
    public ViewHolderFoodItemVendor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderFoodItemVendor(LayoutInflater.from(activityVendorFoodItems).inflate(R.layout.row_food_items_vendor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFoodItemVendor holder, int position) {
        holder.getFoodName().setText(mModelFoodItem.get(position).getName());
        holder.getFoodPrice().setText(mModelFoodItem.get(position).getPrice());
        holder.getFoodDesc().setText(mModelFoodItem.get(position).getFoodDescription());
        Glide.with(holder.itemView.getContext()).load(mModelFoodItem.get(position).getFoodDescription()).into(holder.getFoodImage());
    }

    @Override
    public int getItemCount() {
      //  return activityVendorFoodItems.getArrayListFoodItems().size();
        return mModelFoodItem.size();
    }

    public void abcd() {
        notifyDataSetChanged();
    }
}
