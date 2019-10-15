import 'package:flutter_ftp_client/flutter_ftp_client.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  String filename;
  group("", () {
    setUp(() {
      FlutterFtpClient.configHost(
          "192.168.1.116", "claudinei", "5c6h1224", "21");
    });

    test('Verify Platform version', (WidgetTester tester) async {
      try {
        FlutterFtpClient.configFilePath("/", "produtos", "txt");

        filename = await FlutterFtpClient.getFile;
      } on Exception {
        print('Failed to get platform version.');
      }
      expect('produtos.txt', filename);
    });
  });
}
