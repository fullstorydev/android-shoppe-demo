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

    public CartViewModel(Application application) {
        super(application);
        mItemRepository = new ItemRepository(application);
        mItemList = mItemRepository.getAllItemsFromDB();
    }

    public LiveData<List<Item>> getItemList() {
        return mItemList;
    }

    public void decreaseQuantityInCart(Item item){
        mItemRepository.decreaseQuantityInCart(item);
    }
}