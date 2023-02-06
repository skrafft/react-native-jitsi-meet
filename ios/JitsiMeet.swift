import UIKit
import JitsiMeetSDK

@objc(JitsiMeet)
class JitsiMeet: NSObject {
    
  var vc: JitsiMeetViewController?
    
  @objc func hangUp() {
     self.vc?.jitsiMeetView.hangUp()
  }
    
  @objc func launchJitsiMeetView(_ options: NSDictionary, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
    DispatchQueue.main.async {
      let rootViewController = UIApplication.shared.delegate?.window??.rootViewController as! UINavigationController
      let _vc = JitsiMeetViewController()
      
      _vc.resolver = resolve
      _vc.modalPresentationStyle = .fullScreen
      _vc.conferenceOptions = JitsiMeetUtil.buildConferenceOptions(options)
                
      rootViewController.pushViewController(_vc, animated: false)
        
        self.vc = _vc
    }
  }

  @objc func launch(_ options: NSDictionary, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
    launchJitsiMeetView(options, resolver: resolve, rejecter: reject)
  }
  
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }
}
