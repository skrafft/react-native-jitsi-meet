#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(JitsiMeet, NSObject)
RCT_EXTERN_METHOD(hangUp)
RCT_EXTERN_METHOD(launchJitsiMeetView:(NSDictionary)options
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
RCT_EXTERN_METHOD(launch:(NSDictionary)options
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
@end
