package com.example.wifirestart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import android.widget.Toast;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    RSSPullService intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "on create main activity", Toast.LENGTH_SHORT).show();
        //Log.v("test", "main activity created");
        PeriodicWorkRequest periodicSyncDataWork =  new PeriodicWorkRequest.Builder(RSSPullService.class, 15, TimeUnit.MINUTES)
                 // setting a backoff on case the work needs to retry
                .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build();
        WorkManager mWorkManager = WorkManager.getInstance(this);
        mWorkManager.enqueueUniquePeriodicWork(
                "check wifi",
                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                periodicSyncDataWork //work request
        );
    }


}

