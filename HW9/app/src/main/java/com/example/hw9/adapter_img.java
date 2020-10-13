package com.example.hw9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter_img extends RecyclerView.Adapter<adapter_img.ViewHolder>  {


    private List<data_structure_img> mFruitList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;

        public ViewHolder(View view) {
            super(view);
            fruitImage = view.findViewById(R.id.imageView25);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.container_frag_photos_img, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        data_structure_img fruit = mFruitList.get(position);
        Picasso.with(context).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public adapter_img( Context context,List<data_structure_img> fruitList) {
        mFruitList = fruitList;
        this.context = context;
    }

}