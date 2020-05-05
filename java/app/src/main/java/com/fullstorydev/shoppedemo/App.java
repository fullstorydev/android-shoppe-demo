package com.fullstorydev.shoppedemo;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.fullstory.FS;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.ValueMap;
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
//                .trackApplicationLifecycleEvents()
                // Enable this to record screen views automatically!
//                .recordScreenViews()
                .logLevel(Analytics.LogLevel.VERBOSE)
                .middleware(chain -> {
                    // Get the original payload from chain
                    BasePayload payload = chain.payload();

                    // Add FullStory URL to event properties
                    ValueMap properties = payload.getValueMap("properties");
                    Map<String, Object> fullStoryProperties = new HashMap<>();
                    if(properties != null) fullStoryProperties.putAll(properties);
                    fullStoryProperties.put("fullstoryUrl", FS.getCurrentSessionURL());
                    // now URL API available post 1.3.0
                    fullStoryProperties.put("fullstoryNowUrl", FS.getCurrentSessionURL(true));

                    Log.d("middleware", String.valueOf(fullStoryProperties));
                    Log.d("middleware", String.valueOf(payload.type()));

                    // Add any other information to context
                    // Example: Set the device year class on the context object.
                    Map<String, Object> context = new LinkedHashMap<>(payload.context());
                    context.put("device_year_class", 2019);

                    Log.d("middleware","here" + context.get("userID"));

                    BasePayload newPayload = null;

                    // https://github.com/segmentio/analytics-android/tree/master/analytics/src/main/java/com/segment/analytics/integrations
                    switch (payload.type()){
                        case group: // FS.setuservar?
                            GroupPayload groupPayload = (GroupPayload) payload;
                            Log.d("middleware","group userid: " + groupPayload.userId());
                            Log.d("middleware","group id: " + groupPayload.groupId());
                            Log.d("middleware","group traits: " + groupPayload.traits());
                            break;
                        case identify:
                            IdentifyPayload identifyPayload = (IdentifyPayload) payload;

                            //identify user in FullStory
                            FS.identify(identifyPayload.userId(), identifyPayload.traits());

                            break;
                        case screen:
                            ScreenPayload screenPayload = (ScreenPayload) payload;

                            // send custom event to FS
                            FS.event("visited screen: " + screenPayload.name(), screenPayload.properties());

                            //add FS URL to screen payload properties and build new payload
                            ScreenPayload.Builder screenPayloadBuilder = screenPayload.toBuilder()
                                    .context(context)
                                    .properties(fullStoryProperties);
                            newPayload = screenPayloadBuilder.build();

                            break;
                        case track:
                            TrackPayload trackPayload = (TrackPayload) payload;

                            // send custom event to FS
                            FS.event(trackPayload.event(),trackPayload.properties());

                            //add FS URL to track payload properties and build new payload
                            TrackPayload.Builder trackPayloadBuilder = trackPayload.toBuilder()
                                    .context(context)
                                    .properties(fullStoryProperties);
                            newPayload = trackPayloadBuilder.build();

                            break;
                        default:
                            Log.d("middleware","No matching API found, not adding FS...");
                            break;
                    }

                    if(newPayload == null) newPayload = payload.toBuilder().context(context).build();
                    chain.proceed(newPayload);
                })
                .build();

        //TODO: check for userid, if user id is null then anonymmize the user?
        // https://segment.com/docs/connections/sources/catalog/libraries/mobile/android/#reset
        // Events queued on disk are not cleared and are uploaded the next time the app starts, or listen to shared pref


        // Set the initialized instance as a globally accessible instance.
        Analytics.setSingletonInstance(analytics);

    }
}
