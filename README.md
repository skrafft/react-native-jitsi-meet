# react-native-jitsi-meet
React native wrapper for Jitsi Meet SDK

## Important notice

Jitsi Meet SDK is a packed React Native SDK. Running react-native-jitsi-meet will run this React Native SDK inside your React Native app. We know that this is suboptimal but sadly we did not find any other solution without massive rewrite of Jitsi Meet SDK. Compatibility with other libraries used internally by Jitsi Meet SDK might be broken (version mismatch) or you might experience performance issues or touch issues in some edge cases.

## Install

`npm install react-native-jitsi-meet --save` 

If you are using React-Native < 0.60, you should use a version < 2.0.0.  
For versions higher than 2.0.0, you need to add the following piece of code in your ```metro.config.js``` file to avoid conflicts between react-native-jitsi-meet and react-native in metro bundler.

```
const blacklist = require('metro-config/src/defaults/blacklist');

module.exports = {
  resolver: {
    blacklistRE: blacklist([
      /ios\/Pods\/JitsiMeetSDK\/Frameworks\/JitsiMeet.framework\/assets\/node_modules\/react-native\/.*/,
    ]),
  },
};
```

Although most of the process is automated, you still have to follow the platform install guide below ([iOS](#ios-install-for-rn--060) and [Android](#android-install)) to get this library to work.


## Use (>= 2.0.0)

The following component is an example of use:

```
import React, { useEffect } from 'react';
import { View } from 'react-native';
import JitsiMeet, { JitsiMeetView } from 'react-native-jitsi-meet';

const VideoCall = () => {
  const onConferenceTerminated = (nativeEvent) => {
    /* Conference terminated event */
  }

  const onConferenceJoined = (nativeEvent) => {
    /* Conference joined event */
  }

  const onConferenceWillJoin= (nativeEvent) => {
    /* Conference will join event */
  }

  useEffect(() => {
    setTimeout(() => {
      const url = 'https://meet.jit.si/deneme'; // can also be only room name and will connect to jitsi meet servers
      const userInfo = { displayName: 'User', email: 'user@example.com', avatar: 'https:/gravatar.com/avatar/abc123' };
      const options = {
        audioMuted: false,
        audioOnly: false,
        videoMuted: false,
        subject: "your subject",
        token: "your token"
      }
      const meetFeatureFlags = {
        addPeopleEnabled: true,
        calendarEnabled: true,
        callIntegrationEnabled: true,
        chatEnabled: true,
        closeCaptionsEnabled: true,
        inviteEnabled: true,
        androidScreenSharingEnabled: true,
        liveStreamingEnabled: true,
        meetingNameEnabled: true,
        meetingPasswordEnabled: true,
        pipEnabled: true,
        kickOutEnabled: true,
        conferenceTimerEnabled: true,
        videoShareButtonEnabled: true,
        recordingEnabled: true,
        reactionsEnabled: true,
        raiseHandEnabled: true,
        tileViewEnabled: true,
        toolboxAlwaysVisible: false,
        toolboxEnabled: true,
        welcomePageEnabled: false,
      }
      JitsiMeet.call(url, userInfo, options, meetFeatureFlags);
      /* You can also use JitsiMeet.audioCall(url) for audio only call */
      /* You can programmatically end the call with JitsiMeet.endCall() */
    }, 1000);
  }, [])

  return (
    <View style={{ backgroundColor: 'black', flex: 1 }}>
      <JitsiMeetView onConferenceTerminated={onConferenceTerminated} onConferenceJoined={onConferenceJoined} onConferenceWillJoin={onConferenceWillJoin} style={{ flex: 1, height: '100%', width: '100%' }} />
    </View>
  )
}

export default VideoCall;
```

You can also check the [ExampleApp](https://github.com/skrafft/react-native-jitsi-meet/tree/master/ExampleApp)

### Events

You can add listeners for the following events:
- onConferenceJoined
- onConferenceTerminated
- onConferenceWillJoin


## Use (< 2.0.0 and RN<0.60)

In your component, 

1.) import JitsiMeet and JitsiMeetEvents: `import JitsiMeet, { JitsiMeetEvents } from 'react-native-jitsi-meet';`

2.) add the following code: 

```
  const initiateVideoCall = () => {
    JitsiMeet.initialize();
    JitsiMeetEvents.addListener('CONFERENCE_LEFT', (data) => {
      console.log('CONFERENCE_LEFT');
    });
    setTimeout(() => {
      JitsiMeet.call(`<your url>`);
    }, 1000);
  };
```
### Events

You can add listeners for the following events:
- CONFERENCE_JOINED
- CONFERENCE_LEFT
- CONFERENCE_WILL_JOIN

## iOS Configuration

1.) navigate to `<ProjectFolder>/ios/<ProjectName>/`  
2.) edit `Info.plist` and add the following lines

