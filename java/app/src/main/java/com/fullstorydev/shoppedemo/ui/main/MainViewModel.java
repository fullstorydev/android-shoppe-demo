package com.fullstorydev.shoppedemo.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fullstorydev.shoppedemo.data.ItemRepository;

public class MainViewModel extends AndroidViewModel {
    private ItemRepository mItemRepository;
    private LiveData<Integer> mItemCount;

    public MainViewModel(Application application) {
        super(application);
        mItemRepository = new ItemRepository(application);
        mItemCount = mItemRepository.getItemCount();
    }

    public LiveData<Integer> getItemCount() { return mItemCount; }
}