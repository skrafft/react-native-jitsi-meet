/**
 * @providesModule JitsiMeet
 */

import { NativeModules, requireNativeComponent } from 'react-native';

export const JitsiMeetView = requireNativeComponent('RNJitsiMeetView');
export const JitsiMeetModule = NativeModules.RNJitsiMeetModule
const call = JitsiMeetModule.call;
const audioCall = JitsiMeetModule.audioCall;
JitsiMeetModule.call = (url, userInfo, params) => {
  userInfo = userInfo || {};
  params = params || {};
  call(url, userInfo, params);
}
JitsiMeetModule.audioCall = (url, userInfo, params) => {
  userInfo = userInfo || {};
  params = params || {};
  audioCall(url, userInfo, params);
}
export default JitsiMeetModule;


