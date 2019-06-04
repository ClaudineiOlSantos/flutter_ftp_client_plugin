import 'dart:async';

import 'package:flutter/services.dart';

final String hostPropert = "host";
final String usernamePropert = "username";
final String passwordPropert = "password";
final String portPropert = "port";
//Path
final String pathPropert = "path";
final String fileNamePropert = "fileName";
final String extensionfilePropert = "extension";

class FlutterFtpClient {
  static Map<String, String> _hostData;
  static Map<String, String> _path;

  static const MethodChannel _channel =
      const MethodChannel('flutter_ftp_client');

  static Future<String> get getFile async {
    _validateData();

    //Send data to native environment
    final String version = await _channel
        .invokeMethod('getFile', {"host": _hostData, "path": _path});
    return version;
  }

  ///Before attempting to connect to the server, first provide the connection data.
  static void configHost(
      String host, String username, String password, String port) {
    _hostData = {
      hostPropert: host,
      usernamePropert: username,
      passwordPropert: password,
      portPropert: port
    };
  }

  ///Files properties
  static void configFilePath(String path, String filename, String extension) {
    _path = {
      pathPropert: path,
      fileNamePropert: filename,
      extensionfilePropert: extension
    };
  }

  static void _validateData() {
    if (_hostData == null || _path == null) {
      throw new Exception(
          "Configure the server first before attempting to connect.");
    }
  }
}
