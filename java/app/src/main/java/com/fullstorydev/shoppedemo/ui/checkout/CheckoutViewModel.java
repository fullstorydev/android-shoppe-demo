package com.fullstorydev.shoppedemo.ui.checkout;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fullstorydev.shoppedemo.data.CustomerInfo;
import com.fullstorydev.shoppedemo.data.CustomerInfoRepository;


public class CheckoutViewModel extends AndroidViewModel {
    private CustomerInfoRepository mCustomerInfoRepo;
    private LiveData<Boolean> isLoading;
    private LiveData<CustomerInfo> info; // holds data from repo
    private CustomerInfo _info; // holds transient data


    public CheckoutViewModel(Application application) {
        super(application);

        mCustomerInfoRepo = new CustomerInfoRepository(application);
        isLoading = mCustomerInfoRepo.getIsLoading();
//        info = mCustomerInfoRepo.getInfo();
        _info = new CustomerInfo.OrderBuilder().buildOrder();
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public CustomerInfo getCustomerInfo() {
        _info = mCustomerInfoRepo.getInfo();
        return _info;
    }

    public String[] getStates() { return mCustomerInfoRepo.getStates(); }
    public Integer[] getYears() { return mCustomerInfoRepo.getYears(); }
    public Integer[] getMonths() { return mCustomerInfoRepo.getMonths(); }

    public void updateInfo() throws IllegalArgumentException { mCustomerInfoRepo.updateInfo(_info); }

    public String getFirstName(){ return _info.getFirstName(); }
    public void setFirstName(String firstName) { _info.setFirstName(firstName); }

    public String getLastName() { return _info.getLastName(); }
    public void setLastName(String lastName) { _info.setLastName(lastName); }

    public String getAddress1() { return _info.getAddress1(); }
    public void setAddress1(String address1) { _info.setAddress1(address1); }

    public String getAddress2() { return _info.getAddress2(); }
    public void setAddress2(String address2) { _info.setAddress2(address2); }

    public String getCity() { return _info.getCity(); }
    public void setCity(String city) { _info.setCity(city); }

    public int getState() { return _info.getState(); }
    public void setState(int state) { _info.setState(state); }

    public String getZip() { return _info.getZip(); }
    public void setZip(String zip) { _info.setZip(zip); }

    public String getCreditCardNumber() { return _info.getCreditCardNumber(); }
    public void setCreditCardNumber(String creditCardNumber) { _info.setCreditCardNumber(creditCardNumber); }

    public int getExpirationMonth() { return _info.getExpirationMonth(); }
    public void setExpirationMonth(int expirationMonth) { _info.setExpirationMonth(expirationMonth); }

    public int getExpirationYear() { return _info.getExpirationYear(); }
    public void setExpirationYear(int expirationYear) { _info.setExpirationYear(expirationYear); }

    public String getSecurityCode() { return _info.getSecurityCode(); }
    public void setSecurityCode(String securityCode) { _info.setSecurityCode(securityCode); }

}
