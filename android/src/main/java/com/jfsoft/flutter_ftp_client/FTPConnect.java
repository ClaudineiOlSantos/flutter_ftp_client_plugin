package com.jfsoft.flutter_ftp_client;

import android.content.Context;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class FTPConnect {
    private static final String TAG = "FTPConnect";
    public FTPClient FTPClient = null;


    /**
     * Classe responsável por fazer a conecxão com o servidor:
     *
     * Disponibiliza alguns metodos para manipulação de arquivos no servidor como:
     * Upload, download, remoção de arquivo, renomeação, escolha de diretório de trabalho, criação de
     * diretório entre outros.
     * 
     * @param host Map<String,String>
     * @return
     */
    public boolean ftpConnect(Map host) {
        String hostLink = (String) host.get("host");
        int hostPort = Integer.parseInt((String) host.get("port"));

        try {
            FTPClient = new FTPClient();

            // connecting to the host
            FTPClient.connect(hostLink, hostPort);

            // now check the reply code, if positive mean connection success
            if (FTPReply.isPositiveCompletion(FTPClient.getReplyCode())) {
                // login using username & password
                boolean status = FTPClient.login((String) host.get("username"), (String) host.get("password"));

                /*
                 * Set File Transfer Mode
                 *
                 * To avoid corruption issue you must specified a correct
                 * transfer mode, such as ASCII_FILE_TYPE, BINARY_FILE_TYPE,
                 * EBCDIC_FILE_TYPE .etc. Here, I use BINARY_FILE_TYPE for
                 * transferring text, image, and compressed files.
                 */
                FTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                FTPClient.enterLocalPassiveMode();

                return status;
            }
        } catch (Exception e) {
            Log.d(TAG, "Error: could not connect to host: " + host);
        }

        return false;
    }

    /**
     * Desconecta do servidor:
     */
    public boolean ftpDisconnect() {
        try {
            FTPClient.logout();
            FTPClient.disconnect();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error occurred while disconnecting from ftp server.");
        }

        return false;
    }

    /**
     * Retorna o diretório atual
     *
     * @return String
     */

    public String ftpGetCurrentWorkingDirectory() {
        try {
            String workingDir = FTPClient.printWorkingDirectory();
            return workingDir;
        } catch (Exception e) {
            Log.d(TAG, "Error: could not get current working directory.");
        }

        return null;
    }

    /**
     * Escolhe o diretório atual
     *
     * @param directory_path String
     * @return
     */

    public boolean ftpChangeDirectory(String directory_path) {
        try {
            FTPClient.changeWorkingDirectory(directory_path);
        } catch (Exception e) {
            Log.d(TAG, "Error: could not change directory to " + directory_path);
        }

        return false;
    }

    /**
     * Obtém a lista completa de arquivos presente no diretório
     *
     * @param dir_path String
     * @return String[]
     */

    public String[] ftpPrintFilesList(String dir_path) {
        String[] fileList = null;
        try {
            FTPFile[] ftpFiles = FTPClient.listFiles(dir_path);
            int length = ftpFiles.length;
            fileList = new String[length];
            for (int i = 0; i < length; i++) {
                String name = ftpFiles[i].getName();
                boolean isFile = ftpFiles[i].isFile();

                if (isFile) {
                    fileList[i] = "File :: " + name;
                    Log.i(TAG, "File : " + name);
                } else {
                    fileList[i] = "Directory :: " + name;
                    Log.i(TAG, "Directory : " + name);
                }
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
            return fileList;
        }
    }

    /**
     * Cria um diretório:
     *
     * @param new_dir_path
     * @return String
     */

    public boolean ftpMakeDirectory(String new_dir_path) {
        try {
            boolean status = FTPClient.makeDirectory(new_dir_path);
            return status;
        } catch (Exception e) {
            Log.d(TAG, "Error: could not create new directory named "
                    + new_dir_path);
        }

        return false;
    }

    /**
     * Remove um diretório
     *
     * @param dir_path
     * @return String
     */

    public boolean ftpRemoveDirectory(String dir_path) {
        try {
            boolean status = FTPClient.removeDirectory(dir_path);
            return status;
        } catch (Exception e) {
            Log.d(TAG, "Error: could not remove directory named " + dir_path);
        }

        return false;
    }

    /**
     * Metodo para remover um arquivo
     *
     * @param filePath String
     * @return boolean
     */

    public boolean ftpRemoveFile(String filePath) {
        try {
            boolean status = FTPClient.deleteFile(filePath);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Metodo para renomear um rquivo
     *
     * @param from String
     * @param to   String
     * @return boolean
     */

    public boolean ftpRenameFile(String from, String to) {
        try {
            boolean status = FTPClient.rename(from, to);
            return status;
        } catch (Exception e) {
            Log.d(TAG, "Could not rename file: " + from + " to: " + to);
        }

        return false;
    }

    // Method to download a file from FTP server:

    /**
     * FTPClient: FTP client connection object (see FTP connection example)
     * srcFilePath: path to the source file in FTP server desFilePath: path to
     * the destination file to be saved in sdcard
     *
     * @param srcFilePath Full Oringn from file
     * @param desFilePath File destination
     * @param context     Application context
     * @return boolean
     */
    public boolean ftpDownload(String srcFilePath, String desFilePath, Context context) {
        boolean status = false;
        try {
            //FileOutputStream desFileStream = new FileOutputStream(Environment.getExternalStorageDirectory());
            FileOutputStream desFileStream = context.openFileOutput(desFilePath, Context.MODE_PRIVATE);

            status = FTPClient.retrieveFile(srcFilePath, desFileStream);
            desFileStream.close();

            return status;
        } catch (Exception e) {
            Log.d(TAG, "download failed");
        }

        return status;
    }

    // Method to upload a file to FTP server:

    /**
     * FTPClient: FTP client connection object (see FTP connection example)
     * srcFilePath: source file path in sdcard desFileName: file name to be
     * stored in FTP server desDirectory: directory path where the file should
     * be upload to
     *
     * @param srcFilePath  String
     * @param desFileName  String
     * @param context      Context
     * @return boolean
     */

    public boolean ftpUpload(String srcFilePath, String desFileName,
                             Context context) {
        boolean status = false;
        try {
            FileInputStream srcFileStream = new FileInputStream(srcFilePath);

            // change working directory to the destination directory
            // if (ftpChangeDirectory(desDirectory)) {
            status = FTPClient.storeFile(desFileName, srcFileStream);
            // }

            srcFileStream.close();

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "upload failed: " + e);
        }

        return status;
    }


}