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

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertAll(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.insertAll(product);
        });
    }

    public boolean updateQuantityInCartByOne(Product product,boolean isAdd){
        List<Product>  p = mProductDao.getAllByTitles(product.title);
        if(isAdd){
            if(p.size()>0){
                p.get(0).quantityInCart++;
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    mProductDao.updateQuantityInCart(p.get(0));
                });
                return true;
            }else{
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    mProductDao.insertAll(product);
                });
                return true;
            }
        }else{
            if(p.size()>0 && p.get(0).quantityInCart > 1){
                p.get(0).quantityInCart--;
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    mProductDao.updateQuantityInCart(p.get(0));
                });
                return true;
            }else if(p.size()>0 && p.get(0).quantityInCart == 1){
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    mProductDao.delete(product);
                });
                return true;
            }else{
                Log.e("ProductRepository","trying to remove a product that's not in cart");
                return false;
            }
        }
    }
}
