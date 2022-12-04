package com.example.testapp.adapters.viewholderes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;

public class ViewHolderFoodItemVendor extends RecyclerView.ViewHolder {

    public TextView getFoodName() {
        return foodName;
    }

    public TextView getFoodPrice() {
        return foodPrice;
    }

    public TextView getFoodDesc() {
        return foodDesc;
    }

  /*  public ImageView getEditImage() {
        return editImage;
    }

    public ImageView getDeleteImage() {
        return deleteImage;
    }*/

    TextView foodName, foodPrice, foodDesc;
    ImageView editImage, deleteImage, foodImage;


    public void setFoodName(TextView foodName) {
        this.foodName = foodName;
    }

    public ImageView getFoodImage() {
        return foodImage;
    }

    public ViewHolderFoodItemVendor(@NonNull View itemView) {
        super(itemView);

        foodName = itemView.findViewById(R.id.food_name);
        foodPrice = itemView.findViewById(R.id.food_price);
        foodDesc = itemView.findViewById(R.id.food_desc);
        foodImage = itemView.findViewById(R.id.food_item_img);
        //editImage = itemView.findViewById(R.id.food_edit_img);
        //deleteImage = itemView.findViewById(R.id.food_delete_img);

    }
}
