package com.fullstorydev.shoppedemo.ui.cart;

import com.fullstorydev.shoppedemo.data.Item;

public interface CartEventHandlers {
    void onClickRemoveFromCart(Item item);
    void onClickAddToCart(Item item);
}
