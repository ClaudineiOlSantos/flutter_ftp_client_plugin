package com.jfsoft.flutter_ftp_client;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Map;

/**
 * Classe responsável por executar o upload do arquivo
 *
 * Esta classe herda da classe AsyncTask para execução de em background
 */
public class AsyncUpload extends AsyncTask<Map, Integer, String> {
    private FTPConnect ftp;
    private Context context;
    private String TAG = "FTPConnect";

    AsyncUpload(FTPConnect ftpConnect, Context context) {
        this.ftp = ftpConnect;
        this.context = context;
    }

    /**
     * @param maps varags  map[0] = host and map[1]=path
     * @return String
     */
    @Override
    protected String doInBackground(Map... maps) {
        String fullPath = (String) maps[1].get("path") + maps[1].get("fileName") + "." + maps[1].get("extension");
        String filename = maps[1].get("fileName") + "." + maps[1].get("extension");
//        String file = maps[1].get("fileName") + "." + maps[1].get("extension");

        if (this.ftp.ftpConnect(maps[0])) {


            if (this.ftp.ftpUpload(fullPath, filename, this.context)) {
                return "Upload executado com sucesso";
            } else {
                return null;
            }
        } else {
            return  "Não foi possível connectar ao servidor:" + fullPath;
        }
    }



}
