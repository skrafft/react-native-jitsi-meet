import React, {useEffect} from 'react';
import JitsiMeet, {JitsiMeetView} from 'react-native-jitsi-meet';
import {UIManager} from 'react-native';
function App() {
  // UIManager.playTouchSound = () => { }

  let options = {};
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
  };
  useEffect(() => {
    const url = 'https://vchat.scholars-home.org/myc';
    const userInfo = {
      displayName: 'User',
      email: 'user@example.com',
      avatar: 'https:/gravatar.com/avatar/abc123',
    };
    JitsiMeet.call(url, userInfo, options, meetFeatureFlags);
    return () => {
      JitsiMeet.endCall();
    };
  }, []);

  function onConferenceTerminated(nativeEvent) {
    /* Conference terminated event */
    console.log(nativeEvent);
  }

  function onConferenceJoined(nativeEvent) {
    /* Conference joined event */
    console.log(nativeEvent);
  }

  function onConferenceWillJoin(nativeEvent) {
    /* Conference will join event */
    console.log(nativeEvent);
  }
  return (
    <JitsiMeetView
      onConferenceTerminated={e => onConferenceTerminated(e)}
      onConferenceJoined={e => onConferenceJoined(e)}
      onConferenceWillJoin={e => onConferenceWillJoin(e)}
      style={{
        flex: 1,
        height: '100%',
        width: '100%',
      }}
    />
  );
}
export default App;
