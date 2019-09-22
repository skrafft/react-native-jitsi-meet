package com.reactnativejitsimeet;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RNJitsiMeetPackage implements ReactPackage, IRNJitsiMeetViewReference {

    private RNJitsiMeetView mJitsiMeetView = null;

    public void setJitsiMeetView(RNJitsiMeetView jitsiMeetView) {
        mJitsiMeetView = jitsiMeetView;
    }

    public RNJitsiMeetView getJitsiMeetView() {
        return mJitsiMeetView;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new RNJitsiMeetModule(reactContext, this));
        return modules;
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new RNJitsiMeetViewManager(reactContext, this)
        );
    }
}
