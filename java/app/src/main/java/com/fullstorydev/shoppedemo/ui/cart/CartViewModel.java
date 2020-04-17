package com.fullstorydev.shoppedemo.ui.cart;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fullstorydev.shoppedemo.data.Item;
import com.fullstorydev.shoppedemo.data.ItemRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private ItemRepository mItemRepository;
    private LiveData<List<Item>> mItemList;
    private LiveData<Double> mSubtotal;

    public CartViewModel(Application application) {
        super(application);
        mItemRepository = new ItemRepository(application);
        mItemList = mItemRepository.getAllItemsFromDB();
        mSubtotal = mItemRepository.getSubtotal();
    }

    LiveData<List<Item>> getItemList() { return mItemList; }
    public LiveData<Double> getSubtotal() { return mSubtotal; }

    void decreaseQuantityInCart(Item item){ mItemRepository.decreaseQuantityInCart(item); }
    void increaseQuantityInCart(Item item){ mItemRepository.increaseQuantityInCart(item); }
}