package com.fullstorydev.shoppedemo.ui.checkout;

import android.view.View;

import com.fullstorydev.shoppedemo.data.CustomerInfo;

public interface CheckoutEventHandlers {
    void onClickPurchase(CustomerInfo customerInfo, Double subtotal);
    View.OnTouchListener getOnTouchListener();
}
