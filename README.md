# react-native-jitsi-meet
React native wrapper for Jitsi Meet SDK

## Install

`npm install react-native-jitsi-meet --save` 

## Use

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
- CONFERENCE_FAILED
- CONFERENCE_LEFT
- CONFERENCE_WILL_JOIN
- CONFERENCE_WILL_LEAVE
- LOAD_CONFIG_ERROR

## iOS Manual Install
### Step 1. Add Files Into Project
1-1.) in Xcode: Right click `Libraries` ➜ `Add Files to [project]`  
1-2.) choose `node_modules/react-native-jitsi-meet/ios/RNJitsiMeet.xcodeproj` then `Add`  
1-3.) add `node_modules/react-native-jitsi-meet/ios/WebRTC.framework` and `node_modules/react-native-jitsi-meet/ios/JitsiMeet.framework` to the Frameworks folder
1-4.) add `node_modules/react-native-jitsi-meet/ios/JitsiMeet.storyboard` in the same folder as AppDelegate.m

### Step 2. Add Library Search Path

2-1.) select `Build Settings`, find `Search Paths`  
2-2.) edit BOTH `Framework Search Paths` and `Library Search Paths`  
2-3.) add path on BOTH sections with: `$(SRCROOT)/../node_modules/react-native-jitsi-meet/ios` with `recursive`  

## Step 3. Change General Setting and Embed Framework

3-1.) go to `General` tab  
3-2.) change `Deployment Target` to `8.0`  
3-3.) add `WebRTC.framework` and `JitsiMeet.framework` in `Embedded Binaries` 

## Step 4. Link/Include Necessary Libraries

4-1.) click `Build Phases` tab, open `Link Binary With Libraries`  
4-2.) add `libRNJitsiMeet.a`  
4-3.) make sure `WebRTC.framework` and `JitsiMeet.framework` linked  
4-4.) add the following libraries:  

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
```

4-5.) Under `Build setting` set `Dead Code Stripping` to `No`, set `Enable Bitcode` to `No` and `Always Embed Swift Standard Libraries` to `Yes`

## Step 5. Add Permissions

5-1.) navigate to `<ProjectFolder>/ios/<ProjectName>/`  
5-2.) edit `Info.plist` and add the following lines

```
<key>NSCameraUsageDescription</key>
<string>Camera Permission</string>
<key>NSMicrophoneUsageDescription</key>
<string>Microphone Permission</string>
```
5-3.) in `Info.plist`, make sure that 
```
<key>UIBackgroundModes</key>
<array>
</array>
```
contains `<string>voip</string>`

## Android Manual Install

1.) In `android/app/src/main/AndroidManifest.xml` add these permissions

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus"/>

<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<permission android:name="com.reactnativejitsimeet.permission.JITSI_BROADCAST"
    android:label="Jitsi Meet Event Broadcast"
    android:protectionLevel="normal"></permission>
<uses-permission android:name="com.reactnativejitsimeet.permission.JITSI_BROADCAST"/>
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
dependencies {
  ...
    implementation(project(':react-native-jitsi-meet'))
}

```
5.) In `android/build.gradle`, add the following code 
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

6.) In `android/app/src/main/java/com/xxx/MainApplication.java`

```java
import com.reactnativejitsimeet.JitsiMeetPackage;  // <--- Add this line
...
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        new JitsiMeetPackage()                  // <--- Add this line
      );
    }
```

### Side-note

If your app already includes `react-native-locale-detector` or `react-native-vector-icons`, you must exclude them from the `react-native-jitsi-meet` project implementation with the following code:

```
    implementation(project(':react-native-jitsi-meet')) {
      exclude group: 'com.facebook.react',module:'react-native-locale-detector'
      exclude group: 'com.facebook.react',module:'react-native-vector-icons'
    }
```
