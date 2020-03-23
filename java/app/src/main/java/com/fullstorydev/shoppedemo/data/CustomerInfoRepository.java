package com.fullstorydev.shoppedemo.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fullstorydev.shoppedemo.utilities.Constants;


public class CustomerInfoRepository {
    private SharedPreferences sharedPref;
    private CustomerInfo info;

    private Application application;
    private MutableLiveData<Boolean> isLoading;

    public CustomerInfoRepository(Application application) {
        this.application = application;
        isLoading = new MutableLiveData<>();
        initInfoFromSharedPref();
    }

    public CustomerInfo getInfo() { return info; } // info may return null if still loading
    public LiveData<Boolean> getIsLoading(){ return isLoading; }

    public void updateInfo(CustomerInfo newInfo) throws IllegalArgumentException {
        isLoading.setValue(true);
        updateInfo(Constants.FIRST_NAME,newInfo.getFirstName());
        updateInfo(Constants.LAST_NAME,newInfo.getLastName());
        updateInfo(Constants.ADDRESS_1,newInfo.getAddress1());
        updateInfo(Constants.ADDRESS_2,newInfo.getAddress2());
        updateInfo(Constants.CITY,newInfo.getCity());
        updateInfo(Constants.STATE,newInfo.getState());
        updateInfo(Constants.ZIP,newInfo.getZip());
        updateInfo(Constants.CREDIT_CARD_NUMBER,newInfo.getCreditCardNumber());
        updateInfo(Constants.EXPIRATION_MONTH,newInfo.getExpirationMonth());
        updateInfo(Constants.EXPIRATION_YEAR,newInfo.getExpirationYear());
        updateInfo(Constants.SECURITY_CODE,newInfo.getSecurityCode());
        isLoading.setValue(false);
    }

    // initialize info values from shared pref and update loading state
    private void initInfoFromSharedPref() {
        // To persist user input, CustomerInfo contains simple key-value pairs, thus using lighter weight shared preference instead of sql
        // !! All customer information are saved as plain text for demo purpose only. Please handle your user data properly !!
        // taken this off UI thread and post the values whenever finished reading from shared pref
        isLoading.setValue(true);
        new Thread(() -> {
            sharedPref = application.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);

            String firstName = sharedPref.getString(Constants.FIRST_NAME,"");
            String lastName = sharedPref.getString(Constants.LAST_NAME,"");
            String address1 = sharedPref.getString(Constants.ADDRESS_1,"");
            String address2 = sharedPref.getString(Constants.ADDRESS_2,"");
            String city = sharedPref.getString(Constants.CITY,"");
            int state = sharedPref.getInt(Constants.STATE,0);
            String zip = sharedPref.getString(Constants.ZIP,"");
            String creditCardNumber = sharedPref.getString(Constants.CREDIT_CARD_NUMBER,"");
            int expirationMonth = sharedPref.getInt(Constants.EXPIRATION_MONTH,0);
            int expirationYear = sharedPref.getInt(Constants.EXPIRATION_YEAR,0);
            String securityCode = sharedPref.getString(Constants.SECURITY_CODE,"");

            info = new CustomerInfo.OrderBuilder()
                    .withName(firstName,lastName)
                    .withAddress(address1,address2,city,state,zip)
                    .withPayment(creditCardNumber,expirationMonth,expirationYear,securityCode)
                    .buildOrder();

            isLoading.postValue(false);
        }).start();
    }

    // update Shared Pref String values
    private void updateInfo(String key, String val) throws IllegalArgumentException{
        switch (key){
            case Constants.FIRST_NAME:
                info.setFirstName(val);
                break;
            case Constants.LAST_NAME:
                info.setLastName(val);
                break;
            case Constants.ADDRESS_1:
                info.setAddress1(val);
                break;
            case Constants.ADDRESS_2:
                info.setAddress2(val);
                break;
            case Constants.CITY:
                info.setCity(val);
                break;
            case Constants.ZIP:
                info.setZip(val);
                break;
            case Constants.CREDIT_CARD_NUMBER:
                info.setCreditCardNumber(val);
                break;
            case Constants.SECURITY_CODE:
                info.setSecurityCode(val);
                break;
            default:
                throw new IllegalArgumentException("invalid value for key: " + key);
        }
        sharedPref.edit().putString(key, val).apply();
    }

    // overload private updateInfo to handle int type values
    private void updateInfo(String key, int val) throws IllegalArgumentException{
        switch (key){
            case Constants.STATE:
                info.setState(val);
                break;
            case Constants.EXPIRATION_MONTH:
                info.setExpirationMonth(val);
                break;
            case Constants.EXPIRATION_YEAR:
                info.setExpirationYear(val);
                break;
            default:
                throw new IllegalArgumentException("invalid value for key: " + key);
        }
        sharedPref.edit().putInt(key, val).apply();
    }
}