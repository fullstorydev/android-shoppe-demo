package com.fullstorydev.shoppedemo.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.adapters.CartItemAdapter;
import com.fullstorydev.shoppedemo.data.Item;
import com.fullstorydev.shoppedemo.databinding.FragmentCartBinding;
import com.fullstorydev.shoppedemo.utilities.Constants;
import com.fullstorydev.shoppedemo.utilities.FSUtils;

public class CartFragment extends Fragment implements CartEventHandlers{
    private CartViewModel cartViewModel;
    private CartItemAdapter mCartItemAdapter;
    private FragmentCartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        View root = binding.getRoot();

        mCartItemAdapter = new CartItemAdapter(this);
        RecyclerView mRecyclerView = root.findViewById(R.id.rv_cart);
        mRecyclerView.setAdapter(mCartItemAdapter);

        root.findViewById(R.id.btn_checkout).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_navigation_cart_to_checkoutFragment);
        });
        
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getItemList().observe(this.getViewLifecycleOwner(), items -> mCartItemAdapter.setItemList(items));

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewmodel(cartViewModel);
    }

    public void onClickRemoveFromCart(Item item) {
        cartViewModel.decreaseQuantityInCart(item);
        FSUtils.productUpdated(FSUtils.EVENT_NAMES.PRODUCT_REMOVED, item);
    }
    public void onClickAddToCart(Item item){
        cartViewModel.increaseQuantityInCart(item);
        FSUtils.productUpdated(FSUtils.EVENT_NAMES.PRODUCT_ADDED, item);
    }
}