package com.fullstorydev.shoppedemo.ui.market;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fullstorydev.shoppedemo.data.Product;
import com.fullstorydev.shoppedemo.data.ProductRepository;

import java.util.List;

public class MarketViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<Product>> mProductList;

    public MarketViewModel(Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mProductList = mRepository.getAllFromAPI();
    }

    public LiveData<List<Product>> getProductList() {
        return mProductList;
    }

    public void increaseQuantityInCart(Product product){
        mRepository.increaseQuantityInCart(product);
    }
}