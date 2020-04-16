package com.fullstorydev.shoppedemo;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.fullstory.FS;
import com.segment.analytics.Analytics;
import com.segment.analytics.Middleware;
import com.segment.analytics.integrations.AliasPayload;
import com.segment.analytics.integrations.BasePayload;
import com.segment.analytics.integrations.GroupPayload;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;

import java.util.LinkedHashMap;
import java.util.Map;

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
                .middleware(new Middleware() {
                    @Override
                    public void intercept(Chain chain) {
                        // Get the original payload from chain
                        BasePayload payload = chain.payload();

                        Log.d("middleware",payload.type().toString());
                        // https://github.com/segmentio/analytics-android/tree/master/analytics/src/main/java/com/segment/analytics/integrations
                        switch (payload.type()){
                            case alias:
                                AliasPayload aliasPayload = (AliasPayload) payload;
                                Log.d("middleware","alias userid: " + aliasPayload.userId());
                                Log.d("middleware","alias previousId: " + aliasPayload.previousId());
                                break;
                            case group:
                                GroupPayload groupPayload = (GroupPayload) payload;
                                Log.d("middleware","group userid: " + groupPayload.userId());
                                Log.d("middleware","group id: " + groupPayload.groupId());
                                Log.d("middleware","group traits: " + groupPayload.traits());
                                break;
                            case identify://FS.identify
                                IdentifyPayload identifyPayload = (IdentifyPayload) payload;
                                Log.d("middleware","identify userid: " + identifyPayload.userId());
                                Log.d("middleware","identify traits: " + identifyPayload.traits());
                                break;
                            case screen:
                                ScreenPayload screenPayload = (ScreenPayload) payload;
                                Log.d("middleware","screen name: " + screenPayload.name());
                                Log.d("middleware","screen event: " + screenPayload.event());
                                Log.d("middleware","screen properties: " + screenPayload.properties());
                                break;
                            case track://FS.track
                                TrackPayload trackPayload = (TrackPayload) payload;
                                Log.d("middleware","track event: " + trackPayload.event());
                                Log.d("middleware","track properties: " + trackPayload.properties());
                                break;
                            default:
                                Log.d("middleware","default: no known payload identified");
                                Log.d("middleware","default to logger?" + payload.type());
                                break;
                        }
                        // Set the device year class on the context object.

                        Map<String, Object> context = new LinkedHashMap<>(payload.context());
                        context.put("fullstory_url", FS.getCurrentSessionURL());
                        context.put("fullstory_now_url", FS.getCurrentSessionURL() + ':' + System.currentTimeMillis());
//                        Build our new payload.
                        BasePayload newPayload = payload.toBuilder()
                                .context(context)
                                .build();

//                       Continue with the new payload.
                        chain.proceed(newPayload);
                    }
                })
                .build();

// Set the initialized instance as a globally accessible instance.
        Analytics.setSingletonInstance(analytics);
    }
}
