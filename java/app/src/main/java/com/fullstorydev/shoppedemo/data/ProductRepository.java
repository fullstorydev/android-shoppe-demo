package com.fullstorydev.shoppedemo.data;

import android.app.Application;
import android.content.res.Resources;
import android.os.AsyncTask;

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

    // Note that in order to unit test the class, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mProductDao = db.productDao();
        mAllItems = mProductDao.getAll();
        String hostURLStr = Resources.getSystem().getString(R.string.aws_host);
        new FetchFromAPI().execute(hostURLStr);
    }

    class FetchFromAPI extends AsyncTask<String,Void,List<Product>> {
        @Override
        protected List<Product> doInBackground(String... urls) {
            if(urls.length>0){
                try{
                    NetworkUtils.getProductListFromURL(urls[0]);
                    return JsonHelper.getProductListFromJsonString(urls[0]);
                }catch (IOException | JsonParseException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute( List<Product> list) {
            if(list != null){
                mAllProducts.postValue(list);
            }
        }
    }

    public LiveData<List<Product>> getAllFromDB() {
        return mAllItems;
    }

    public LiveData<List<Product>> getAllFromAPI() { return mAllProducts; }

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
