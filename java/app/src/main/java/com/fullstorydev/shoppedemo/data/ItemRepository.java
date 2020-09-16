package com.fullstorydev.shoppedemo.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {
    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems; //items are in cart
    private LiveData<Double> mSubtotal;
    private LiveData<Integer> mItemCount;

    // Note that in order to unit test the class, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public ItemRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAll();
        mSubtotal = mItemDao.getSubtotal();
        mItemCount = mItemDao.getItemCount();
    }

    // get items from cart - data saved in DB
    public LiveData<List<Item>> getAllItemsFromDB() {
        return mAllItems;
    }

    public LiveData<Double> getSubtotal(){ return mSubtotal; }

    public LiveData<Integer> getItemCount(){ return mItemCount; }


    public void insert(Item item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.insert(item);
        });
    }

    public void increaseQuantityInCart(Item item){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.increaseQuantityOrInsert(item);
        });
    }

    public void decreaseQuantityInCart(Item item){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.decreaseQuantityOrDelete(item);
        });
    }
}
