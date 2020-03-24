package com.fullstorydev.shoppedemo.ui.checkout;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fullstorydev.shoppedemo.data.CustomerInfo;
import com.fullstorydev.shoppedemo.data.CustomerInfoRepository;
import com.fullstorydev.shoppedemo.utilities.Constants;


public class CheckoutViewModel extends AndroidViewModel {
    private CustomerInfoRepository mCustomerInfoRepo;
    private LiveData<Boolean> isLoading;
    private CustomerInfo customerInfo;


    public CheckoutViewModel(Application application) {
        super(application);

        mCustomerInfoRepo = new CustomerInfoRepository(application);
        isLoading = mCustomerInfoRepo.getIsLoading();
        customerInfo = new CustomerInfo.OrderBuilder().buildOrder(); // init to new customerInfo Obj with empty values
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public CustomerInfo getCustomerInfo(){ return customerInfo; }
    public String[] getStates() { return mCustomerInfoRepo.getStates(); }
    public Integer[] getYears() { return mCustomerInfoRepo.getYears(); }
    public Integer[] getMonths() { return mCustomerInfoRepo.getMonths(); }

    public void fetchCustomerInfo() { customerInfo = mCustomerInfoRepo.getCustomerInfo(); }

    public void setFirstName(CharSequence s, int arg1, int arg2, int arg3) { // handler for EditText onTextChanged
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getFirstName())){
            customerInfo.setFirstName(str);
            mCustomerInfoRepo.updateCustomerInfo(Constants.FIRST_NAME,str);
        }
    }
    public void setLastName(CharSequence s, int arg1, int arg2, int arg3) { // handler for EditText onTextChanged
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getLastName())){
            customerInfo.setLastName(str);
            mCustomerInfoRepo.updateCustomerInfo(Constants.LAST_NAME,str);
        }
    }
    public void setAddress1(CharSequence s, int arg1, int arg2, int arg3) { // handler for EditText onTextChanged
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getAddress1())){
            customerInfo.setAddress1(str);
            mCustomerInfoRepo.updateCustomerInfo(Constants.ADDRESS_1,str);
        }
    }
    public void setAddress2(CharSequence s, int arg1, int arg2, int arg3) { // handler for EditText onTextChanged
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getAddress2())){
            customerInfo.setAddress2(str);
            mCustomerInfoRepo.updateCustomerInfo(Constants.ADDRESS_2,str);
        }
    }
    public void setCity(CharSequence s, int arg1, int arg2, int arg3) { // handler for EditText onTextChanged
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getCity())){
            customerInfo.setCity(str);
            mCustomerInfoRepo.updateCustomerInfo(Constants.CITY,str);
        }
    }

//    public void setState(int state) { _info.setState(state); info.setValue(_info);}

    public void setZip(CharSequence s, int arg1, int arg2, int arg3) { // handler for EditText onTextChanged
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getZip())){
            customerInfo.setZip(str);
            mCustomerInfoRepo.updateCustomerInfo(Constants.ZIP,str);
        }
    }
    public void setCreditCardNumber(CharSequence s, int arg1, int arg2, int arg3) { // handler for EditText onTextChanged
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getCreditCardNumber())){
            customerInfo.setCreditCardNumber(str);
            mCustomerInfoRepo.updateCustomerInfo(Constants.CREDIT_CARD_NUMBER,str);
        }
    }

//    public void setExpirationMonth(int expirationMonth) { _info.setExpirationMonth(expirationMonth); info.setValue(_info);}

//    public void setExpirationYear(int expirationYear) { _info.setExpirationYear(expirationYear); info.setValue(_info);}

    public void setSecurityCode(CharSequence s, int arg1, int arg2, int arg3) { // handler for EditText onTextChanged
        String str = String.valueOf(s);
        if(!str.equals(customerInfo.getSecurityCode())){
            customerInfo.setSecurityCode(str);
            mCustomerInfoRepo.updateCustomerInfo(Constants.SECURITY_CODE,str);
        }
    }

}
