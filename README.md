# Flutter FTP Client

This plugin provide an simple API to FTP access and manager files, Common-net-3.6 based.
Currently only available for android platform.

## Getting Started

### Config host

First provide the host config

```
  FlutterFtpClient.configHost(host, username, password, port);
```

### Config path

Config the path and filename from download.
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

The method uploadFile execute the upload of file to server.

```
    // Set destination path
    configFilePath(path, filename,
            extension: extension, uploadPath: uploadPath);

    /// Execute o upload file
    return FlutterFtpClient.uploadFile;
```