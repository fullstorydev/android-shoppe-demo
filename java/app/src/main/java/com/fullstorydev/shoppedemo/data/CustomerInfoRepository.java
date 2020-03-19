package com.fullstorydev.shoppedemo.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.utilities.Constants;


public class CustomerInfoRepository {
    private static final String TAG = "CustomerInfoRepository";
    private SharedPreferences sharedPref;
    private CustomMutableLiveData info; // single point of truth of the customer info
    private Application application;
    private MutableLiveData<Boolean> isLoading;

    public CustomerInfoRepository(Application application) {
        this.application = application;
        info = new CustomMutableLiveData();
        isLoading = new MutableLiveData<>();
        isLoading.setValue(true);
        getInfoFromSharedPref();
    }

    private void getInfoFromSharedPref() {
        // To persist user input, CustomerInfo contains simple key-value pairs, thus using lighter weight shared preference instead of sql
        // !! All customer information are saved as plain text for demo purpose only. Please handle your user data properly !!
        // taken this off UI thread and post the values whenever finished reading from shared pref
        new Thread(() -> {
            sharedPref = application.getSharedPreferences(application.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            CustomerInfo newInfo = new CustomerInfo();
            for(String key:Constants.USER_INFO_STRING_KEYS){
                String val = sharedPref.getString(key,"");
                if(!newInfo.putValue(key,val)) Log.e(TAG,"failed to put string value: " + key + ": " + val);
            }
            for(String key:Constants.USER_INFO_INTEGER_KEYS){
                int val = sharedPref.getInt(key,0);
                newInfo.putValue(key,val);
                if(!newInfo.putValue(key,val)) Log.e(TAG,"failed to put int value: " + key + ": " + val);
            }
            info.postValue(newInfo);
            isLoading.postValue(false);
        }).start();
    }

    public LiveData<CustomerInfo> getInfoMap(){ return info; }
    public LiveData<Boolean> getIsLoading(){ return isLoading; }

    // update Shared Pref String values, and emit the change to info
    public void updateInfo(String key, String val){
        sharedPref.edit().putString(key, val).apply();
        info.putValue(key,val);
    }
    // overload to update Shared Pref int values, and emit the change to info
    public void updateInfo(String key, int val){
        sharedPref.edit().putInt(key, val).apply();
        info.putValue(key,val);
        CustomerInfo map = info.getValue();
    }


    class CustomMutableLiveData extends MutableLiveData<CustomerInfo> {
        public void putValue(String key, int val) {
            getValue().putValue(key,val);
            postValue(getValue());
        }
        public void putValue(String key, String val) {
            getValue().putValue(key,val);
            postValue(getValue());
        }
    }
}