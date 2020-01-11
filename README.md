# flutter_ftp_client

This plugin provide an simple API to FTP acess, Common-net-3.6 based.
Currently only available for android platform.

## Getting Started

In your pubspac file

````
dependencies:
  flutter:
    sdk: flutter
  flutter_ftp_client:
    path: ..\flutter_ftp_client #Path to pub.dev
```

### Config host

First to try connect, provide host 
Antes
```
  FlutterFtpClient.configHost(host, username, password, port);
```

### Config path

```
FlutterFtpClient.configFilePath(path, filename, extension,
        uploadPath: uploadPath);
```

### Download File

The method getFile execute the download of file and return your filename.
Use the filename to manipulate the file in your app.

```
   String filename = await FlutterFtpClient.getFile;
```

### Upload File

The method getFile execute the upload of file.
Use the filename to manipulate the file in your app.

```
    // Set destination path
    configFilePath(path, filename,
            extension: extension, uploadPath: uploadPath);

    /// Execute o upload file
    return FlutterFtpClient.uploadFile;
```