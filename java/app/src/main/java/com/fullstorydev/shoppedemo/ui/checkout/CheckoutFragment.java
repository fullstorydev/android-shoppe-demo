package com.fullstorydev.shoppedemo.ui.checkout;

import android.app.Activity;
import android.app.RemoteInput;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class CheckoutFragment extends Fragment implements CheckoutEventHandlers{
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

        Spinner stateSpinner = root.findViewById(R.id.spinner_checkout_states);
        Spinner yearSpinner = root.findViewById(R.id.spinner_checkout_year);
        Spinner monthSpinner = root.findViewById(R.id.spinner_checkout_month);
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
                Toast.makeText(getContext(), "Purchase success!", Toast.LENGTH_LONG).show();
            }else{
                throw new IllegalArgumentException("Order not valid");
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Purchase failed!",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public View.OnTouchListener getOnTouchListener() {
        return (v, event) -> {
            if(!(v instanceof EditText)) {
                hideKeyboard(getActivity());
            }
            v.performClick();
            return false;
        };
    }
    private static void hideKeyboard(Activity activity) {
        if(activity!=null){
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
