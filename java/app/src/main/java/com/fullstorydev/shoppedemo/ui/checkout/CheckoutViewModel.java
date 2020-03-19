package com.fullstorydev.shoppedemo.ui.checkout;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fullstorydev.shoppedemo.data.CustomerInfoMap;
import com.fullstorydev.shoppedemo.data.CustomerInfoRepository;
import com.fullstorydev.shoppedemo.utilities.Constants;

import java.io.IOException;

public class CheckoutViewModel extends AndroidViewModel {
    private CustomerInfoRepository mCustomerInfoRepo;
    private LiveData<CustomerInfo> customerInfo;
    private LiveData<Boolean> isLoading;

    public CheckoutViewModel(Application application) {
        super(application);

        mCustomerInfoRepo = new CustomerInfoRepository(application);
        customerInfo = mCustomerInfoRepo.getInfo();
        isLoading = mCustomerInfoRepo.getIsLoading();
    }

    public LiveData<CustomerInfo> getCustomerInfo() {return customerInfo;}
    public LiveData<Boolean> getIsLoading() {return isLoading;}

    public void updateInfo(String key, String val) throws IOException { mCustomerInfoRepo.updateInfo(key, val); }
    public void updateInfo(String key, int val) throws IOException { mCustomerInfoRepo.updateInfo(key, val); }

    public String[] getStates() { return Constants.getStates(); }
    public Integer[] getYears() { return Constants.getYears(); }
    public Integer[] getMonths() { return Constants.getMonths(); }
}
