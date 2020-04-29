package com.fullstorydev.shoppedemo.adapters;

import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;

public class CheckoutBindingAdapter {
    @BindingAdapter({"selectedValue"})
    public static void setSelectedValue (AutoCompleteTextView s, int position) {
        s.setSelection(position);
    }

    @BindingAdapter({"selectedValue"})
    public static void setSelectedValue (Spinner s, int position) {
        s.setSelection(position);
    }
}
