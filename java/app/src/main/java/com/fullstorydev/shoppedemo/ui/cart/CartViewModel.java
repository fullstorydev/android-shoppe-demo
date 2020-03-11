package com.fullstorydev.shoppedemo.ui.cart;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fullstorydev.shoppedemo.data.Product;
import com.fullstorydev.shoppedemo.data.ProductRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<Product>> mItemList;

    public CartViewModel(Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mItemList = mRepository.getAllItemsFromDB();
    }

    public LiveData<List<Product>> getItemList() {
        return mItemList;
    }

    public void decreaseQuantityInCart(Product product){
        mRepository.decreaseQuantityInCart(product);
    }
}