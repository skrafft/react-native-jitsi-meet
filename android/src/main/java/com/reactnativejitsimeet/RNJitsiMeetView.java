package com.reactnativejitsimeet;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;

import org.jitsi.meet.sdk.BaseReactView;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetViewListener;
import org.jitsi.meet.sdk.ListenerUtils;
import org.jitsi.meet.sdk.log.JitsiMeetLogger;

import java.lang.reflect.Method;
import java.util.Map;


public class RNJitsiMeetView extends BaseReactView<JitsiMeetViewListener>
        implements RNOngoingConferenceTracker.OngoingConferenceListener {

    /**
     * The {@code Method}s of {@code JitsiMeetViewListener} by event name i.e.
     * redux action types.
     */
    private static final Map<String, Method> LISTENER_METHODS
            = ListenerUtils.mapListenerMethods(JitsiMeetViewListener.class);

    /**
     * The URL of the current conference.
     */
    // XXX Currently, one thread writes and one thread reads, so it should be
    // fine to have this field volatile without additional synchronization.
    private volatile String url;

    /**
     * Helper method to recursively merge 2 {@link Bundle} objects representing React Native props.
     *
     * @param a - The first {@link Bundle}.
     * @param b - The second {@link Bundle}.
     * @return The merged {@link Bundle} object.
     */
    private static Bundle mergeProps(@Nullable Bundle a, @Nullable Bundle b) {
        Bundle result = new Bundle();

        if (a == null) {
            if (b != null) {
                result.putAll(b);
            }

            return result;
        }

        if (b == null) {
            result.putAll(a);

            return result;
        }

        // Start by putting all of a in the result.
        result.putAll(a);

        // Iterate over each key in b and override if appropriate.
        for (String key : b.keySet()) {
            Object bValue = b.get(key);
            Object aValue = a.get(key);
            String valueType = bValue.getClass().getSimpleName();

            if (valueType.contentEquals("Boolean")) {
                result.putBoolean(key, (Boolean)bValue);
            } else if (valueType.contentEquals("String")) {
                result.putString(key, (String)bValue);
            } else if (valueType.contentEquals("Bundle")) {
                result.putBundle(key, mergeProps((Bundle)aValue, (Bundle)bValue));
            } else {
                throw new RuntimeException("Unsupported type: " + valueType);
            }
        }

        return result;
    }

    public RNJitsiMeetView(@NonNull Context context) {
        super(context);

        RNOngoingConferenceTracker.getInstance().addListener(this);
    }

    @Override
    public void dispose() {
        RNOngoingConferenceTracker.getInstance().removeListener(this);
        super.dispose();
    }

    /**
     * Enters Picture-In-Picture mode, if possible. This method is designed to
     * be called from the {@code Activity.onUserLeaveHint} method.
     *
     * This is currently not mandatory, but if used will provide automatic
     * handling of the picture in picture mode when user minimizes the app. It
     * will be probably the most useful in case the app is using the welcome
     * page.
     */
    public void enterPictureInPicture() {
        JitsiMeetLogger.e("PiP not supported");
    }

    /**
     * Joins the conference specified by the given {@link RNJitsiMeetConferenceOptions}. If there is
     * already an active conference, it will be left and the new one will be joined.
     * @param options - Description of what conference must be joined and what options will be used
     *                when doing so.
     */
    public void join(@Nullable RNJitsiMeetConferenceOptions options) {
        setProps(options != null ? options.asProps() : new Bundle());
    }

    /**
     * Leaves the currently active conference.
     */
    public void leave() {
        setProps(new Bundle());
    }

    /**
     * Helper method to set the React Native props.
     * @param newProps - New props to be set on the React Native view.
     */
    private void setProps(@NonNull Bundle newProps) {
        // Merge the default options with the newly provided ones.
        Bundle props = mergeProps(new Bundle(), newProps);

        // XXX The setProps() method is supposed to be imperative i.e.
        // a second invocation with one and the same URL is expected to join
        // the respective conference again if the first invocation was followed
        // by leaving the conference. However, React and, respectively,
        // appProperties/initialProperties are declarative expressions i.e. one
        // and the same URL will not trigger an automatic re-render in the
        // JavaScript source code. The workaround implemented bellow introduces
        // "imperativeness" in React Component props by defining a unique value
        // per setProps() invocation.
        props.putLong("timestamp", System.currentTimeMillis());

        createReactRootView("App", props);
    }

    /**
     * Handler for {@link RNOngoingConferenceTracker} events.
     * @param conferenceUrl
     */
    @Override
    public void onCurrentConferenceChanged(String conferenceUrl) {
        // This property was introduced in order to address
        // an exception in the Picture-in-Picture functionality which arose
        // because of delays related to bridging between JavaScript and Java. To
        // reduce these delays do not wait for the call to be transferred to the
        // UI thread.
        this.url = conferenceUrl;
    }

    /**
     *
     * @param name The name of the event.
     * @param data The details/specifics of the event to send determined
     * by/associated with the specified {@code name}.
     */
    @Override
    protected void onExternalAPIEvent(String name, ReadableMap data) {
        onExternalAPIEvent(LISTENER_METHODS, name, data);
    }
}