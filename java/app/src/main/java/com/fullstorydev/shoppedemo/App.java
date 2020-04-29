package com.fullstorydev.shoppedemo;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.fullstory.FS;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.android.integrations.firebase.FirebaseIntegration;
import com.segment.analytics.integrations.AliasPayload;
import com.segment.analytics.integrations.BasePayload;
import com.segment.analytics.integrations.GroupPayload;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;

import java.util.HashMap;
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
                .use(FirebaseIntegration.FACTORY)
                .trackApplicationLifecycleEvents()
                // Enable this to record screen views automatically!
                .recordScreenViews()
                .logLevel(Analytics.LogLevel.VERBOSE)
                .middleware(chain -> {
                    // Get the original payload from chain
                    BasePayload payload = chain.payload();

                    // Add FullStory URL to event properties
                    Map<String, Object> p = payload.getValueMap("properties",Properties.class);
                    Map<String, Object> fsp = new HashMap<>(p);
                    String fsURL = FS.getCurrentSessionURL();
                    if(fsURL != null){ // only add when FS is initialized
                        fsp.put("fullstoryUrl", fsURL);
                        fsp.put("fullstoryNowUrl", FS.getCurrentSessionURL() + ':' + System.currentTimeMillis());
                    }

                    // Add any other information to context
                    // Example: Set the device year class on the context object.
                    Map<String, Object> context = new LinkedHashMap<>(payload.context());
                    context.put("device_year_class", 2019);

                    BasePayload newPayload = null;

                    // https://github.com/segmentio/analytics-android/tree/master/analytics/src/main/java/com/segment/analytics/integrations
                    switch (payload.type()){
                        case alias: // FS.setuservar? anonymize and identify?
                            AliasPayload aliasPayload = (AliasPayload) payload;
                            Log.d("middleware","alias userid: " + aliasPayload.userId());
                            Log.d("middleware","alias previousId: " + aliasPayload.previousId());
                            break;
                        case group: // FS.setuservar?
                            GroupPayload groupPayload = (GroupPayload) payload;
                            Log.d("middleware","group userid: " + groupPayload.userId());
                            Log.d("middleware","group id: " + groupPayload.groupId());
                            Log.d("middleware","group traits: " + groupPayload.traits());
                            break;
                        case identify:// FS.identify
                            IdentifyPayload identifyPayload = (IdentifyPayload) payload;
                            Log.d("middleware","identify userid: " + identifyPayload.userId());
                            Log.d("middleware","identify traits: " + identifyPayload.traits());
                            break;
                        case screen:// FS.event?
                            ScreenPayload screenPayload = (ScreenPayload) payload;
                            Log.d("middleware","screen name: " + screenPayload.name());
                            Log.d("middleware","screen event: " + screenPayload.event());
                            Log.d("middleware","screen properties: " + screenPayload.properties());
                            ScreenPayload.Builder screenPayloadBuilder = screenPayload.toBuilder()
                                    .context(context)
                                    .properties(fsp);
                            newPayload = screenPayloadBuilder.build();
                            break;
                        case track://FS.event
                            TrackPayload trackPayload = (TrackPayload) payload;
                            Log.d("middleware","track event: " + trackPayload.event());
                            Log.d("middleware","track properties: " + trackPayload.properties());
                            // send custom event to FS
                            FS.event(trackPayload.event(),trackPayload.properties());

                            TrackPayload.Builder trackPayloadBuilder = trackPayload.toBuilder()
                                    .context(context)
                                    .properties(fsp);
                            newPayload = trackPayloadBuilder.build();
                            break;
                        default:
                            Log.d("middleware","default: no known payload identified");
                            Log.d("middleware","default to logger?" + payload.type());
                            break;
                    }

                    if(newPayload == null) newPayload = payload.toBuilder().context(context).build();
                    chain.proceed(newPayload);
                })
                .build();

        // Set the initialized instance as a globally accessible instance.
        Analytics.setSingletonInstance(analytics);

    }
}
