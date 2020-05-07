package com.fullstorydev.shoppedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fullstory.FS;
import com.fullstory.FSOnReadyListener;
import com.fullstory.FSSessionData;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;

import org.jetbrains.annotations.NotNull;

import static com.segment.analytics.internal.Utils.getSegmentSharedPreferences;

public class MainActivity extends AppCompatActivity implements FSOnReadyListener {
    AppBarConfiguration mAppBarConfiguration;
    NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        Analytics.with(getApplicationContext()).track("navigationEvent", new Properties().putName(item.toString()));

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
        Log.d("MainActivity", "FS is ready with URL: " + FS.getCurrentSessionURL());
//        Analytics.with(getApplicationContext()).track("FS_ready",new Properties().putUrl(FS.getCurrentSessionURL()));
        Analytics.with(getApplicationContext()).identify("test_user_id_3", new Traits().putName("Test User").putEmail("test_user_3@test.com"), null);

        Analytics.with(getApplicationContext()).group("fruitShoppe_group", new Traits().putIndustry("Retail"));

//        try {
//            Thread.sleep(2000);
//            Analytics.with(getApplicationContext()).reset();
//            Log.d("here-resetting","reset segment called");
//            SharedPreferences sharedPreferences = getSegmentSharedPreferences(getApplicationContext(), BuildConfig.SEGMENT_WRITE_KEY);
//            Log.d("here-resetting", String.valueOf(sharedPreferences.getAll()));
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        FS.anonymize();
//        Analytics.with(getApplicationContext()).identify("new_user_id");

    }
}
