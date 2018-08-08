/*#import <React/RCTViewManager.h>
#import <React/RCTLog.h>
#import <JitsiMeet/JitsiMeet.h>

@interface RNJitsiMeetManager : RCTViewManager
@end

@implementation RNJitsiMeetManager {
    JitsiMeetView *jitsiMeetView;
    RCTResponseSenderBlock onConferenceFailed;
    RCTResponseSenderBlock onConferenceJoined;
    RCTResponseSenderBlock onConferenceLeft;
    RCTResponseSenderBlock onConferenceWillJoin;
    RCTResponseSenderBlock onConferenceWillLeave;
    RCTResponseSenderBlock onLoadConfigError;
}

RCT_EXPORT_MODULE()

- (UIView *)view
{
    jitsiMeetView = [[JitsiMeetView alloc] init];
    return jitsiMeetView;
}

- (void)conferenceFailed:(NSDictionary *)data {
    RCTLogInfo(@"Conference failed");
    if (onConferenceFailed != nil) {
      onConferenceFailed(@[data]);
    }
}

- (void)conferenceJoined:(NSDictionary *)data {
    RCTLogInfo(@"Conference joined");
    if (onConferenceJoined != nil) {
      onConferenceJoined(@[data]);
    }
}

- (void)conferenceLeft:(NSDictionary *)data {
    RCTLogInfo(@"Conference left");
    if (onConferenceLeft != nil) {
      onConferenceLeft(@[data]);
    }
}

- (void)conferenceWillJoin:(NSDictionary *)data {
    RCTLogInfo(@"Conference will join");
    if (onConferenceWillJoin != nil) {
      onConferenceWillJoin(@[data]);
    }
}

- (void)conferenceWillLeave:(NSDictionary *)data {
    RCTLogInfo(@"Conference will leave");
    if (onConferenceWillLeave != nil) {
      onConferenceWillLeave(@[data]);
    }
}

- (void)loadConfigError:(NSDictionary *)data {
    RCTLogInfo(@"Config error");
    if (onLoadConfigError != nil) {
      onLoadConfigError(@[data]);
    }
}

RCT_EXPORT_METHOD(loadUrl:(NSString *)urlString)
{
  RCTLogInfo(@"Load URL %@", urlString);
  dispatch_sync(dispatch_get_main_queue(), ^{
      jitsiMeetView.delegate = self;
      [jitsiMeetView loadURLString:urlString];
  });
}

RCT_EXPORT_METHOD(setEventHandler:(NSString *)event callback:(RCTResponseSenderBlock)callback)
{
    if ([event  isEqual: @"CONFERENCE_FAILED"]) {
    onConferenceFailed = callback;
    } else if ([event  isEqual: @"CONFERENCE_JOINED"]) {
    onConferenceJoined = callback;
    } else if ([event  isEqual: @"CONFERENCE_LEFT"]) {
    onConferenceLeft = callback;
    } else if ([event  isEqual: @"CONFERENCE_WILL_JOIN"]) {
    onConferenceWillJoin = callback;
    } else if ([event  isEqual: @"CONFERENCE_WILL_LEAVE"]) {
    onConferenceWillLeave = callback;
    } else if ([event  isEqual: @"LOAD_CONFIG_ERROR"]) {
    onLoadConfigError = callback;
  }
}

RCT_EXPORT_METHOD(removeEventHandler:(NSString *)event)
{
    if ([event  isEqual: @"CONFERENCE_FAILED"]) {
    onConferenceFailed = nil;
    } else if ([event  isEqual: @"CONFERENCE_JOINED"]) {
    onConferenceJoined = nil;
    } else if ([event  isEqual: @"CONFERENCE_LEFT"]) {
    onConferenceLeft = nil;
    } else if ([event  isEqual: @"CONFERENCE_WILL_JOIN"]) {
    onConferenceWillJoin = nil;
    } else if ([event  isEqual: @"CONFERENCE_WILL_LEAVE"]) {
    onConferenceWillLeave = nil;
    } else if ([event  isEqual: @"LOAD_CONFIG_ERROR"]) {
    onLoadConfigError = nil;
  }
}

@end*/
