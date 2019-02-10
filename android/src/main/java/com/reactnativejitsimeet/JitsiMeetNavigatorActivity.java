package com.reactnativejitsimeet;

import java.util.Map;
import java.util.HashMap;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.PermissionListener;

import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.ReadableMap;

import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;
import org.jitsi.meet.sdk.ReactActivityLifecycleCallbacks;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;

public class JitsiMeetNavigatorActivity extends AppCompatActivity implements JitsiMeetViewListener, JitsiMeetActivityInterface {

    /**
     * Instance of the {@link JitsiMeetView} which this activity will display.
     */
    private JitsiMeetView view;


    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {
        ReactActivityLifecycleCallbacks.onActivityResult(
                this, requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        ReactActivityLifecycleCallbacks.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra("url");
        view = new JitsiMeetView(this);
        view.setListener(this);
        view.loadURLString(url);

        setContentView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (view != null) {
            view.dispose();
            view = null;
        }

        ReactActivityLifecycleCallbacks.onHostDestroy(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        ReactActivityLifecycleCallbacks.onNewIntent(intent);
    }

    // https://developer.android.com/reference/android/support/v4/app/ActivityCompat.OnRequestPermissionsResultCallback
    @Override
    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {
        ReactActivityLifecycleCallbacks.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Implementation of the {@code PermissionAwareActivity} interface.
     */
    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        ReactActivityLifecycleCallbacks.requestPermissions(this, permissions, requestCode, listener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ReactActivityLifecycleCallbacks.onHostResume(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        ReactActivityLifecycleCallbacks.onHostPause(this);
    }

    @Override
    protected void onUserLeaveHint() {
        if (view != null) {
            view.enterPictureInPicture();
        }
    }

    private void on(String name, Map<String, Object> data) {
        UiThreadUtil.assertOnUiThread();

        // Log with the tag "ReactNative" in order to have the log
        // visible in react-native log-android as well.
        Log.d(
            "JitsiMeet",
            JitsiMeetViewListener.class.getSimpleName() + " "
                + name + " "
                + data);
        Intent intent = new Intent(name);
        intent.putExtra("data", (HashMap<String, Object>) data);
        sendBroadcast(intent, getApplication().getPackageName() + ".permission.JITSI_BROADCAST");
    }

    public void onConferenceFailed(Map<String, Object> data) {
        on("CONFERENCE_FAILED", data);
    }

    public void onConferenceJoined(Map<String, Object> data) {
        on("CONFERENCE_JOINED", data);
    }

    public void onConferenceLeft(Map<String, Object> data) {
        this.onBackPressed();
        on("CONFERENCE_LEFT", data);
    }

    public void onConferenceWillJoin(Map<String, Object> data) {
        on("CONFERENCE_WILL_JOIN", data);
    }

    public void onConferenceWillLeave(Map<String, Object> data) {
        on("CONFERENCE_WILL_LEAVE", data);
    }

    public void onLoadConfigError(Map<String, Object> data) {
        on("LOAD_CONFIG_ERROR", data);
    }
}
