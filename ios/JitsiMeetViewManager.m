#import "React/RCTViewManager.h"

@interface RCT_EXTERN_MODULE(JitsiMeetViewManager, RCTViewManager)
  RCT_EXPORT_VIEW_PROPERTY(options, NSDictionary)
  RCT_EXPORT_VIEW_PROPERTY(onConferenceTerminated, RCTDirectEventBlock)
  RCT_EXPORT_VIEW_PROPERTY(onConferenceJoined, RCTDirectEventBlock)
  RCT_EXPORT_VIEW_PROPERTY(onConferenceWillJoin, RCTDirectEventBlock)
@end
