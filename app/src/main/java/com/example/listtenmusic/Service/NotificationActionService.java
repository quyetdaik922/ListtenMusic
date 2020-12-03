package com.example.listtenmusic.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationActionService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent("TRACKS_TRACKS")
        .putExtra("actionname", intent.getAction()));
        Log.d("BBB", "onReceive: ");
    }
}