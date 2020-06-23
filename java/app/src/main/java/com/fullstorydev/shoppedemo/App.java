package com.fullstorydev.shoppedemo;

import androidx.multidex.MultiDexApplication;

import com.fullstorydev.fullstory_segment_middleware.FullStorySegmentMiddleware;

import com.segment.analytics.Analytics;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        final String writeKey = BuildConfig.SEGMENT_WRITE_KEY;

//        allow list only certain events
//        ArrayList<String> allowlistedEvents = new ArrayList<>();
//        allowlistedEvents.add("Cart Viewed");
//        allowlistedEvents.add("Order Completed");

        FullStorySegmentMiddleware fsm = new FullStorySegmentMiddleware(getApplicationContext(), writeKey);
        // enable to insert FS session URL to Segment event properties and contexts, default to true
        fsm.enableFSSessionURLInEvents = true;
        // when calling Segment group, send group traits as userVars, default to false
        fsm.enableGroupTraitsAsUserVars = true;
        // when calling Segment screen, sent the screen event as custom events to FS, default to false
        fsm.enableSendScreenAsEvents = true;
        // allow all track events as FS custom events, alternatively allow list that you would like to track, default to false
        fsm.allowlistAllTrackEvents = true;

        // Create an analytics client with the given context, segmentTag and Segment write key.
        Analytics analytics = new Analytics.Builder(getApplicationContext(), writeKey)
                // If you explicitly set the tag like below, use the same tag for FS, else by default use write key
                // https://github.com/segmentio/analytics-android/blob/a9fefda981bd67e4b151f1e466e3113fdb866f00/analytics/src/main/java/com/segment/analytics/Analytics.java#L1146
                .tag(writeKey)
                .trackApplicationLifecycleEvents()

                // add middleware for FullStory
                .useSourceMiddleware(fsm)
                .build();

        // Set the initialized instance as a globally accessible instance.
        Analytics.setSingletonInstance(analytics);

    }
}
