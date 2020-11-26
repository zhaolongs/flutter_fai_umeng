#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'flutter_fai_umeng'
  s.version          = '0.0.2'
  s.summary          = '友盟统计 flutter 插件 '
  s.description      = <<-DESC
友盟统计 flutter 插件 
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.static_framework = true
  s.dependency 'Flutter'
  s.dependency 'UMCCommon', '7.2.2'
  #s.dependency 'UMCAnalytics', '6.0.5'
  s.dependency 'UMCCommonLog', '1.0.0'
  s.dependency 'UMCPush'
  #s.dependency 'UMCSecurityPlugins'
  #s.dependency 'UMCErrorCatch'


  s.ios.deployment_target = '8.0'
end

