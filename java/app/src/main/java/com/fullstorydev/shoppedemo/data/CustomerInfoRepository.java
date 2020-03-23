package com.fullstorydev.shoppedemo.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fullstorydev.shoppedemo.utilities.Constants;


public class CustomerInfoRepository {
    private SharedPreferences sharedPref;
    private CustomerInfo customerInfo;

    private Application application;
    private MutableLiveData<Boolean> isLoading; // lifecycle aware observable, observe it to get update when loading is done

    public CustomerInfoRepository(Application application) {
        this.application = application;
        isLoading = new MutableLiveData<>();
        customerInfo = new CustomerInfo.OrderBuilder().buildOrder();
        initCustomerInfoFromSharedPref();
    }

    public CustomerInfo getCustomerInfo() { return customerInfo; } // info may return null if still loading
    public LiveData<Boolean> getIsLoading(){ return isLoading; }

    // get constants to be used to populate drop downs in UI
    public String[] getStates() { return Constants.getStates(); }
    public Integer[] getYears() { return Constants.getYears(); }
    public Integer[] getMonths() { return Constants.getMonths(); }

    public void updateCustomerInfo(CustomerInfo newCustomerInfo) throws IllegalArgumentException {
        isLoading.setValue(true);
        updateCustomerInfo(Constants.FIRST_NAME,newCustomerInfo.getFirstName());
        updateCustomerInfo(Constants.LAST_NAME,newCustomerInfo.getLastName());
        updateCustomerInfo(Constants.ADDRESS_1,newCustomerInfo.getAddress1());
        updateCustomerInfo(Constants.ADDRESS_2,newCustomerInfo.getAddress2());
        updateCustomerInfo(Constants.CITY,newCustomerInfo.getCity());
        updateCustomerInfo(Constants.STATE,newCustomerInfo.getState());
        updateCustomerInfo(Constants.ZIP,newCustomerInfo.getZip());
        updateCustomerInfo(Constants.CREDIT_CARD_NUMBER,newCustomerInfo.getCreditCardNumber());
        updateCustomerInfo(Constants.EXPIRATION_MONTH,newCustomerInfo.getExpirationMonth());
        updateCustomerInfo(Constants.EXPIRATION_YEAR,newCustomerInfo.getExpirationYear());
        updateCustomerInfo(Constants.SECURITY_CODE,newCustomerInfo.getSecurityCode());
        isLoading.setValue(false);
    }

    // initialize info values from shared pref and update loading state
    private void initCustomerInfoFromSharedPref() {
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

            customerInfo = new CustomerInfo.OrderBuilder()
                    .withName(firstName,lastName)
                    .withAddress(address1,address2,city,state,zip)
                    .withPayment(creditCardNumber,expirationMonth,expirationYear,securityCode)
                    .buildOrder();
            isLoading.postValue(false);
        }).start();
    }

    // update Shared Pref String values
    public void updateCustomerInfo(String key, String val) throws IllegalArgumentException{
        switch (key){
            case Constants.FIRST_NAME:
                customerInfo.setFirstName(val);
                break;
            case Constants.LAST_NAME:
                customerInfo.setLastName(val);
                break;
            case Constants.ADDRESS_1:
                customerInfo.setAddress1(val);
                break;
            case Constants.ADDRESS_2:
                customerInfo.setAddress2(val);
                break;
            case Constants.CITY:
                customerInfo.setCity(val);
                break;
            case Constants.ZIP:
                customerInfo.setZip(val);
                break;
            case Constants.CREDIT_CARD_NUMBER:
                customerInfo.setCreditCardNumber(val);
                break;
            case Constants.SECURITY_CODE:
                customerInfo.setSecurityCode(val);
                break;
            default:
                throw new IllegalArgumentException("invalid value for key: " + key);
        }
        sharedPref.edit().putString(key, val).apply();
    }

    // overload private updateInfo to handle int type values
    public void updateCustomerInfo(String key, int val) throws IllegalArgumentException{
        switch (key){
            case Constants.STATE:
                customerInfo.setState(val);
                break;
            case Constants.EXPIRATION_MONTH:
                customerInfo.setExpirationMonth(val);
                break;
            case Constants.EXPIRATION_YEAR:
                customerInfo.setExpirationYear(val);
                break;
            default:
                throw new IllegalArgumentException("invalid value for key: " + key);
        }
        sharedPref.edit().putInt(key, val).apply();
    }
}