/**
 * @providesModule JitsiMeet
 */

import { NativeModules, requireNativeComponent } from "react-native";

export const JitsiMeetView = requireNativeComponent("JitsiMeetView");
export const JitsiMeetModule = NativeModules.JitsiMeetModule;

export default JitsiMeetModule;
