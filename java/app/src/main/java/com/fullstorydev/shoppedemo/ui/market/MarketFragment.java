package com.fullstorydev.shoppedemo.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.data.Product;

import java.util.List;

public class MarketFragment extends Fragment {

    private MarketViewModel marketViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_market, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        marketViewModel = new ViewModelProvider(this).get(MarketViewModel.class);

        marketViewModel.getProductList().observe(this.getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                // products holds the list of products, live data is updated when retrieved from API, this data will be used to populate the recycler view
            }
        });
    }
}