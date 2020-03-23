package com.fullstorydev.shoppedemo.ui.checkout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fullstorydev.shoppedemo.R;
import com.fullstorydev.shoppedemo.data.CustomerInfo;
import com.fullstorydev.shoppedemo.databinding.FragmentCheckoutBinding;
import com.fullstorydev.shoppedemo.utilities.Constants;

public class CheckoutFragment extends Fragment {
    private CheckoutViewModel checkoutViewModel;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText address1EditText;
    private EditText address2EditText;
    private EditText cityEditText;
    private EditText zipEditText;
    private EditText creditCardNumberEditText;
    private EditText securityCodeEditText;
    private Spinner stateSpinner;
    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private ArrayAdapter<String> stateAdapter;
    private ArrayAdapter<Integer> yearAdapter;
    private ArrayAdapter<Integer> monthAdapter;

    FragmentCheckoutBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_checkout, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_checkout,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stateSpinner = view.findViewById(R.id.spinner_checkout_states);
        yearSpinner = view.findViewById(R.id.spinner_checkout_year);
        monthSpinner = view.findViewById(R.id.spinner_checkout_month);
        firstNameEditText = view.findViewById(R.id.et_checkout_first_name);
        lastNameEditText = view.findViewById(R.id.et_checkout_last_name);
        address1EditText = view.findViewById(R.id.et_checkout_address1);
        address2EditText = view.findViewById(R.id.et_checkout_address2);
        cityEditText = view.findViewById(R.id.et_checkout_city);
        zipEditText = view.findViewById(R.id.et_checkout_zip);
        creditCardNumberEditText = view.findViewById(R.id.et_checkout_credit_card_number);
        securityCodeEditText = view.findViewById(R.id.et_checkout_security_code);

        stateAdapter = new ArrayAdapter<>(view.getContext(),R.layout.support_simple_spinner_dropdown_item);
        yearAdapter = new ArrayAdapter<>(view.getContext(),R.layout.support_simple_spinner_dropdown_item);
        monthAdapter = new ArrayAdapter<>(view.getContext(),R.layout.support_simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);
        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);

        view.setOnClickListener(this::onClickListener);
        stateSpinner.setOnTouchListener(this::onTouchListener);
        yearSpinner.setOnTouchListener(this::onTouchListener);
        monthSpinner.setOnTouchListener(this::onTouchListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);

        stateAdapter.addAll(checkoutViewModel.getStates());
        yearAdapter.addAll(checkoutViewModel.getYears());
        monthAdapter.addAll(checkoutViewModel.getMonths());

        binding.setViewmodel(checkoutViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        ProgressDialog dialog = new ProgressDialog(getContext());
        checkoutViewModel.getIsLoading().observe(this.getViewLifecycleOwner(), isLoading -> {
            dialog.setMessage("Loading....");
            if(isLoading){ dialog.show(); }
            else{
                checkoutViewModel.getCustomerInfo();
                dialog.dismiss();
            }
        });

    }


    private boolean onTouchListener (View v, MotionEvent event) {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) getActivity().getSystemService(
//                        Activity.INPUT_METHOD_SERVICE);
//        assert inputMethodManager != null;
//        inputMethodManager.hideSoftInputFromWindow(
//                v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return false;
    }

    private void onClickListener (View v) {
        //TODO: 1 handle null pointer
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) getActivity().getSystemService(
//                        Activity.INPUT_METHOD_SERVICE);
//        assert inputMethodManager != null;
//        inputMethodManager.hideSoftInputFromWindow(
//                getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        checkoutViewModel.updateInfo();
    }
}
