package com.example.wifirestart;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public  class RSSPullService extends Worker {
    WifiManager wManager;
    ConnectivityManager connManager ;
    NetworkInfo mWifi ;

    public RSSPullService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

     //   Log.v("test", "worker created");
         wManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    }


    private void restartWifi(){

      //  Log.v("test", "wifi rr");
        wManager.setWifiEnabled(false);
        wManager.setWifiEnabled(true);
    }
    private boolean checkWifiStatus(){

     //   Log.v("test", "wifi status:"+mWifi.isConnected());
        return mWifi.isConnected() ;
    }



    @NonNull
    @Override
    public Result doWork() {
    //    Log.v("test", "do work");
        if(!checkWifiStatus())
            restartWifi();
        return Result.success();
    }
}