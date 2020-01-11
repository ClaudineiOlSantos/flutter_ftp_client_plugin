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
final String uploadPathPropert = "uploadPath";

class FlutterFtpClient {
  static Map<String, String> _hostData;
  static Map<String, String> _path;

  static const MethodChannel _channel =
      const MethodChannel('flutter_ftp_client');

  /// Antes de tentar se conectar ao servidor forneça os dados de conexão
  /// [host] Servidor
  /// [username] Usuário
  /// [password] Senha
  /// [port] Porta
  static void configHost(
      String host, String username, String password, String port) {
    _hostData = {
      hostPropert: host,
      usernamePropert: username,
      passwordPropert: password,
      portPropert: port
    };
  }

  /// Forneça as configurações do arquivo
  /// [path] Caminho do arquivo
  /// [filename] Nome
  /// [extension] Extensão ex: txt
  static void configFilePath(String path, String filename, String extension,
      {uploadPath: String}) {
    _path = {
      pathPropert: path,
      fileNamePropert: filename,
      extensionfilePropert: extension,
      uploadPathPropert: uploadPath
    };
  }

  /// Validate host credentials
  static void _validateData() {
    if (_hostData[hostPropert] == null || _path[pathPropert] == null) {
      throw new Exception(
          "Configure the server first before attempting to connect.");
    }
  }

  /// Execute de download file from server
  /// Before attempting to download files please configure the server using
  /// the [configHost] method and then configure the filePath using the [configFilePath] method.
  static Future<String> get getFile async {
    _validateData();

    /// Send data to native environment
    final String filename = await _channel
        .invokeMethod('getFile', {hostPropert: _hostData, pathPropert: _path});

    /// Returns the file name downloaded from the server.
    return filename;
  }

  /// Execute the upload file to server
  ///
  /// Execute de download file from server
  /// Before attempting to download files please configure the server using
  /// the [configHost] method and then configure the filePath using the [configFilePath] method.
  static Future<String> get uploadFile async {
    _validateData();

    /// Send data to native environment
    final String filename = await _channel.invokeMethod(
        'uploadFile', {hostPropert: _hostData, pathPropert: _path});

    /// Returns the file name uploaded from the server.
    return filename;
  }
}
