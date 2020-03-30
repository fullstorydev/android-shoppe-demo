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

    private ProductDao mProductDao;
    private LiveData<List<Product>> mAllItems; //items are in cart
    private MutableLiveData<List<Product>> mAllProducts; //products are sold in shop, fetched from API
    private LiveData<Double> mSubtotal;

    // Note that in order to unit test the class, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    // The repository is the only class that depends on multiple other classes; in this example, the repository depends on a persistent data model and a remote backend data source.
    // For more information on this app architecture, see: https://developer.android.com/jetpack/docs/guide
    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mProductDao = db.productDao();
        mAllItems = mProductDao.getAll();
        mSubtotal = mProductDao.getSubtotal();
        mAllProducts = new MutableLiveData<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String hostURLStr = application.getString(R.string.products_endpoint);
                try{
                    String resStr = NetworkUtils.getProductListFromURL(hostURLStr);
                    List<Product> list = JsonHelper.getProductListFromJsonString(resStr);
                    mAllProducts.postValue(list);
                }catch (IOException | JsonParseException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // get items from cart - data saved in DB
    public LiveData<List<Product>> getAllItemsFromDB() {
        return mAllItems;
    }

    // get products for market - data fetched from REST API via a async task
    public LiveData<List<Product>> getAllFromAPI() { return mAllProducts; }

    public LiveData<Double> getSubtotal(){ return mSubtotal; }

    public void insert(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.insert(product);
        });
    }

    public void increaseQuantityInCart(Product product){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.increaseQuantityOrInsert(product);
        });
    }

    public void decreaseQuantityInCart(Product product){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.decreaseQuantityOrDelete(product);
        });
    }
}
