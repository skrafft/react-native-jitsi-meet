#import <React/RCTComponent.h>

@import JitsiMeetSDK;

@interface RNJitsiMeetView : JitsiMeetView
@property (nonatomic, copy) RCTBubblingEventBlock onConferenceJoined;
@property (nonatomic, copy) RCTBubblingEventBlock onConferenceTerminated;
@property (nonatomic, copy) RCTBubblingEventBlock onConferenceWillJoin;
@property (nonatomic, copy) RCTBubblingEventBlock onEnteredPip;
@end