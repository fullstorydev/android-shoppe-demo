package com.fullstorydev.shoppedemo.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.data.CustomerInfo;
import com.fullstorydev.shoppedemo.databinding.FragmentCheckoutBinding;

public class CheckoutFragment extends Fragment implements CheckoutEventHandlers {
    FragmentCheckoutBinding binding;
    private Spinner stateSpinner;
    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private ArrayAdapter<String> stateAdapter;
    private ArrayAdapter<Integer> yearAdapter;
    private ArrayAdapter<Integer> monthAdapter;
    CheckoutViewModel checkoutViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);
        View root = binding.getRoot();

        stateSpinner = root.findViewById(R.id.spinner_checkout_states);
        yearSpinner = root.findViewById(R.id.spinner_checkout_year);
        monthSpinner = root.findViewById(R.id.spinner_checkout_month);
        stateAdapter = new ArrayAdapter<>(root.getContext(),R.layout.support_simple_spinner_dropdown_item);
        yearAdapter = new ArrayAdapter<>(root.getContext(),R.layout.support_simple_spinner_dropdown_item);
        monthAdapter = new ArrayAdapter<>(root.getContext(),R.layout.support_simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);
        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        stateAdapter.addAll(checkoutViewModel.getStates());
        yearAdapter.addAll(checkoutViewModel.getYears());
        monthAdapter.addAll(checkoutViewModel.getMonths());

        checkoutViewModel.getIsLoading().observe(getViewLifecycleOwner(),isLoading->{
            if(!isLoading){
                checkoutViewModel.fetchCustomerInfo();
                binding.setViewmodel(checkoutViewModel);
                binding.setHandlers(this);
            }
        });
    }

    @Override
    public void onClickPurchase(CustomerInfo customerInfo, Double subtotal) {
        try{
            boolean valid = customerInfo.validateOrder();
            if(valid && subtotal!= null && subtotal>0) {
                // placeholder for your logic here to complete purchase
                Toast.makeText(getContext(), "Purchase success!", Toast.LENGTH_LONG).show();
            }else{
                throw new IllegalArgumentException("Order not valid");
            }
        }catch (Exception e){
            e.printStackTrace();
            // placeholder for your logic here to handle failed purchase
            Toast.makeText(getContext(),"Purchase failed!",Toast.LENGTH_LONG).show();
        }
    }
}
