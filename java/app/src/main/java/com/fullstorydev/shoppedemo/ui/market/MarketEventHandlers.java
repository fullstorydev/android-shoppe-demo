package com.fullstorydev.shoppedemo.ui.market;

import com.fullstorydev.shoppedemo.data.Product;

// interface for all action handlers for the market fragment
public interface MarketEventHandlers {
    void onClickAddToCart(Product product);
}
