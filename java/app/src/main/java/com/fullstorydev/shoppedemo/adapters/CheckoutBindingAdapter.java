package com.fullstorydev.shoppedemo.adapters;

import android.widget.AutoCompleteTextView;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputLayout;

public class CheckoutBindingAdapter {
    @BindingAdapter({"selectedValue"})
    public static void setSelectedValue (AutoCompleteTextView s, int position) {
        String text = String.valueOf(s.getAdapter().getItem(position));
        // set filter to false: disable the filtering and always show full list
        s.setText(text,false);
    }

    @BindingAdapter({"errorMessageValue"})
    public static void setErrorMessageValue (TextInputLayout inputLayout, String err) {
        inputLayout.setError(err);
    }
}
