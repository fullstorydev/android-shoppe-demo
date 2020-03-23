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
        info = mCustomerInfoRepo.getInfo();
        _info = new CustomerInfo.OrderBuilder().buildOrder();
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public CustomerInfo getCustomerInfo() {
        _info = mCustomerInfoRepo.getInfo();
        Log.d("gere",String.valueOf(_info.getFirstName()));
        Log.d("gere",String.valueOf(_info.getLastName()));
        return _info;
    }

    public String[] getStates() { return mCustomerInfoRepo.getStates(); }
    public Integer[] getYears() { return mCustomerInfoRepo.getYears(); }
    public Integer[] getMonths() { return mCustomerInfoRepo.getMonths(); }

    public void updateInfo() throws IllegalArgumentException {
        Log.d("gere1",String.valueOf(_info.getFirstName()));
        Log.d("gere1",String.valueOf(_info.getLastName()));
        mCustomerInfoRepo.updateInfo(_info);
    }

//    public String getFirstName(){
//        Log.d("gere2",String.valueOf(_info.getFirstName()));
//        return _info.getFirstName(); }
//    public void setFirstName(String firstName) { _info.setFirstName(firstName);
//
//         }
//
//    public LiveData<String> getLastName() {
//        Log.d("gere3",String.valueOf(_info.getLastName()));
//        return _info.getLastName(); }
//    public void setLastName(String lastName) { _info.setLastName(lastName);
//        }
//
//    public String getAddress1() { return address1; }
//    public void setAddress1(String address1) { this.address1 = address1; }
//
//    public String getAddress2() { return address2; }
//    public void setAddress2(String address2) { this.address2 = address2; }
//
//    public String getCity() { return city; }
//    public void setCity(String city) { this.city = city; }
//
//    public int getState() { return state; }
//    public void setState(int state) { this.state = state; }
//
//    public String getZip() { return zip; }
//    public void setZip(String zip) { this.zip = zip; }
//
//    public String getCreditCardNumber() { return creditCardNumber; }
//    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }
//
//    public int getExpirationMonth() { return expirationMonth; }
//    public void setExpirationMonth(int expirationMonth) { this.expirationMonth = expirationMonth; }
//
//    public int getExpirationYear() { return expirationYear; }
//    public void setExpirationYear(int expirationYear) { this.expirationYear = expirationYear; }
//
//    public String getSecurityCode() { return securityCode; }
//    public void setSecurityCode(String securityCode) { this.securityCode = securityCode; }

}
