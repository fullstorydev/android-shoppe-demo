package com.fullstorydev.shoppedemo.ui.checkout;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fullstorydev.shoppedemo.data.CustomerInfo;
import com.fullstorydev.shoppedemo.data.CustomerInfoRepository;

public class CheckoutViewModel extends AndroidViewModel {
    private CustomerInfoRepository mCustomerInfoRepo;
    private LiveData<Boolean> isLoading;
    private CustomerInfo customerInfo;

    public CheckoutViewModel(Application application) {
        super(application);
        mCustomerInfoRepo = new CustomerInfoRepository(application);
        isLoading = mCustomerInfoRepo.getIsLoading();
        fetchCustomerInfo();
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public CustomerInfo getCustomerInfo(){ return customerInfo; }
    public String[] getStates() { return mCustomerInfoRepo.getStates(); }
    public Integer[] getYears() { return mCustomerInfoRepo.getYears(); }
    public Integer[] getMonths() { return mCustomerInfoRepo.getMonths(); }

    public void fetchCustomerInfo() { customerInfo = mCustomerInfoRepo.getCustomerInfo(); } //fetch the current customer info from repo

    // handler for EditText onTextChanged or Spinner
    public void setFirstName(CharSequence s) {
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getFirstName())){
            customerInfo.setFirstName(str);
        }
    }
    public void setLastName(CharSequence s) {
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getLastName())){
            customerInfo.setLastName(str);
        }
    }
    public void setAddress1(CharSequence s) {
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getAddress1())){
            customerInfo.setAddress1(str);
        }
    }
    public void setAddress2(CharSequence s) {
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getAddress2())){
            customerInfo.setAddress2(str);
        }
    }
    public void setCity(CharSequence s) {
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getCity())){
            customerInfo.setCity(str);
        }
    }
    public void setState(int position) {
        if(position != customerInfo.getState()){
            customerInfo.setState(position);
        }
    }
    public void setZip(CharSequence s) {
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getZip())){
            customerInfo.setZip(str);
        }
    }
    public void setCreditCardNumber(CharSequence s) {
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getCreditCardNumber())){
            customerInfo.setCreditCardNumber(str);
        }
    }
    public void setExpirationMonth(int position) {
        if(position != customerInfo.getExpirationMonth()){
            customerInfo.setExpirationMonth(position);
        }
    }
    public void setExpirationYear(int position) {
        if(position != customerInfo.getExpirationYear()){
            customerInfo.setExpirationYear(position);
        }
    }
    public void setSecurityCode(CharSequence s) { // handler for EditText onTextChanged
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getSecurityCode())){
            customerInfo.setSecurityCode(str);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCustomerInfoRepo.updateCustomerInfo(customerInfo);
    }
}
