#import "RNJitsiMeetNavigator.h"

@implementation RNJitsiMeetNavigatorManager {
    JitsiMeetViewController *jitsiMeetViewController;
}

RCT_EXPORT_MODULE()

- (void)conferenceFailed:(NSDictionary *)data {
    RCTLogInfo(@"Conference failed");
    [self sendEventWithName:@"CONFERENCE_FAILED" body:data];
}

- (void)conferenceJoined:(NSDictionary *)data {
    RCTLogInfo(@"Conference joined");
    [self sendEventWithName:@"CONFERENCE_JOINED" body:data];
}

- (void)conferenceLeft:(NSDictionary *)data {
    RCTLogInfo(@"Conference left");
    UIViewController* rootViewController = [[[[UIApplication sharedApplication]delegate] window] rootViewController];
    UINavigationController *navigationController = (UINavigationController *) rootViewController;
    [navigationController popViewControllerAnimated:YES];
    [self sendEventWithName:@"CONFERENCE_LEFT" body:data];
}

- (void)conferenceWillJoin:(NSDictionary *)data {
    RCTLogInfo(@"Conference will join");
    [self sendEventWithName:@"CONFERENCE_WILL_JOIN" body:data];
}

- (void)conferenceWillLeave:(NSDictionary *)data {
    RCTLogInfo(@"Conference will leave");
    [self sendEventWithName:@"CONFERENCE_WILL_LEAVE" body:data];
}

- (void)loadConfigError:(NSDictionary *)data {
    RCTLogInfo(@"Config error");
    [self sendEventWithName:@"LOAD_CONFIG_ERROR" body:data];
}

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"CONFERENCE_FAILED", @"CONFERENCE_JOINED", @"CONFERENCE_LEFT", @"CONFERENCE_WILL_JOIN", @"CONFERENCE_WILL_LEAVE", @"LOAD_CONFIG_ERROR"];
}

RCT_EXPORT_METHOD(initialize)
{
    RCTLogInfo(@"Initialize");
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"JitsiMeet" bundle:nil];
    jitsiMeetViewController = [storyboard instantiateViewControllerWithIdentifier:@"jitsiMeetStoryBoardID"];
}

RCT_EXPORT_METHOD(call:(NSString *)urlString)
{
    RCTLogInfo(@"Load URL %@", urlString);
    dispatch_sync(dispatch_get_main_queue(), ^{
        UIViewController* rootViewController = [[[[UIApplication sharedApplication]delegate] window] rootViewController];
        UINavigationController *navigationController = (UINavigationController *) rootViewController;
        [navigationController pushViewController:jitsiMeetViewController animated:true];
        [jitsiMeetViewController setDelegate:self];
        [jitsiMeetViewController loadUrl:urlString];
    });
}

@end
