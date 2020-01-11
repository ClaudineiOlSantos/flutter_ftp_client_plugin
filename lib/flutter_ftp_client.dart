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

  static Future<String> get getFile async {
    _validateData();

    //Send data to native environment
    final String version = await _channel
        .invokeMethod('getFile', {hostPropert: _hostData, pathPropert: _path});
    return version;
  }

  static Future<String> get uploadFile async {
    _validateData();

    //Send data to native environment
    final String version = await _channel
        .invokeMethod('uploadFile', {hostPropert: _hostData, pathPropert: _path});
    return version;
  }

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
  static void configFilePath(String path, String filename, String extension,{uploadPath:String}) {
    _path = {
      pathPropert: path,
      fileNamePropert: filename,
      extensionfilePropert: extension,
      uploadPathPropert:uploadPath
    };
  }

  /// Validação dos dados informador
  static void _validateData() {
    if (_hostData[hostPropert] == null || _path[pathPropert] == null) {
      throw new Exception(
          "Configure the server first before attempting to connect.");
    }
  }
}
