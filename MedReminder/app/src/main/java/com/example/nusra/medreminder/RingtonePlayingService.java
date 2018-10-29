package com.example.nusra.medreminder;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Random;

public class RingtonePlayingService extends Service{
    private static final String TAG = RingtonePlayingService.class.getSimpleName();
    private static final String URI_BASE = RingtonePlayingService.class.getName() + ".";
    public static final String ACTION_DISMISS = URI_BASE + "ACTION_DISMISS";


    private Ringtone ringtone;
    private int _uid;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        Log.e("Data", "uid in ringtone "+ String.valueOf(_uid));

        if (intent == null) {
            Log.d(TAG, "The intent is null.");
            return START_REDELIVER_INTENT;
        }

        String action = intent.getAction();

        if (ACTION_DISMISS.equals(action))
            dismissRingtone();
        else {
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            ringtone = RingtoneManager.getRingtone(this, alarmUri);
            ringtone.play();
        }

        return START_NOT_STICKY;
    }

    public void dismissRingtone() {
        // stop the alarm rigntone
        Intent i = new Intent(this, RingtonePlayingService.class);
        i.putExtra("UID",_uid);
        stopService(i);

        // also dismiss the alarm to ring again or trigger again
        AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getBaseContext(), Alarm_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, _uid, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        aManager.cancel(pendingIntent);

        // Canceling the current notification
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.cancel(_uid);
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
        super.onDestroy();
    }
}
