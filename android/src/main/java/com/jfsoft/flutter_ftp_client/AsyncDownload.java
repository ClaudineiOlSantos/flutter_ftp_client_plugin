package com.jfsoft.flutter_ftp_client;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class AsyncDownload extends AsyncTask<Map, Integer, String> {
    private FTPConnect ftp;
    private Context context;
    private String TAG = "FTPConnect";

    AsyncDownload(FTPConnect ftpConnect, Context context) {
        this.ftp = ftpConnect;
        this.context = context;
    }

    /**
     * @param maps varags  map[0] = host and map[1]=path
     * @return
     */
    @Override
    protected String doInBackground(Map... maps) {
        String error = "";
        String fullPath = (String) maps[1].get("path") + maps[1].get("fileName") + "." + maps[1].get("extension");
        String filename = (String) maps[1].get("fileName") + "." + maps[1].get("extension");
        String file = (String) maps[1].get("fileName") + "." + maps[1].get("extension");
        String text = "";
        if (this.ftp.ftpConnect(maps[0], maps[1])) {
//            String[] lista = this.ftp.ftpPrintFilesList("/");
//            for (String t : lista) {
//                Log.i(TAG, t);
//            }

            //String s = new File("produtos").getAbsolutePath();
            if (this.ftp.ftpDownload(fullPath, filename, this.context)) {
                text = this.readFile(file);
                Log.i(TAG, "Resultado da leitura:  " + text);
                return text;
            } else {
                return null;
            }
        } else {
            String path = (String) maps[1].get("path") + maps[1].get("fileName") + "." + maps[1].get("extension");
            error = "Não foi possível connectar ao servidor" + path;
            Log.i(TAG, error);
            return error;
        }
    }

    /**
     * Efetua a leitura do arquivo baixado.
     *
     * @param path String
     * @return String
     */
    private String readFile(String path) {
        String text = "";
        try {

            FileInputStream fileInputStream = context.openFileInput(path);

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha = "";

            while ((linha = bufferedReader.readLine()) != null) {
                text += linha;
            }
        } catch (IOException e) {
            Log.e("ERROR", e.toString());
        }
        return text;
    }
}
