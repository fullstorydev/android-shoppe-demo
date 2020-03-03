package com.fullstorydev.shoppedemo.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.fullstorydev.shoppedemo.R;

public class MarketFragment extends Fragment {

    private MarketViewModel marketViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        marketViewModel =
                ViewModelProviders.of(this).get(MarketViewModel.class);
        View root = inflater.inflate(R.layout.fragment_market, container, false);
//        final TextView textView = root.findViewById(R.id.text_market);
//        marketViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}