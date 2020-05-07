package com.fullstorydev.shoppedemo.fssegment;

import android.util.Log;

import com.fullstory.FS;
import com.segment.analytics.Middleware;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.BasePayload;
import com.segment.analytics.integrations.GroupPayload;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;
import static com.segment.analytics.internal.Utils.isNullOrEmpty;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FullStoryMiddleware implements Middleware {
    @Override
    public void intercept(Chain chain) {
        // Get the original payload from chain
        BasePayload payload = chain.payload();

        // If properties is available for this payload add FullStory URLs to event properties
        // properties obj is immutable so we need to create a new one
        ValueMap properties = payload.getValueMap("properties");
        Map<String, Object> fullStoryProperties = new HashMap<>();
        if(properties != null) fullStoryProperties.putAll(properties);
        fullStoryProperties.put("fullstoryUrl", FS.getCurrentSessionURL());
        // now URL API available post FullStory plugin v1.3.0
        fullStoryProperties.put("fullstoryNowUrl", FS.getCurrentSessionURL(true));

        // Add any other information you would like to context
        // For example: add the device year class and FS URL to the context object.
        Map<String, Object> context = new LinkedHashMap<>(payload.context());
        context.put("device_year_class", 2019);
        context.put("fullstoryUrl", FS.getCurrentSessionURL());

        // create a place holder payload to be created
        BasePayload newPayload = null;

        // available payloads:
        // https://github.com/segmentio/analytics-android/tree/master/analytics/src/main/java/com/segment/analytics/integrations
        switch (payload.type()){
            case group:
                GroupPayload groupPayload = (GroupPayload) payload;

                // User will always be tied to the newest groupID that you set, group traits will not be included
                // this is because if the user changes it's group which no longer has certain traits, we will not detect when to clear them in FS and user will be tied to old group data
                HashMap<String, Object> userVars = new HashMap<>();
                userVars.put("groupID",groupPayload.groupId());
                FS.setUserVars(userVars);
                break;
            case identify:
                IdentifyPayload identifyPayload = (IdentifyPayload) payload;

                //identify user in FullStory along with the user's traits
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
                Log.d("FullStoryMiddleware","No matching API found, not falling FS APIs...");
                break;
        }

        if(newPayload == null) newPayload = payload.toBuilder().context(context).build();
        chain.proceed(newPayload);
    }

}