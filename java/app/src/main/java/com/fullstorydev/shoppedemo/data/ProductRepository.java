package com.fullstorydev.shoppedemo.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.utilities.JsonHelper;
import com.fullstorydev.shoppedemo.utilities.NetworkUtils;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.List;

public class ProductRepository {
    private MutableLiveData<List<Item>> mAllProducts; //products are sold in shop, fetched from API

    public ProductRepository(Application application) {
        mAllProducts = new MutableLiveData<>();

        // get from API every time
        //TODO: save the product locally and call API in the backend, only update DB when it changes and notify and handle change
        new Thread(() -> {
            String hostURLStr = application.getString(R.string.products_endpoint);
            try {
                String resStr = NetworkUtils.getProductListFromURL(hostURLStr);
                List<Item> list = JsonHelper.getProductListFromJsonString(resStr);
                mAllProducts.postValue(list);
            } catch (IOException | JsonParseException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public LiveData<List<Item>> getAllFromAPI() { return mAllProducts; }
}
