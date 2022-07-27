package com.example.android.setalarmapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class App extends Application {
    public static final String CHANNEL_ID = "BROADCAST_CHANNEL";
    NotificationChannel channel;
    NotificationManager manager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, "BR_Chl", NotificationManager.IMPORTANCE_HIGH);
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }
}
