package com.example.qizhang.teamone.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qizhang.teamone.R;
import com.example.qizhang.teamone.model.broswe.ItemSummary;
import com.squareup.picasso.Picasso;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView itemImage;
    private TextView itemName;
    private TextView itemPrice;

    ItemViewHolder(View itemView) {
        super(itemView);
        itemImage = itemView.findViewById(R.id.item_product_image);
        itemName = itemView.findViewById(R.id.item_product_name);
        itemPrice = itemView.findViewById(R.id.item_product_price);
    }

    void bindData(ItemSummary itemSummary) {

        Picasso.get().load(itemSummary.getImage().getImageUrl()).into(itemImage);
        itemName.setText(itemSummary.getTitle());
        itemPrice.setText(itemSummary.getPrice().getValue());
    }
}
