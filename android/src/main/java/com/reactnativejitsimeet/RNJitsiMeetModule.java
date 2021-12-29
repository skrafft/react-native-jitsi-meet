package com.reactnativejitsimeet;

import android.util.Log;
import java.net.URL;
import java.net.MalformedURLException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableMap;

@ReactModule(name = RNJitsiMeetModule.MODULE_NAME)
public class RNJitsiMeetModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RNJitsiMeetModule";
    private IRNJitsiMeetViewReference mJitsiMeetViewReference;

    public RNJitsiMeetModule(ReactApplicationContext reactContext, IRNJitsiMeetViewReference jitsiMeetViewReference) {
        super(reactContext);
        mJitsiMeetViewReference = jitsiMeetViewReference;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void initialize() {
        Log.d("JitsiMeet", "Initialize is deprecated in v2");
    }

    @ReactMethod
    public void call(String url, ReadableMap userInfo, ReadableMap meetOptions, ReadableMap meetFeatureFlags) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                        .setRoom(url)
                        .setToken(meetOptions.hasKey("token") ? meetOptions.getString("token") : "")
                        .setSubject(meetOptions.hasKey("subject") ? meetOptions.getString("subject") : "")
                        .setAudioMuted(meetOptions.hasKey("audioMuted") ? meetOptions.getBoolean("audioMuted") : false)
                        .setAudioOnly(meetOptions.hasKey("audioOnly") ? meetOptions.getBoolean("audioOnly") : false)
                        .setVideoMuted(meetOptions.hasKey("videoMuted") ? meetOptions.getBoolean("videoMuted") : false)
                        .setUserInfo(_userInfo)
                        .setFeatureFlag("add-people.enabled", meetFeatureFlags.hasKey("addPeopleEnabled") ? meetFeatureFlags.getBoolean("addPeopleEnabled") : true)
                        .setFeatureFlag("calendar.enabled", meetFeatureFlags.hasKey("calendarEnabled") ?meetFeatureFlags.getBoolean("calendarEnabled") : true)
                        .setFeatureFlag("call-integration.enabled", meetFeatureFlags.hasKey("callIntegrationEnabled") ?meetFeatureFlags.getBoolean("callIntegrationEnabled") : true)
                        .setFeatureFlag("chat.enabled", meetFeatureFlags.hasKey("chatEnabled") ?meetFeatureFlags.getBoolean("chatEnabled") : true)
                        .setFeatureFlag("close-captions.enabled", meetFeatureFlags.hasKey("closeCaptionsEnabled") ?meetFeatureFlags.getBoolean("closeCaptionsEnabled") : true)
                        .setFeatureFlag("invite.enabled", meetFeatureFlags.hasKey("inviteEnabled") ?meetFeatureFlags.getBoolean("inviteEnabled") : true)
                        .setFeatureFlag("android.screensharing.enabled", meetFeatureFlags.hasKey("androidScreenSharingEnabled") ?meetFeatureFlags.getBoolean("androidScreenSharingEnabled") : true)
                        .setFeatureFlag("live-streaming.enabled", meetFeatureFlags.hasKey("liveStreamingEnabled") ?meetFeatureFlags.getBoolean("liveStreamingEnabled") : true)
                        .setFeatureFlag("meeting-name.enabled", meetFeatureFlags.hasKey("meetingNameEnabled") ?meetFeatureFlags.getBoolean("meetingNameEnabled") : true)
                        .setFeatureFlag("meeting-password.enabled", meetFeatureFlags.hasKey("meetingPasswordEnabled") ?meetFeatureFlags.getBoolean("meetingPasswordEnabled") : true)
                        .setFeatureFlag("pip.enabled", meetFeatureFlags.hasKey("pipEnabled") ?meetFeatureFlags.getBoolean("pipEnabled") : true)
                        .setFeatureFlag("kick-out.enabled", meetFeatureFlags.hasKey("kickOutEnabled") ?meetFeatureFlags.getBoolean("kickOutEnabled") : true)
                        .setFeatureFlag("conference-timer.enabled", meetFeatureFlags.hasKey("conferenceTimerEnabled") ?meetFeatureFlags.getBoolean("conferenceTimerEnabled") : true)
                        .setFeatureFlag("video-share.enabled", meetFeatureFlags.hasKey("videoShareButtonEnabled") ?meetFeatureFlags.getBoolean("videoShareButtonEnabled") : true)
                        .setFeatureFlag("recording.enabled", meetFeatureFlags.hasKey("recordingEnabled") ?meetFeatureFlags.getBoolean("recordingEnabled") : true)
                        .setFeatureFlag("reactions.enabled", meetFeatureFlags.hasKey("reactionsEnabled") ?meetFeatureFlags.getBoolean("reactionsEnabled") : true)
                        .setFeatureFlag("raise-hand.enabled", meetFeatureFlags.hasKey("raiseHandEnabled") ?meetFeatureFlags.getBoolean("raiseHandEnabled") : true)
                        .setFeatureFlag("tile-view.enabled", meetFeatureFlags.hasKey("tileViewEnabled") ?meetFeatureFlags.getBoolean("tileViewEnabled") : true)
                        .setFeatureFlag("toolbox.alwaysVisible", meetFeatureFlags.hasKey("toolboxAlwaysVisible") ?meetFeatureFlags.getBoolean("toolboxAlwaysVisible") : false)
                        .setFeatureFlag("toolbox.enabled", meetFeatureFlags.hasKey("toolboxEnabled") ?meetFeatureFlags.getBoolean("toolboxEnabled") : true)
                        .setFeatureFlag("welcomepage.enabled", meetFeatureFlags.hasKey("welcomePageEnabled") ?meetFeatureFlags.getBoolean("welcomePageEnabled") : false)
                        .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void audioCall(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(true)
                            .setUserInfo(_userInfo)
                            .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void endCall() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    mJitsiMeetViewReference.getJitsiMeetView().leave();
                }
            }
        });
    }
}
