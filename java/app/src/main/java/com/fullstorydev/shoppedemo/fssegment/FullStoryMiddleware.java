//package com.fullstorydev.shoppedemo.fssegment;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.util.Log;
//
//import com.fullstory.FS;
//import com.segment.analytics.Middleware;
//import com.segment.analytics.ValueMap;
//import com.segment.analytics.integrations.BasePayload;
//import com.segment.analytics.integrations.GroupPayload;
//import com.segment.analytics.integrations.IdentifyPayload;
//import com.segment.analytics.integrations.ScreenPayload;
//import com.segment.analytics.integrations.TrackPayload;
//
//import static com.segment.analytics.internal.Utils.getSegmentSharedPreferences;
//import static com.segment.analytics.internal.Utils.isNullOrEmpty;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class FullStoryMiddleware implements Middleware {
//
//    public boolean enableGroupTraitsAsUserVars = false;
//    public boolean enableFSSessionURLInEvents = true;
//    public boolean enableSendScreenAsEvents = false;
//    private ArrayList<String> whitelistedEvents = new ArrayList<>();
//
//    private final String TAG = "FullStoryMiddleware";
//
//    /**
//     * Initialize FullStory middle ware with application context, segemntTag, and whitelisted events.
//     * This middleware will enable Segment APIs to be passed into FullStory session replay, for more information go to: https://help.fullstory.com/hc/en-us/sections/360007387073-Native-Mobile
//     * @param context Application context that's passed in to Segment Analytics builder
//     * @param segmentTag Segment tag if not set this should be the same as segment write key
//     * @param whitelistEvents whitelist any events that you would like to be passed to FullStory automatically
//     */
//    public FullStoryMiddleware (Context context, String segmentTag, ArrayList<String> whitelistEvents){
//        this.whitelistedEvents = whitelistEvents;
//
//        // Analytics reset does not use middleware, so we need to listen to when the userID becomes null in shared preference and call anonymize to logout the user properly
//        SharedPreferences sharedPreferences = getSegmentSharedPreferences(context, segmentTag);
//        if(isNullOrEmpty(sharedPreferences.getAll())) { Log.w(TAG,"Unable to find Segment preferences, can not anonymize user when Analytics.reset() is called!");}
//        SharedPreferences.OnSharedPreferenceChangeListener listener = (sharedPreferences1, key) -> {
//            if (key.equals("traits-" + segmentTag) && sharedPreferences1.getString("userId", null) == null) {
//                Log.d(TAG, "Segment User ID has become null, calling FS.anonymize");
//                FS.anonymize();
//            }
//        };
//        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
//    }
//
//    /**
//     * Initialize FullStory middle ware with application context, segemntTag.
//     * With no whitelisted events, no Segment track events will be passed to FullStory session replay.
//     * For more information go to: https://help.fullstory.com/hc/en-us/sections/360007387073-Native-Mobile
//     * @param context Application context that's passed in to Segment Analytics builder
//     * @param segmentTag Segment tag if not set this should be the same as segment write key
//     */
//    public FullStoryMiddleware (Context context, String segmentTag){
//        this(context, segmentTag, new ArrayList<>());
//        Log.i(TAG,"no events whitelisted, will not forward Segment track/screen events to FullStory!");
//    }
//
//    @Override
//    public void intercept(Chain chain) {
//        // Get the original payload from chain
//        BasePayload payload = chain.payload();
//
//        // create a place holder payload to be created
//        BasePayload newPayload = null;
//
//        // available payloads:
//        // https://github.com/segmentio/analytics-android/tree/master/analytics/src/main/java/com/segment/analytics/integrations
//        switch (payload.type()){
//            case group:
//                GroupPayload groupPayload = (GroupPayload) payload;
//
//                // User will always be tied to the newest groupID that you set, group traits will not be included by default!
//                // this is because if the user changes it's group which no longer has certain traits, we will not detect when to clear them in FS and user will be tied to old group data
//                HashMap<String, Object> userVars = new HashMap<>();
//                userVars.put("groupID",groupPayload.groupId());
//                // optionally enable group traits to be passed into user vars
//                if(enableGroupTraitsAsUserVars && !isNullOrEmpty(groupPayload.traits())) { userVars.putAll(groupPayload.traits()); }
//                FS.setUserVars(userVars);
//                break;
//
//            case identify:
//                IdentifyPayload identifyPayload = (IdentifyPayload) payload;
//                FS.identify(identifyPayload.userId(), identifyPayload.traits());
//                break;
//
//            case screen:
//                ScreenPayload screenPayload = (ScreenPayload) payload;
//                if(this.enableSendScreenAsEvents){ FS.event("visited screen: " + screenPayload.name(), screenPayload.properties()); }
//                break;
//
//            case track:
//                TrackPayload trackPayload = (TrackPayload) payload;
//                if(this.whitelistedEvents.indexOf(trackPayload.event()) != -1){ FS.event(trackPayload.event(),trackPayload.properties()); }
//                break;
//
//            default:
//                Log.d("FullStoryMiddleware","No matching API found, not calling any FS APIs...");
//                break;
//        }
//
//        Map<String, Object> context = new LinkedHashMap<>(payload.context());
//        if(this.enableFSSessionURLInEvents){
//            newPayload = getNewPayloadWithFSURL(payload,context);
//        }
//
//        if(newPayload == null) newPayload = payload.toBuilder().context(context).build();
//        chain.proceed(newPayload);
//    }
//
//    private BasePayload getNewPayloadWithFSURL(BasePayload payload, Map<String, Object>  context){
//        // properties obj is immutable so we need to create a new one
//        ValueMap properties = payload.getValueMap("properties");
//        Map<String, Object> fullStoryProperties= new HashMap<>();
//        if(!isNullOrEmpty(properties)) fullStoryProperties.putAll(properties);
//
//        fullStoryProperties.put("fullstoryUrl", FS.getCurrentSessionURL());
////            // now URL API available post FullStory plugin v1.3.0
////        fullStoryProperties.put("fullstoryNowUrl", FS.getCurrentSessionURL(true));
//        context.put("fullstoryUrl", FS.getCurrentSessionURL());
//
//        if(payload.type() == BasePayload.Type.screen){
//            ScreenPayload screenPayload = (ScreenPayload) payload;
//            ScreenPayload.Builder screenPayloadBuilder = screenPayload.toBuilder()
//                    .context(context)
//                    .properties(fullStoryProperties);
//            return screenPayloadBuilder.build();
//
//        }else if(payload.type() == BasePayload.Type.track){
//            TrackPayload trackPayload = (TrackPayload) payload;
//            TrackPayload.Builder trackPayloadBuilder = trackPayload.toBuilder()
//                    .context(context)
//                    .properties(fullStoryProperties);
//            return trackPayloadBuilder.build();
//        }
//        return null;
//    }
//}