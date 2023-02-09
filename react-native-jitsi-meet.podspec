require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = package['name']
  s.version      = package['version']
  s.summary      = package['description']
  s.license      = package['license']

  s.authors      = package['author']
  s.homepage     = package['homepage']
  s.platform     = :ios, "14.4"

  s.source       = { :git => "https://github.com/narvis2/react-native-jitsi-meet-example.git", :tag => "#{s.version}" }
  s.source_files = "ios/**/*.{h,m,mm,swift}"

  s.dependency 'React'
  s.dependency 'CustomJitsiMeetSDK', '0.0.7'
end
