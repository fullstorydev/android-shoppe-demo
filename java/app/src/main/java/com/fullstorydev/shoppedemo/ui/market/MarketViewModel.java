package com.fullstorydev.shoppedemo.ui.market;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fullstorydev.shoppedemo.data.Item;
import com.fullstorydev.shoppedemo.data.ItemRepository;
import com.fullstorydev.shoppedemo.data.ProductRepository;

import java.util.List;

public class MarketViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private ItemRepository mItemRepository;
    private LiveData<List<Item>> mProductList;

    public MarketViewModel(Application application) {
        super(application);
        mProductRepository = new ProductRepository(application);
        mItemRepository = new ItemRepository(application);
        mProductList = mProductRepository.getAllFromAPI();
    }

    public LiveData<List<Item>> getProductList() {
        return mProductList;
    }

    public void increaseQuantityInCart(Item item){ mItemRepository.increaseQuantityInCart(item); }
}