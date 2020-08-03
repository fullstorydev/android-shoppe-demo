package com.fullstorydev.shoppedemo.utilities;

import com.fullstory.FS;
import com.fullstorydev.shoppedemo.BuildConfig;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.HashMap;
import java.util.Map;

public class CrashlyticsUtil {
    public static void identifyAndAddUserURLs(String userID) {
        Map<String, String> userVars = new HashMap<>();
        userVars.put("displayName", userID);
        userVars.put("crashlyticsURLAndroidDemoapp","https://console.firebase.google.com/u/0/project/fs-crashlytics/crashlytics/app/android:com.fullstorydev.shoppedemo>/search?time=last-seven-days&type=crash&q="+userID);

        FS.identify(userID, userVars);

        FirebaseCrashlytics instance = FirebaseCrashlytics.getInstance();
        // identify User in Crashlytics
        instance.setUserId(userID);
        // add FS links to the crash report as custom key
        String fsUserUrl = "https://app.staging.fullstory.com/ui/" + BuildConfig.ORG_ID + "/segments/everyone/people:search:(:((UserAppKey:==:%22" + userID + "%22)):():():():)/0)";
        String fsUserCrashUrl = "https://app.staging.fullstory.com/ui/" + BuildConfig.ORG_ID + "/segments/everyone/people:search:(:((UserAppKey:==:%22" + userID + "%22)):():(((EventType:==:\"crashed\"))):():)/0";
        instance.setCustomKey("FSUserSearchURL", fsUserUrl);
        instance.setCustomKey("FSUserCrashedSearchURL", fsUserCrashUrl);
    }

    public static void handleNonFatalException(Exception e) {
        FirebaseCrashlytics.getInstance().recordException(e);
        // Send the error to FS
        Map<String, String> eventVars = new HashMap<>();
        eventVars.put("errorMessage", e.getMessage());
        FS.event("nonFatalException",eventVars);
        FS.log(FS.LogLevel.ERROR,e.getMessage());
    }

    public static void log(String logMsg) {
        FirebaseCrashlytics.getInstance().log(logMsg);
        FS.log(FS.LogLevel.INFO, logMsg);
    }
}
