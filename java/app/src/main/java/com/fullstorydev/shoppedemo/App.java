package com.fullstorydev.shoppedemo;

import androidx.multidex.MultiDexApplication;

import com.segment.analytics.Analytics;

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        // Segment integration
        // Create an analytics client with the given context and Segment write key.
        Analytics analytics = new Analytics.Builder(getApplicationContext(), BuildConfig.SEGMENT_WRITE_KEY)
                // Enable this to record certain application events automatically!
                .trackApplicationLifecycleEvents()
                // Enable this to record screen views automatically!
                .recordScreenViews()
                .build();

// Set the initialized instance as a globally accessible instance.
        Analytics.setSingletonInstance(analytics);
    }
}
