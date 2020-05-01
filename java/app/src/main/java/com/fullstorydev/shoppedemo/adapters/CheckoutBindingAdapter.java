package com.fullstorydev.shoppedemo.adapters;

import android.widget.AutoCompleteTextView;

import androidx.databinding.BindingAdapter;

public class CheckoutBindingAdapter {
    @BindingAdapter({"selectedValue"})
    public static void setSelectedValue (AutoCompleteTextView s, int position) {
        String text = String.valueOf(s.getAdapter().getItem(position));
        // set filter to false: disable the filtering and always show full list
        s.setText(text,false);
    }
}
