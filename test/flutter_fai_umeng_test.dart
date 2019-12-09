import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_fai_umeng/flutter_fai_umeng.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_fai_umeng');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    FlutterFaiUmeng.uMengInit("");
  });
}
