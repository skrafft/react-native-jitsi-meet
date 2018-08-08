package com.reactnativejitsimeet;

import java.util.Map;
import java.util.HashMap;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.ReadableMap;

import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

public class JitsiMeetNavigatorActivity extends AppCompatActivity implements JitsiMeetViewListener{
    private JitsiMeetView view;
    private String JITSI_BROADCAST = "com.reactnativejitsimeet.permission.JITSI_BROADCAST";

    @Override
    public void onBackPressed() {
        if (!JitsiMeetView.onBackPressed()) {
            // Invoke the default handler if it wasn't handled by React.
            super.onBackPressed();
        }
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

        view.dispose();
        view = null;

        JitsiMeetView.onHostDestroy(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        JitsiMeetView.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        JitsiMeetView.onHostResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        JitsiMeetView.onHostPause(this);
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
        sendBroadcast(intent, JITSI_BROADCAST);
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
