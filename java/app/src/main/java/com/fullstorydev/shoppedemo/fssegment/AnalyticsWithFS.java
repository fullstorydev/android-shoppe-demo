package com.fullstorydev.shoppedemo.fssegment;

import android.content.Context;

import com.fullstory.FS;
import com.segment.analytics.Analytics;

public class AnalyticsWithFS {
    private Context context;
    static volatile AnalyticsWithFS singleton = null;

    AnalyticsWithFS(Context context){
        this.context = context;
    }

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
        Analytics.with(this.context).identify(id);
        FS.identify(id);
    }
}
