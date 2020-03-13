package com.fullstorydev.shoppedemo.ui.cart;

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
import com.fullstorydev.shoppedemo.adapters.CartItemAdapter;
import com.fullstorydev.shoppedemo.data.Product;

import java.util.List;

public class CartFragment extends Fragment implements CartEventHandlers{
    private CartViewModel cartViewModel;
    private CartItemAdapter mCartItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        mCartItemAdapter = new CartItemAdapter(this);
        RecyclerView mRecyclerView = root.findViewById(R.id.rv_cart);
        mRecyclerView.setAdapter(mCartItemAdapter);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getItemList().observe(this.getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> items) {
                mCartItemAdapter.setItemList(items);
            }
        });
    }

    @Override
    public void onClickRemoveFromCart(Product item) {
        cartViewModel.decreaseQuantityInCart(item);
    }
}