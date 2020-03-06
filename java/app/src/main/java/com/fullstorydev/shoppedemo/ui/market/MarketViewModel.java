package com.fullstorydev.shoppedemo.ui.market;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MarketViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MarketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is market fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}