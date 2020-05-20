package com.fullstorydev.shoppedemo;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.fullstory.FS;
import com.fullstorydev.shoppedemo.fssegment.FullStoryIntegration;
import com.fullstorydev.shoppedemo.fssegment.FullStoryMiddleware;
import com.segment.analytics.Analytics;

import java.util.ArrayList;

import static com.segment.analytics.internal.Utils.getSegmentSharedPreferences;


public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        final String writeKey = BuildConfig.SEGMENT_WRITE_KEY;

        ArrayList<String> whitelistedEvents = new ArrayList<>();
        whitelistedEvents.add("Cart Viewed");
        whitelistedEvents.add("Order Completed");

        FullStoryMiddleware fsm = new FullStoryMiddleware(getApplicationContext(), writeKey, whitelistedEvents);
        fsm.enableFSSessionURLInEvents = true;
        fsm.enableGroupTraitsAsUserVars = true;
        fsm.enableSendScreenAsEvents = true;

        // Segment integration
        // Create an analytics client with the given context, segmentTag and Segment write key.
        Analytics analytics = new Analytics.Builder(getApplicationContext(), writeKey)
                // use a tag for caching, if not set by default write key is used. We set the key to
                // https://github.com/segmentio/analytics-android/blob/a9fefda981bd67e4b151f1e466e3113fdb866f00/analytics/src/main/java/com/segment/analytics/Analytics.java#L1146
                .tag(writeKey)
//                .logLevel(Analytics.LogLevel.VERBOSE)
//                .use(FirebaseIntegration.FACTORY)
//                .use(FullStoryIntegration.FACTORY)
//                .trackApplicationLifecycleEvents()
//                .recordScreenViews()

                // add middleware for FullStory
                .middleware(fsm)
                .build();

        // Set the initialized instance as a globally accessible instance.
        Analytics.setSingletonInstance(analytics);

    }
}
