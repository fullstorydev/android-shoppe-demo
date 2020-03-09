package com.fullstorydev.shoppedemo.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class MarketProductBingdingAdapter {
    @BindingAdapter({"imageURL"})
    public static void setImageURL(ImageView view, String url) {
        //place holder adapter for loading the image from url and set it to the view
    }
}