#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <React/RCTLog.h>
#import <JitsiMeet/JitsiMeetViewDelegate.h>
#import "JitsiMeetViewController.h"

@interface RNJitsiMeetNavigatorManager : RCTEventEmitter <RCTBridgeModule, JitsiMeetViewDelegate>
@end
