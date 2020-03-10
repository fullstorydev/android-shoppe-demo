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
import androidx.recyclerview.widget.RecyclerView;

import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.adapters.MarketProductAdapter;
import com.fullstorydev.shoppedemo.data.Product;

import java.util.List;

public class MarketFragment extends Fragment implements MarketEventHandlers {

    private MarketViewModel marketViewModel;
    private MarketProductAdapter mMarketProductAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_market, container, false);

        mMarketProductAdapter = new MarketProductAdapter(this);
        RecyclerView mRecyclerView = root.findViewById(R.id.rv_product);
        mRecyclerView.setAdapter(mMarketProductAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        marketViewModel = new ViewModelProvider(this).get(MarketViewModel.class);
        marketViewModel.getProductList().observe(this.getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mMarketProductAdapter.setProductList(products);
            }
        });
    }

    public void onClickAddToCart(Product product){
        marketViewModel.increaseQuantityInCart(product);
    }
}