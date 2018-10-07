package com.example.nusra.medreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("Alarm","at the receiver");
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        context.startService(service_intent);
        Log.e("Alarm","service started");
    }
}