```
<key>NSCameraUsageDescription</key>
<string>Camera Permission</string>
<key>NSMicrophoneUsageDescription</key>
<string>Microphone Permission</string>
```
3.) in `Info.plist`, make sure that 
```
<key>UIBackgroundModes</key>
<array>
</array>
```
contains `<string>voip</string>`

## iOS Install for RN >= 0.60
1.) Modify your Podfile to have ```platform :ios, '10.0'``` and execute ```pod install```  
2.) In Xcode, under Build setting set Enable Bitcode to No  

## iOS Install for RN < 0.60
### Step 1. Add Files Into Project
- 1-1.) in Xcode: Right click `Libraries` ➜ `Add Files to [project]`  
- 1-2.) choose `node_modules/react-native-jitsi-meet/ios/RNJitsiMeet.xcodeproj` then `Add`  
- 1-3.) add `node_modules/react-native-jitsi-meet/ios/WebRTC.framework` and `node_modules/react-native-jitsi-meet/ios/JitsiMeet.framework` to the Frameworks folder
- 1-4.) add `node_modules/react-native-jitsi-meet/ios/JitsiMeet.storyboard` in the same folder as AppDelegate.m
- 1-5.) Replace the following code in AppDelegate.m:

```
  UIViewController *rootViewController = [UIViewController new];
  rootViewController.view = rootView;
  self.window.rootViewController = rootViewController;
```
with this one
```
  UIViewController *rootViewController = [UIViewController new];
  UINavigationController *navigationController = [[UINavigationController alloc]initWithRootViewController:rootViewController];
  navigationController.navigationBarHidden = YES;
  rootViewController.view = rootView;
  self.window.rootViewController = navigationController; 
```

This will create a navigation controller to be able to navigate between the Jitsi component and your react native screens.

### Step 2. Add Library Search Path

2-1.) select `Build Settings`, find `Search Paths`  
2-2.) edit BOTH `Framework Search Paths` and `Library Search Paths`  
2-3.) add path on BOTH sections with: `$(SRCROOT)/../node_modules/react-native-jitsi-meet/ios` with `recursive`  

### Step 3. Change General Setting and Embed Framework

3-1.) go to `General` tab  
3-2.) change `Deployment Target` to `8.0`  
3-3.) add `WebRTC.framework` and `JitsiMeet.framework` in `Embedded Binaries` 

### Step 4. Link/Include Necessary Libraries

- 4-1.) click `Build Phases` tab, open `Link Binary With Libraries`  
- 4-2.) add `libRNJitsiMeet.a`  
- 4-3.) make sure `WebRTC.framework` and `JitsiMeet.framework` linked  
- 4-4.) add the following libraries depending on your version of XCode, some libraries might exist or not:  

```
AVFoundation.framework
AudioToolbox.framework
CoreGraphics.framework
GLKit.framework
CoreAudio.framework
CoreVideo.framework
VideoToolbox.framework
libc.tbd
libsqlite3.tbd
libstdc++.tbd
libc++.tbd
```

- 4-5.) Under `Build setting` set `Dead Code Stripping` to `No`, set `Enable Bitcode` to `No` and `Always Embed Swift Standard Libraries` to `Yes`
- 4-6.) Add the following script in a new "Run Script" phase in "Build Phases":

```
echo "Target architectures: $ARCHS"

APP_PATH="${TARGET_BUILD_DIR}/${WRAPPER_NAME}"

find "$APP_PATH" -name '*.framework' -type d | while read -r FRAMEWORK
do
FRAMEWORK_EXECUTABLE_NAME=$(defaults read "$FRAMEWORK/Info.plist" CFBundleExecutable)
FRAMEWORK_EXECUTABLE_PATH="$FRAMEWORK/$FRAMEWORK_EXECUTABLE_NAME"
echo "Executable is $FRAMEWORK_EXECUTABLE_PATH"
echo $(lipo -info "$FRAMEWORK_EXECUTABLE_PATH")

FRAMEWORK_TMP_PATH="$FRAMEWORK_EXECUTABLE_PATH-tmp"

# remove simulator's archs if location is not simulator's directory
case "${TARGET_BUILD_DIR}" in
*"iphonesimulator")
echo "No need to remove archs"
;;
*)
if $(lipo "$FRAMEWORK_EXECUTABLE_PATH" -verify_arch "i386") ; then
lipo -output "$FRAMEWORK_TMP_PATH" -remove "i386" "$FRAMEWORK_EXECUTABLE_PATH"
echo "i386 architecture removed"
rm "$FRAMEWORK_EXECUTABLE_PATH"
mv "$FRAMEWORK_TMP_PATH" "$FRAMEWORK_EXECUTABLE_PATH"
fi
if $(lipo "$FRAMEWORK_EXECUTABLE_PATH" -verify_arch "x86_64") ; then
lipo -output "$FRAMEWORK_TMP_PATH" -remove "x86_64" "$FRAMEWORK_EXECUTABLE_PATH"
echo "x86_64 architecture removed"
rm "$FRAMEWORK_EXECUTABLE_PATH"
mv "$FRAMEWORK_TMP_PATH" "$FRAMEWORK_EXECUTABLE_PATH"
fi
;;
esac

echo "Completed for executable $FRAMEWORK_EXECUTABLE_PATH"
echo $

done
```
This will run a script everytime you build to clean the unwanted architecture

