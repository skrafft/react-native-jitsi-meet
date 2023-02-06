import UIKit
import JitsiMeetSDK
import React

class RNJitsiMeetView: JitsiMeetView {
  @objc var options: NSDictionary? {
    willSet {
      if let newOptions = newValue {
        joinCall(newOptions)
      }
    }
  }
  
  @objc var onConferenceTerminated: RCTDirectEventBlock?
  @objc var onConferenceJoined: RCTDirectEventBlock?
  @objc var onConferenceWillJoin: RCTDirectEventBlock?
  
  override init(frame: CGRect) {
    super.init(frame: frame)
    
    delegate = self
  }
  
  required init?(coder: NSCoder) {
    super.init(coder: coder)
  }
  
  func joinCall(_ options: NSDictionary) {
    DispatchQueue.main.async {
      self.join(JitsiMeetUtil.buildConferenceOptions(options))
    }
  }
  
  func leaveCall() {
    leave()
    hangUp()
  }
  
  override func removeFromSuperview() {
    leaveCall()
    
    super.removeFromSuperview()
  }
}

extension RNJitsiMeetView: JitsiMeetViewDelegate {
  func conferenceTerminated(_ data: [AnyHashable : Any]!) {
    onConferenceTerminated?(data)
  }
  
  func conferenceJoined(_ data: [AnyHashable : Any]!) {
    onConferenceJoined?(data)
  }
  
  func conferenceWillJoin(_ data: [AnyHashable : Any]!) {
    onConferenceWillJoin?(data)
  }
}
