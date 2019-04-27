package com.example.galleryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SingleGridItem> mItemArrayList;

    public CustomAdapter(Context mContext, ArrayList<SingleGridItem> mItemArrayList) {
        this.mContext = mContext;
        this.mItemArrayList = mItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mItemArrayList.get(i).getmImageName());
//        viewHolder.image.setImageResource(R.drawable.ic_menu_black_24dp);
//        Log.w("MainActivity","Glide url"+i+" :"+mItemArrayList.get(i).getmImageUrl());
        Glide.with(mContext)
                .load(mItemArrayList.get(i).getmImageUrl())
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mItemArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.description);
        }
    }
}
