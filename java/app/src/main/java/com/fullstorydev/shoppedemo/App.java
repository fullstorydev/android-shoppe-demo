package com.fullstorydev.shoppedemo;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.fullstory.FS;
import com.fullstorydev.shoppedemo.fssegment.FullStoryMiddleware;
import com.segment.analytics.Analytics;
import static com.segment.analytics.internal.Utils.getSegmentSharedPreferences;


public class App extends MultiDexApplication {
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    public void onCreate() {
        super.onCreate();

        final String writeKey = BuildConfig.SEGMENT_WRITE_KEY;
        final String segmentTag = writeKey;

        // Segment integration
        // Create an analytics client with the given context and Segment write key.
        Analytics analytics = new Analytics.Builder(getApplicationContext(), writeKey)
                // use a tag for caching, if not set by default write key is used. We set the key to
                // https://github.com/segmentio/analytics-android/blob/a9fefda981bd67e4b151f1e466e3113fdb866f00/analytics/src/main/java/com/segment/analytics/Analytics.java#L1146
                .tag(segmentTag)
//                .logLevel(Analytics.LogLevel.VERBOSE)
//                .use(FirebaseIntegration.FACTORY)
//                .use(FullStoryIntegration.FACTORY)
                .trackApplicationLifecycleEvents()
//                .recordScreenViews()

                // add middleware for FullStory
                .middleware(new FullStoryMiddleware())
                .build();

        // Analytics reset does not use middleware, so we need to listen to when the userID becomes null in shared preference and call anonymize to logout the user properly
        // https://segment.com/docs/connections/sources/catalog/libraries/mobile/android/#reset
        SharedPreferences sharedPreferences = getSegmentSharedPreferences(getApplicationContext(), segmentTag);
        listener = (sharedPreferences1, key) -> {
            if(key.equals("traits-" + segmentTag) && sharedPreferences1.getString("userId",null) == null){
                Log.d("App","Segment User ID has become null, calling FS.anonymize");
                FS.anonymize();
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);

        // Set the initialized instance as a globally accessible instance.
        Analytics.setSingletonInstance(analytics);

    }
}
