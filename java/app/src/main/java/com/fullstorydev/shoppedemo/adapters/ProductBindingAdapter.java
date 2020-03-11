package com.fullstorydev.shoppedemo.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.fullstorydev.shoppedemo.R;

public class ProductBindingAdapter {
    @BindingAdapter({"imageURL"})
    public static void setImageURL(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image_available)
                .into(view);
    }
}