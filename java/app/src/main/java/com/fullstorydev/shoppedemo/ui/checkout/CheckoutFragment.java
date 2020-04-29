package com.fullstorydev.shoppedemo.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fullstory.FS;
import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.data.CustomerInfo;
import com.fullstorydev.shoppedemo.databinding.FragmentCheckoutBinding;
import com.fullstorydev.shoppedemo.fssegment.AnalyticsWithFS;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;

public class CheckoutFragment extends Fragment implements CheckoutEventHandlers {
    private FragmentCheckoutBinding binding;
    private ArrayAdapter<String> stateAdapter;
    private ArrayAdapter<Integer> yearAdapter;
    private ArrayAdapter<Integer> monthAdapter;
    private CheckoutViewModel checkoutViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);
        View root = binding.getRoot();

        stateAdapter = new ArrayAdapter<>(root.getContext(),R.layout.support_simple_spinner_dropdown_item);
        monthAdapter = new ArrayAdapter<>(root.getContext(),R.layout.support_simple_spinner_dropdown_item);
        yearAdapter = new ArrayAdapter<>(root.getContext(),R.layout.support_simple_spinner_dropdown_item);

        AutoCompleteTextView stateEditTextFilledExposedDropdown = root.findViewById(R.id.dropdown_checkout_state);
        AutoCompleteTextView monthEditTextFilledExposedDropdown = root.findViewById(R.id.dropdown_checkout_expiration_month);
        AutoCompleteTextView yearEditTextFilledExposedDropdown = root.findViewById(R.id.dropdown_checkout_expiration_year);

        stateEditTextFilledExposedDropdown.setAdapter(stateAdapter);
        monthEditTextFilledExposedDropdown.setAdapter(monthAdapter);
        yearEditTextFilledExposedDropdown.setAdapter(yearAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FS.addClass(view.findViewById(R.id.checkout_credit_cart_number),FS.EXCLUDE_CLASS);
        FS.addClass(view.findViewById(R.id.checkout_expiration_month),FS.EXCLUDE_CLASS);
        FS.addClass(view.findViewById(R.id.checkout_expiration_year),FS.EXCLUDE_CLASS);
        FS.addClass(view.findViewById(R.id.checkout_security_code),FS.EXCLUDE_CLASS);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        stateAdapter.addAll(checkoutViewModel.getStates());
        monthAdapter.addAll(checkoutViewModel.getMonths());
        yearAdapter.addAll(checkoutViewModel.getYears());

        checkoutViewModel.getIsLoading().observe(getViewLifecycleOwner(),isLoading->{
            if(!isLoading){
                checkoutViewModel.fetchCustomerInfo();
                binding.setViewmodel(checkoutViewModel);
                binding.setHandlers(this);
            }
        });
    }
    
    public void onClickPurchase(CustomerInfo customerInfo, Double subtotal) {
        //Analytics.with(getContext()).track("test AnalyticsWithFS event");
        // AnalyticsWithFS.with(getContext()).track("AnalyticsWithFSEvent",new Properties().putValue("name", "Moto 360"));

        try{
            boolean valid = customerInfo.validateOrder();
            if(valid && subtotal != null && subtotal > 0) {
                // placeholder for your logic here to complete purchase
                Toast.makeText(getContext(), "Purchase success!", Toast.LENGTH_LONG).show();
            } else {
                throw new IllegalArgumentException("Order not valid");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            // placeholder for your logic here to handle failed purchase
            Toast.makeText(getContext(),"Purchase failed!",Toast.LENGTH_LONG).show();
        }
        throw new RuntimeException("Test Fatal error"); // Force a crash
    }
}
