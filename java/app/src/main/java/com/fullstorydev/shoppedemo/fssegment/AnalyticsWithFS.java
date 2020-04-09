package com.fullstorydev.shoppedemo.fssegment;

import android.content.Context;

import com.fullstory.FS;
import com.segment.analytics.Analytics;

public class AnalyticsWithFS {
    private Analytics analytics;
    private static volatile AnalyticsWithFS singleton = null;

    private AnalyticsWithFS(Context context){
        this.analytics = Analytics.with(context);
    }

    // mimicking the design pattern for Segment Analytics to preserve the syntax: Analytics.with(context)....
    public static AnalyticsWithFS with(Context context) {
        if (singleton == null) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            synchronized (AnalyticsWithFS.class) {
                if (singleton == null) {
                    singleton = new AnalyticsWithFS(context);
                }
            }
        }
        return singleton;
    }

    public void identify(String id){
        this.analytics.identify(id);
        FS.identify(id);
    }
}