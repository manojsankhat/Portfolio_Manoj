package com.example.testapp.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;

public class ViewHolderFoodItemVendor extends RecyclerView.ViewHolder {

    private TextView tvFoodName,tvFoodDesc,tvFoodPrice;
    private ImageView imgFood;
    private ImageView imgEdit;

    public TextView getTvFoodName() {
        return tvFoodName;
    }

    public TextView getTvFoodDesc() {
        return tvFoodDesc;
    }

    public TextView getTvFoodPrice() {
        return tvFoodPrice;
    }

    public ImageView getImgFood() {
        return imgFood;
    }

    public ImageView getImgEdit() {
        return imgEdit;
    }

    public ImageView getImgDelete() {
        return imgDelete;
    }

    private ImageView imgDelete;

    public ViewHolderFoodItemVendor(@NonNull View itemView) {
        super(itemView);

        tvFoodName=itemView.findViewById(R.id.TV_FOOD_NAME);
        tvFoodDesc=itemView.findViewById(R.id.TV_FOOD_DESC);
        tvFoodPrice=itemView.findViewById(R.id.TV_FOOD_PRICE);
        imgFood=itemView.findViewById(R.id.IMG_FOOD);
        imgEdit=itemView.findViewById(R.id.IMG_EDIT);
        imgDelete=itemView.findViewById(R.id.IMG_DELETE);


    }


}
