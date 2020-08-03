package com.fullstorydev.shoppedemo.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fullstory.FS;
import com.fullstorydev.shoppedemo.utilities.CrashlyticsUtil;

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
            FS.event("Product Added",item.getItemMap());

            String logMsg = "Product added." + item.getItemMap();
            CrashlyticsUtil.log(logMsg);
        });
    }

    public void decreaseQuantityInCart(Item item){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.decreaseQuantityOrDelete(item);

            FS.event("Product Removed",item.getItemMap());

            String logMsg = "Product removed." + item.getItemMap();
            CrashlyticsUtil.log(logMsg);
        });
    }
}
