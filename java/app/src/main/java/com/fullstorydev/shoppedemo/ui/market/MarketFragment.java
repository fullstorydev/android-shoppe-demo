package com.fullstorydev.shoppedemo.ui.market;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fullstory.FS;
import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.adapters.MarketProductAdapter;
import com.fullstorydev.shoppedemo.data.Item;
import com.fullstorydev.shoppedemo.utilities.Constants;
import com.fullstorydev.shoppedemo.utilities.FSUtils;

public class MarketFragment extends Fragment implements MarketEventHandlers {

    private MarketViewModel marketViewModel;
    private MarketProductAdapter mMarketProductAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_market, container, false);

        mMarketProductAdapter = new MarketProductAdapter(this);
        mRecyclerView = root.findViewById(R.id.rv_product);
        mRecyclerView.setAdapter(mMarketProductAdapter);
        setRecyclerViewLayoutManager(getResources().getConfiguration().orientation);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("MarketFragment", "market view is created");
        Log.i("MarketFragment", "FS is ready?" + (FS.getCurrentSessionURL() != null));
        FS.event("market-view-created", null);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRecyclerViewLayoutManager(newConfig.orientation);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        marketViewModel = new ViewModelProvider(this).get(MarketViewModel.class);
        marketViewModel.getProductList().observe(this.getViewLifecycleOwner(), products -> mMarketProductAdapter.setProductList(products));

        marketViewModel.getStatus().observe(this.getViewLifecycleOwner(), status ->{
            View v = getView();
            if(v != null){
                if (status == Constants.Status.LOADING) {
                    v.findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
                } else {
                    v.findViewById(R.id.progressbar).setVisibility(View.GONE);
                }
                //TODO: add network listener to detect network change and auto reload
                if (status == Constants.Status.ERROR) {
                    v.findViewById(R.id.tv_error).setVisibility(View.VISIBLE);
                } else {
                    v.findViewById(R.id.tv_error).setVisibility(View.GONE);
                }
            }
        });
    }

    public void onClickAddToCart(Item item){
        FSUtils.productUpdated(FSUtils.EVENT_NAMES.PRODUCT_ADDED, item);
        marketViewModel.increaseQuantityInCart(item);
    }

    private void setRecyclerViewLayoutManager(int orientation){
        // if landscape then have 2 columns, otherwise 1: for simplicity we are not calculating this based on screen size
        int spanCnt = orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1;
        GridLayoutManager manager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        if(manager == null) manager = new GridLayoutManager(getContext(),spanCnt);
        manager.setSpanCount(spanCnt);
        mRecyclerView.setLayoutManager(manager);
    }
}