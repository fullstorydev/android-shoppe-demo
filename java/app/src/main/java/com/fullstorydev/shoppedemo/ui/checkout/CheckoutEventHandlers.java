package com.fullstorydev.shoppedemo.ui.checkout;

import com.fullstorydev.shoppedemo.data.CustomerInfo;

public interface CheckoutEventHandlers {
    void onClickPurchase(CustomerInfo customerInfo, Double subtotal);
}