## Android Install
1.) In `android/app/build.gradle`, add/replace the following lines:

```
project.ext.react = [
    entryFile: "index.js",
    bundleAssetName: "app.bundle",
]
```

2.) In `android/app/src/main/java/com/xxx/MainApplication.java` add/replace the following methods:

```
  import androidx.annotation.Nullable; // <--- Add this line if not already existing
  ...
    @Override
    protected String getJSMainModuleName() {
      return "index";
    }

    @Override
    protected @Nullable String getBundleAssetName() {
      return "app.bundle";
    }
```

3.) In `android/build.gradle`, add the following code 
```
allprojects {
    repositories {
        mavenLocal()
        jcenter()
        maven {
            // All of React Native (JS, Obj-C sources, Android binaries) is installed from npm
            url "$rootDir/../node_modules/react-native/android"
        }
        maven {
            url "https://maven.google.com"
        }
        maven { // <---- Add this block
            url "https://github.com/jitsi/jitsi-maven-repository/raw/master/releases"
        }
        maven { url "https://jitpack.io" }
    }
}
```

## Android Additional Install for RN < 0.60

1.) In `android/app/src/main/AndroidManifest.xml` add these permissions

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:tools="http://schemas.android.com/tools" // <--- Add this line if not already existing

...
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus"/>

<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<permission android:name="${applicationId}.permission.JITSI_BROADCAST"
    android:label="Jitsi Meet Event Broadcast"
    android:protectionLevel="normal"></permission>
<uses-permission android:name="${applicationId}.permission.JITSI_BROADCAST"/>
```

2.) In the `<application>` section of `android/app/src/main/AndroidManifest.xml`, add
 ```xml
 <activity android:name="com.reactnativejitsimeet.JitsiMeetNavigatorActivity" />
 ```
 
3.) In `android/settings.gradle`, include react-native-jitsi-meet module
```gradle
include ':react-native-jitsi-meet'
project(':react-native-jitsi-meet').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-jitsi-meet/android')
```

4.) In `android/app/build.gradle`, add react-native-jitsi-meet to dependencies
```gradle
android {
  ...
  packagingOptions {
      pickFirst 'lib/x86/libc++_shared.so'
      pickFirst 'lib/x86/libjsc.so'
      pickFirst 'lib/x86_64/libjsc.so'
      pickFirst 'lib/arm64-v8a/libjsc.so'
      pickFirst 'lib/arm64-v8a/libc++_shared.so'
      pickFirst 'lib/x86_64/libc++_shared.so'
      pickFirst 'lib/armeabi-v7a/libc++_shared.so'
      pickFirst 'lib/armeabi-v7a/libjsc.so'
  }
}
dependencies {
  ...
    implementation(project(':react-native-jitsi-meet'))
}
```

and set your minSdkVersion to be at least 24.

5.) In `android/app/src/main/java/com/xxx/MainApplication.java`

```java
import com.reactnativejitsimeet.RNJitsiMeetPackage;  // <--- Add this line
import android.support.annotation.Nullable; // <--- Add this line if not already existing
...
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        new RNJitsiMeetPackage()                  // <--- Add this line
      );
    }
```


### Side-note

If your app already includes `react-native-locale-detector` or `react-native-vector-icons`, you must exclude them from the `react-native-jitsi-meet` project implementation with the following code (even if you're app uses autolinking with RN > 0.60):

```
    implementation(project(':react-native-jitsi-meet')) {
      exclude group: 'com.facebook.react',module:'react-native-locale-detector'
      exclude group: 'com.facebook.react',module:'react-native-vector-icons'
      // Un-comment below if using hermes
      //exclude group: 'com.facebook',module:'hermes'
      // Un-comment any packages below that you have added to your project to prevent `duplicate_classes` errors
      //exclude group: 'com.facebook.react',module:'react-native-community-async-storage'
      //exclude group: 'com.facebook.react',module:'react-native-community_netinfo'
      //exclude group: 'com.facebook.react',module:'react-native-svg'
      //exclude group: 'com.facebook.react',module:'react-native-fetch-blob'
      //exclude group: 'com.facebook.react',module:'react-native-webview'
      //exclude group: 'com.facebook.react',module:'react-native-linear-gradient'
      //exclude group: 'com.facebook.react',module:'react-native-sound'
    }
```
