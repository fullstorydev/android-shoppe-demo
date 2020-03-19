package com.fullstorydev.shoppedemo.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class CheckoutBindingAdapter {
    @BindingAdapter({"selectedValue"})
    public static void setSelectedValue (Spinner s, int position) {
        s.setSelection(position);
    }

//    @BindingAdapter({"text"})
//    public static void setText(EditText et, String val){
//        et.setText(val);
//    }
//
//    @InverseBindingAdapter(attribute = "text")
//    public static String getText(EditText et){
//        return String.valueOf(et.getText());
//    }
//
//    @BindingAdapter("app:textAttrChanged")
//    public static void setListeners(
//            EditText view, final InverseBindingListener attrChange) {
////
////        final TextWatcher newValue = new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                Log.d("here",String.valueOf(s));
////
////            }
////            @Override
////            public void afterTextChanged(Editable s) {
////
////            }
////        };
////        view.addTextChangedListener(newValue);
//    }
}
