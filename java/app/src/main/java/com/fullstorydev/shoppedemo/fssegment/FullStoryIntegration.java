package com.fullstorydev.shoppedemo.fssegment;

import com.fullstory.FS;
import com.fullstory.FSOnReadyListener;
import com.fullstory.FSSessionData;
import com.segment.analytics.Analytics;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.GroupPayload;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.Integration;
import com.segment.analytics.integrations.Logger;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;

import static com.segment.analytics.internal.Utils.isNullOrEmpty;

import java.util.Map;

public class FullStoryIntegration extends Integration<FS> implements FSOnReadyListener {
    public static final Factory FACTORY =
            new Factory() {
                @Override
                public Integration<?> create(ValueMap settings, Analytics analytics) {
                    return new FullStoryIntegration(analytics, settings);
                }

                @Override
                public String key() {
                    return FS_KEY;
                }
            };

    private static final String FS_KEY = "FullStory";
    private final Logger logger;

    FullStoryIntegration(Analytics analytics, ValueMap settings) {
        logger = analytics.logger(FS_KEY);
    }

    @Override
    public void identify(IdentifyPayload identify) {
        super.identify(identify);

        Map<String, Object> traits = identify.traits();
        String userID = identify.userId();

        if(!isNullOrEmpty(userID) && !isNullOrEmpty(traits)) {
            FS.identify(userID,traits);
            logger.verbose("FS.identify(%s, %s);", userID, traits);
        }
        else if (!isNullOrEmpty(userID)) {
            FS.identify(userID);
            logger.verbose("FS.identify(%s);", userID);
        }
        else if(!isNullOrEmpty(traits)) {
            FS.setUserVars(traits);
            logger.verbose("FS.setUserVars(%s);", traits);
        }
    }

    @Override
    public void screen(ScreenPayload screen) {
        super.screen(screen);

        FS.event("visited screen: " + screen.name(), screen.properties());
        logger.verbose("FS.event(visited screen: %s, %s);",screen.name(), screen.properties());
    }

    @Override
    public void track(TrackPayload track) {
        super.track(track);

        FS.event(track.event(),track.properties());
        logger.verbose("FS.event(%s, %s);",track.event(), track.properties());
    }

    @Override
    public void group(GroupPayload group) {
        super.group(group);
        FS.setUserVars(group.traits());
        logger.verbose("FS.setUserVars(%s);",group.traits());
    }

    @Override
    public void reset() {
        super.reset();
        FS.anonymize();
    }

    @Override
    public void onReady(FSSessionData sessionData) {
        logger.verbose("FS Session URL: %s;",FS.getCurrentSessionURL());
    }
}
