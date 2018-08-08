package com.reactnativejitsimeet;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import com.facebook.react.bridge.WritableMap;

import java.util.Map;

public class JitsiBroadcastReceiver extends BroadcastReceiver{

    private JitsiMeetModule reactModule;

    public void setReactModule(JitsiMeetModule module) {
        this.reactModule = module;
    }

    public void onReceive(Context context, Intent intent) {
        Map<String, Object> data = (Map<String, Object>) intent.getSerializableExtra("data");
        WritableMap bridgeableData = MapUtil.toWritableMap(data);
        String action = intent.getAction();
        if (reactModule != null) {
            reactModule.onEventReceived(action, bridgeableData);
        }
    }
}
