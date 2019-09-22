package com.reactnativejitsimeet;

import com.facebook.react.bridge.ReadableMap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;


/**
 * Helper class to keep track of what the current conference is.
 */
class RNOngoingConferenceTracker {
    private static final RNOngoingConferenceTracker instance = new RNOngoingConferenceTracker();

    private static final String CONFERENCE_WILL_JOIN = "CONFERENCE_WILL_JOIN";
    private static final String CONFERENCE_TERMINATED = "CONFERENCE_TERMINATED";

    private final Collection<OngoingConferenceListener> listeners =
            Collections.synchronizedSet(new HashSet<OngoingConferenceListener>());
    private String currentConference;

    public RNOngoingConferenceTracker() {
    }

    public static RNOngoingConferenceTracker getInstance() {
        return instance;
    }

    /**
     * Gets the current active conference URL.
     *
     * @return - The current conference URL as a String.
     */
    synchronized String getCurrentConference() {
        return currentConference;
    }

    synchronized void onExternalAPIEvent(String name, ReadableMap data) {
        if (!data.hasKey("url")) {
            return;
        }

        String url = data.getString("url");
        if (url == null) {
            return;
        }

        switch(name) {
            case CONFERENCE_WILL_JOIN:
                currentConference = url;
                updateListeners();
                break;

            case CONFERENCE_TERMINATED:
                if (url.equals(currentConference)) {
                    currentConference = null;
                    updateListeners();
                }
                break;
        }
    }

    void addListener(OngoingConferenceListener listener) {
        listeners.add(listener);
    }

    void removeListener(OngoingConferenceListener listener) {
        listeners.remove(listener);
    }

    private void updateListeners() {
        synchronized (listeners) {
            for (OngoingConferenceListener listener : listeners) {
                listener.onCurrentConferenceChanged(currentConference);
            }
        }
    }

    public interface OngoingConferenceListener {
        void onCurrentConferenceChanged(String conferenceUrl);
    }
}
