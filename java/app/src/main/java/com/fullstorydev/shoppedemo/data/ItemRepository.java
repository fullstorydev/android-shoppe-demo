package com.fullstorydev.shoppedemo.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.fullstory.FS;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.List;

public class ItemRepository {
    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems; //items are in cart
    private LiveData<Double> mSubtotal;

    // Note that in order to unit test the class, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public ItemRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAll();
        mSubtotal = mItemDao.getSubtotal();
    }

    // get items from cart - data saved in DB
    public LiveData<List<Item>> getAllItemsFromDB() {
        return mAllItems;
    }

    public LiveData<Double> getSubtotal(){ return mSubtotal; }

    public void insert(Item item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.insert(item);
        });
    }

    public void increaseQuantityInCart(Item item){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.increaseQuantityOrInsert(item);

            String logMsg = "Product added. Title: " + item.title + ", price: " + item.price ;
            // Crashlytics:
            FirebaseCrashlytics.getInstance().log(logMsg);
            FS.log(FS.LogLevel.INFO,logMsg);
        });
    }

    public void decreaseQuantityInCart(Item item){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.decreaseQuantityOrDelete(item);
        });
    }
}
