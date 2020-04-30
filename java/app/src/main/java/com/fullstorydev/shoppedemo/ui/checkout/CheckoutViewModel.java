package com.fullstorydev.shoppedemo.ui.checkout;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fullstorydev.shoppedemo.data.CustomerInfo;
import com.fullstorydev.shoppedemo.data.CustomerInfoRepository;
import com.fullstorydev.shoppedemo.data.ItemRepository;

public class CheckoutViewModel extends AndroidViewModel {
    private CustomerInfoRepository mCustomerInfoRepo;
    private ItemRepository mItemRepo;
    private LiveData<Boolean> isLoading;
    private CustomerInfo customerInfo;
    private LiveData<Double> mSubtotal;

    public CheckoutViewModel(Application application) {
        super(application);
        mCustomerInfoRepo = new CustomerInfoRepository(application);
        mItemRepo = new ItemRepository(application);
        isLoading = mCustomerInfoRepo.getIsLoading();
        mSubtotal = mItemRepo.getSubtotal();
        fetchCustomerInfo();
    }

    public CustomerInfo getCustomerInfo(){ return customerInfo; }
    public LiveData<Double> getSubtotal() { return mSubtotal; }

    LiveData<Boolean> getIsLoading() { return isLoading; }
    String[] getStates() { return mCustomerInfoRepo.getStates(); }
    Integer[] getYears() { return mCustomerInfoRepo.getYears(); }
    Integer[] getMonths() { return mCustomerInfoRepo.getMonths(); }
    void fetchCustomerInfo() { customerInfo = mCustomerInfoRepo.getCustomerInfo(); } //fetch the current customer info from repo


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
        try{
            mCustomerInfoRepo.updateCustomerInfo(customerInfo);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}