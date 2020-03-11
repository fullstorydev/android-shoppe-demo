package com.fullstorydev.shoppedemo.ui.cart;

import com.fullstorydev.shoppedemo.data.Product;

public interface CartEventHandlers {
    void onClickRemoveFromCart(Product item);
}
