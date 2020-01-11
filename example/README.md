# Flutter FTP Client Example

Demonstrates how to use the flutter_ftp_client plugin.

## Getting Started

* 1 - Provide the host credentials.
* 2 - Provide the file path and extention.
* 3 - Download or upload file using FlutterFtpClient.getFile and FlutterFtpClient.uploadFile methods.

### Download demonstration

Demonstrates how to download files using Flutter FTP Client.

```
    /// set credentials
    FlutterFtpClient.configHost("111.111.1.111", "user", "password", "21");

    /// set file path and extention.
    FlutterFtpClient.configFilePath("/", "products","txt");

    /// download file and get the filename
    filename = await FlutterFtpClient.getFile;
```

### Upload demonstration

Demonstrates how to upload files using Flutter FTP Client.

```
    /// set credentials.
    FlutterFtpClient.configHost("111.111.1.111", "user", "password", "21");

    /// set file path and extention.
    FlutterFtpClient.configFilePath("/", "order", "txt", uploadPath: "/destineFolder");

    /// upload file.
    filename = await FlutterFtpClient.uploadFile;
```

For more information access [Flutter FTP Client on GitHub](https://github.com/ClaudineiOlSantos/flutter_ftp_client_plugin)
