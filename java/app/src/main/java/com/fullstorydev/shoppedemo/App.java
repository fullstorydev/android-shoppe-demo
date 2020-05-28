package com.fullstorydev.shoppedemo;

import androidx.multidex.MultiDexApplication;

import com.fullstorydev.shoppedemo.utilities.FullStoryMiddleware;
import com.segment.analytics.Analytics;

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        final String tag = BuildConfig.SEGMENT_WRITE_KEY;
        FullStoryMiddleware fsm = new FullStoryMiddleware(getApplicationContext(), tag);
        fsm.enableFSSessionURLInEvents = true;
        fsm.enableGroupTraitsAsUserVars = false;
        fsm.enableSendScreenAsEvents = true;
        fsm.whitelistAllTrackEvents = true;

        Analytics analytics = new Analytics.Builder(getApplicationContext(), BuildConfig.SEGMENT_WRITE_KEY)
                .tag(tag)
                // add middleware for FullStory
                .middleware(fsm)
                .build();
        Analytics.setSingletonInstance(analytics);

    }
}
