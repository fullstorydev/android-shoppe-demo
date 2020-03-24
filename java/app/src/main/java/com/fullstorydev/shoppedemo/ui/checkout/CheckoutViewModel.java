package com.fullstorydev.shoppedemo.ui.checkout;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fullstorydev.shoppedemo.data.CustomerInfo;
import com.fullstorydev.shoppedemo.data.CustomerInfoRepository;
import com.fullstorydev.shoppedemo.utilities.Constants;


public class CheckoutViewModel extends AndroidViewModel {
    private CustomerInfoRepository mCustomerInfoRepo;
    private LiveData<Boolean> isLoading;
    private CustomerInfo info;


    public CheckoutViewModel(Application application) {
        super(application);

        mCustomerInfoRepo = new CustomerInfoRepository(application);
        isLoading = mCustomerInfoRepo.getIsLoading();
        info = mCustomerInfoRepo.getCustomerInfo(); //nullable
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public void fetchCustomerInfo() { info = mCustomerInfoRepo.getCustomerInfo(); }

    public CustomerInfo getInfo(){ return info; }

    public String[] getStates() { return mCustomerInfoRepo.getStates(); }
    public Integer[] getYears() { return mCustomerInfoRepo.getYears(); }
    public Integer[] getMonths() { return mCustomerInfoRepo.getMonths(); }

//    public String getFirstName(){ return _info.getFirstName(); }
    public void setFirstName(CharSequence s, int arg1, int arg2, int arg3) {
        Log.d("here","set first name");
        String firstName = String.valueOf(s);
        if(!firstName.equals(info.getFirstName())){
            info.setFirstName(firstName);
            mCustomerInfoRepo.updateCustomerInfo(Constants.FIRST_NAME,firstName);
        }
    }

//
//    public String getLastName() { return _info.getLastName(); }
//    public void setLastName(String lastName) { _info.setLastName(lastName);}
//
//    public String getAddress1() { return _info.getAddress1(); }
//    public void setAddress1(String address1) { _info.setAddress1(address1);}
//
//    public String getAddress2() { return _info.getAddress2(); }
//    public void setAddress2(String address2) { _info.setAddress2(address2); info.setValue(_info);}
//
//    public String getCity() { return _info.getCity(); }
//    public void setCity(String city) { _info.setCity(city); info.setValue(_info);}
//
//    public int getState() { return _info.getState(); }
//    public void setState(int state) { _info.setState(state); info.setValue(_info);}
//
//    public String getZip() { return _info.getZip(); }
//    public void setZip(String zip) { _info.setZip(zip); info.setValue(_info);}
//
//    public String getCreditCardNumber() { return _info.getCreditCardNumber(); }
//    public void setCreditCardNumber(String creditCardNumber) { _info.setCreditCardNumber(creditCardNumber); info.setValue(_info);}
//
//    public int getExpirationMonth() { return _info.getExpirationMonth(); }
//    public void setExpirationMonth(int expirationMonth) { _info.setExpirationMonth(expirationMonth); info.setValue(_info);}
//
//    public int getExpirationYear() { return _info.getExpirationYear(); }
//    public void setExpirationYear(int expirationYear) { _info.setExpirationYear(expirationYear); info.setValue(_info);}
//
//    public String getSecurityCode() { return _info.getSecurityCode(); }
//    public void setSecurityCode(String securityCode) { _info.setSecurityCode(securityCode); info.setValue(_info);}

}
