/**
 * @providesModule JitsiMeet
 */

import { NativeModules, requireNativeComponent } from "react-native";

export const JitsiMeetView = requireNativeComponent("RNJitsiMeetView");
export const JitsiMeetModule = NativeModules.JitsiMeet;

export default JitsiMeetModule;
