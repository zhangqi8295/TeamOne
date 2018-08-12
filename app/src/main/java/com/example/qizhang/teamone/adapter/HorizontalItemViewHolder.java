package com.example.qizhang.teamone.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.qizhang.teamone.R;

import com.example.qizhang.teamone.model.broswe.ItemSummary;
import com.facebook.drawee.view.SimpleDraweeView;

public class HorizontalItemViewHolder extends RecyclerView.ViewHolder {
    private SimpleDraweeView imageView;
    private TextView itemName;
    private TextView itemPrice;

    public HorizontalItemViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.horizontal_item_image);
        itemName = itemView.findViewById(R.id.horizontal_item_name);
        itemPrice = itemView.findViewById(R.id.horizontal_item_disount_price);
    }

    public void bindData(ItemSummary itemSummary) {
        Uri uri = Uri.parse(itemSummary.getImage().getImageUrl());
        imageView.setImageURI(uri);
        itemName.setText(itemSummary.getTitle());
        itemPrice.setText(itemSummary.getPrice().getValue());
    }
}
