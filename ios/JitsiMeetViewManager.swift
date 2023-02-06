import UIKit
import React
import JitsiMeetSDK

@objc(JitsiMeetViewManager)
class JitsiMeetViewManager: RCTViewManager {
  override func view() -> UIView! {
    return RNJitsiMeetView()
  }
  
  override class func requiresMainQueueSetup() -> Bool {
    return true
  }
}
