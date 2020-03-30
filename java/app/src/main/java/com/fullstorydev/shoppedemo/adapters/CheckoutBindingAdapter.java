package com.fullstorydev.shoppedemo.adapters;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;

public class CheckoutBindingAdapter {
    @BindingAdapter({"selectedValue"})
    public static void setSelectedValue (Spinner s, int position) {
        s.setSelection(position);
    }

    @BindingAdapter("touchListener")
    public static void setTouchListener(View v, final View.OnTouchListener listener) {
        v.setOnTouchListener(listener);
    }
}
