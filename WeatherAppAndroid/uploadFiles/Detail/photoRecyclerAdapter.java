package com.example.weatherapp.Detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class photoRecyclerAdapter extends RecyclerView.Adapter<photoRecyclerAdapter.photoViewHolder> {

    private List<String> urls = new ArrayList<>();
    private Context mcontext ;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class photoViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a photo ] in this case
        public ImageView photoView;
        public photoViewHolder(ImageView v) {
            super(v);
            photoView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public photoRecyclerAdapter(Context context) {
        mcontext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public photoRecyclerAdapter.photoViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scene_photo, parent, false);

        photoViewHolder vh = new photoViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(photoViewHolder holder, int position) {
        String url = urls.get(position);
        Glide.with(mcontext)
                .load(url)
                .into(holder.photoView);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return urls.size();
        // return 8;
    }

    public void addList(List<String> urls){
        this.urls = urls;
        notifyDataSetChanged();
    }


}
