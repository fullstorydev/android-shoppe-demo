package com.fullstorydev.shoppedemo.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.databinding.FragmentCheckoutBinding;
import com.fullstorydev.shoppedemo.databinding.ListItemMarketBinding;
import com.fullstorydev.shoppedemo.ui.cart.CartViewModel;

public class CheckoutFragment extends Fragment {
    FragmentCheckoutBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CheckoutViewModel vm = new ViewModelProvider(this).get(CheckoutViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        vm.getIsLoading().observe(getViewLifecycleOwner(),isLoading->{
            if(!isLoading){
                vm.fetchCustomerInfo();
                binding.setViewmodel(vm);
                binding.executePendingBindings();
            }
        });

    }
}
