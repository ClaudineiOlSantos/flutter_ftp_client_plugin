package com.jfsoft.flutter_ftp_client;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterFtpClientPlugin
 */
public class FlutterFtpClientPlugin implements MethodCallHandler, Application.ActivityLifecycleCallbacks {
    private static final String CHANNEL = "com.jfsoft.flutter_ftp_client";
    private static final String TAG = "FTPConnect";

    private MethodChannel flutterChannel;
    private Context context;
    private Map<String, String> _host = new HashMap<>();
    private Map<String, String> _path = new HashMap<>();

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_ftp_client");
        channel.setMethodCallHandler(new FlutterFtpClientPlugin(registrar.context(), registrar.messenger()));
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {

        if (call.method.equals("getFile")) {
            //Get host data and path data from flutter application.
            _host = call.argument("host");
            _path = call.argument("path");

            //Connect to FTP server and download file.
            FTPConnect ftpConnect = new FTPConnect();
            AsyncDownload asyncDownload = new AsyncDownload(ftpConnect, this.context);
            asyncDownload.execute(_host, _path);

            String resultado = "Executou só n sei pegar o resultado;";
            result.success(resultado);
        } else if (call.method.equals("uploadFile")) {
            //TODO
        } else {
            result.notImplemented();
        }
    }

    private FlutterFtpClientPlugin(Context context, BinaryMessenger messenger) {
        this.context = context;
        flutterChannel = new MethodChannel(messenger, CHANNEL);
        flutterChannel.setMethodCallHandler(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
