package com.fullstorydev.shoppedemo.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fullstory.FS;
import com.fullstory.FSOnReadyListener;
import com.fullstory.FSSessionData;
import com.fullstorydev.shoppedemo.R;
import com.jakewharton.rxbinding4.view.RxView;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements FSOnReadyListener {
    public static final int CLICK_COUNT_TO_CRASH = 4;
    public static final int ONE_SECOND = 1000;
    AppBarConfiguration mAppBarConfiguration;
    NavController mNavController;
    MainViewModel mMainViewModel;
    Disposable clickObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //setup custom title in toolbar, so that we can add easter egg for crashing app
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //crash app if title is clicked on 4 times
        // within 1 sec and you're on the main fragment
        clickObserver = RxView.clicks(title)
                .buffer(ONE_SECOND, TimeUnit.MILLISECONDS, CLICK_COUNT_TO_CRASH)
                .filter(list -> list.size() == CLICK_COUNT_TO_CRASH && mNavController.getCurrentDestination().getId() == R.id.navigation_market)
                .subscribe(events -> {
                    throw new RuntimeException("Clicked On View 4 Times, Will Crash Now, Good Job!");
                });

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mNavController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.navigation_market:
                    title.setText(getString(R.string.title_market));
                    break;
                case R.id.navigation_cart:
                    title.setText(getString(R.string.title_cart));
                    break;
                case R.id.navigation_checkout:
                    title.setText(getString(R.string.checkout));
                    break;
                default:
                    title.setText(getString(R.string.app_name));
                    break;
            }
        });
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
        // do stuff with session URL (for example send to desired integrations, etc)
    }

    @Override
    protected void onDestroy() {
        if(clickObserver != null) {
            clickObserver.dispose();
        }

        super.onDestroy();
    }
}
