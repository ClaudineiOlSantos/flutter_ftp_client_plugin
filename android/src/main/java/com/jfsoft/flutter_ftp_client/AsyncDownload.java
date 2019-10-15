package com.jfsoft.flutter_ftp_client;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        String fullPath = (String) maps[1].get("path") + maps[1].get("fileName") + "." + maps[1].get("extension");
        String filename = maps[1].get("fileName") + "." + maps[1].get("extension");
        String file = maps[1].get("fileName") + "." + maps[1].get("extension");
        String text = "", error = "";

        if (this.ftp.ftpConnect(maps[0])) {

            if (this.ftp.ftpDownload(fullPath, filename, this.context)) {
                text = this.readFile(file);
                return text;
            } else {
                return null;
            }
        } else {
            error = "Não foi possível connectar ao servidor:" + fullPath;
            Log.i(TAG, error);
            return null;
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
            //Log.i("MeuText","");
            while ((linha = bufferedReader.readLine()) != null) {
                text += linha;
                //System.out.println("MEUTXT"+linha+"\n");
            }

        } catch (IOException e) {
            Log.e("ERROR", e.toString());
        }
//        return text;
        return path;
    }
}
