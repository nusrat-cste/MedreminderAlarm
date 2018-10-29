package com.example.nusra.medreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Alarm_Receiver extends BroadcastReceiver {
    public static Ringtone ringtone;
    private int _uid;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        // using service class

        try{
            Bundle b = intent.getExtras();
            _uid = b.getInt("UID");
        } catch(Exception ex){
            _uid = 1; //Or some error status //
        }

        Intent i = new Intent(context, RingtonePlayingService.class);
        i.putExtra("UID",_uid);

        Log.e("Data", "uid in receiver "+ String.valueOf(_uid));


        context.startService(i);

        createNotification(context);
    }

    public void createNotification(Context context) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("It's time to take medicine")
                .setContentText("Medicine")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSubText("Tab to dismiss the alarm")
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        //To add a dismiss button
        Intent dismissIntent = new Intent(context, RingtonePlayingService.class);
        dismissIntent.setAction(RingtonePlayingService.ACTION_DISMISS);

        PendingIntent pendingIntent = PendingIntent.getService(context,
                _uid, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action
                (android.R.drawable.ic_lock_idle_alarm, "DISMISS", pendingIntent);
        builder.addAction(action);
        // end of setting action button to notification


        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, _uid, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(_uid, notification);
    }
}
