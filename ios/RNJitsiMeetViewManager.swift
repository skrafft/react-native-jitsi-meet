import UIKit
import React
import JitsiMeetSDK

@objc(RNJitsiMeetViewManager)
class RNJitsiMeetViewManager: RCTViewManager {
  override func view() -> UIView! {
    return RNJitsiMeetView()
  }
  
  override class func requiresMainQueueSetup() -> Bool {
    return true
  }
}
