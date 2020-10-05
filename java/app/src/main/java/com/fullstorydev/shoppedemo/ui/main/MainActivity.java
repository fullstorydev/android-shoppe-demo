package com.fullstorydev.shoppedemo.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fullstory.FS;
import com.fullstory.FSOnReadyListener;
import com.fullstory.FSSessionData;
import com.fullstorydev.shoppedemo.R;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements FSOnReadyListener {
    AppBarConfiguration mAppBarConfiguration;
    NavController mNavController;
    MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                mNavController.getGraph())
                .build();
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        FS.setReadyListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);

        View view = menu.findItem(R.id.navigation_cart).getActionView();

        // can't set data binding in menu resources
        TextView cntView = view.findViewById(R.id.tv_nav_item_cart_cnt);
        if(cntView != null) {
            mMainViewModel.getItemCount().observe(this, cnt -> cntView.setText(String.valueOf(cnt)));
        }

        view.setOnClickListener(v -> {
            onOptionsItemSelected(menu.findItem(R.id.navigation_cart));
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, mNavController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // hide the keyboard when user click on anywhere else
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if(view != null && view.requestFocus()){
            hideKeyboard(view);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onReady(FSSessionData sessionData) {
        String fsUrl = sessionData.getCurrentSessionURL();
        Log.d("MainActivity", "FS URL is " + fsUrl);
    }

}