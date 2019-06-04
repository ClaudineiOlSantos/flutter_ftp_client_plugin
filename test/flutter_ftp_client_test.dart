import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_ftp_client/flutter_ftp_client.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_ftp_client');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await FlutterFtpClient.getFile, '42');
  });
}
