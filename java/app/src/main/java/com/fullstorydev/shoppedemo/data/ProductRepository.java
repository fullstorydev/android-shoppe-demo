package com.fullstorydev.shoppedemo.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {

    private ProductDao mProductDao;
    private LiveData<List<Product>> mAllProducts;

    // Note that in order to unit test the class, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mProductDao = db.productDao();
        mAllProducts = mProductDao.getAll();
    }

    // Room executes all **queries** on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Product>> getAll() {
        return mAllProducts;
    }

    // You must call these on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertAll(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.insert(product);
        });
    }

    public void increaseQuantityInCart(Product product){
            mProductDao.increaseQuantityOrInsert(product);
    }

    public void decreaseQuantityInCart(Product product){
            mProductDao.decreaseQuantityOrDelete(product);
    }
}
