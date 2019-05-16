//
//  ViewController.m
//  JitsiMobile
//
//  Created by Sébastien Krafft on 07/08/2018.
//  Copyright © 2018 Sébastien Krafft. All rights reserved.
//

#import "JitsiMeetViewController.h"

@implementation JitsiMeetViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void) setDelegate:(id<JitsiMeetViewDelegate>) delegate {
    JitsiMeetView *jitsiMeetView = (JitsiMeetView *) self.view;
    if (delegate) {
        jitsiMeetView.delegate = delegate;
    }
}

- (void)loadUrl:(NSString *) url {
    JitsiMeetView *jitsiMeetView = (JitsiMeetView *) self.view;
    JitsiMeetConferenceOptions *options = [JitsiMeetConferenceOptions fromBuilder:^(JitsiMeetConferenceOptionsBuilder *builder) {        builder.room = url;
    }];
    [jitsiMeetView join:options];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
