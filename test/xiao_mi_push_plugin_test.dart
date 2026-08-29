import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:xiao_mi_push_plugin/xiao_mi_push_plugin.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  const MethodChannel channel = MethodChannel('xiao_mi_push_plugin');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(channel, (MethodCall call) async {
          return switch (call.method) {
            'getRegId' => 'reg-id',
            'getSdkVersion' => '7_12_4-C',
            _ => null,
          };
        });
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(channel, null);
  });

  test('gets the MiPush registration ID', () async {
    expect(await XiaoMiPushPlugin.getRegId(), 'reg-id');
  });

  test('gets the MiPush SDK version', () async {
    expect(await XiaoMiPushPlugin.getSdkVersion(), '7_12_4-C');
  });
}
