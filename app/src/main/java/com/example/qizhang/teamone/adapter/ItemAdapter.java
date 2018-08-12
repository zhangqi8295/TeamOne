package com.example.qizhang.teamone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qizhang.teamone.Constant;
import com.example.qizhang.teamone.MainActivity;
import com.example.qizhang.teamone.R;
import com.example.qizhang.teamone.model.broswe.ItemSummary;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<HorizontalItemViewHolder>{
    private int viewType;
    private List<ItemSummary> itemSummaries;
    private Context context;

    public ItemAdapter(Context ctx, List<ItemSummary> itemSummaries, int viewType) {
        this.context = ctx;
        this.itemSummaries = itemSummaries;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public HorizontalItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v;
        if (viewType == Constant.HORIZONTAL) {
            v = inflater.inflate(R.layout.horizontal_list_view, parent, false);
        } else {
            v = inflater.inflate(R.layout.list_item_product, parent, false);
        }
        return new HorizontalItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalItemViewHolder holder, int position) {
        ItemSummary summary = itemSummaries.get(position);
        holder.bindData(summary);
    }

    @Override
    public int getItemCount() {
        return itemSummaries.size();
    }


}
