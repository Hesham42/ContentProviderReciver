package com.example.contentproviderproject;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import static com.example.contentproviderproject.App.CHANEL_1_ID;


public class MyBroadcastReceiver extends BroadcastReceiver
{
    //app notifcation
    Intent activeIntent;
    PendingIntent pendingIntent;
    Notification notification;
    NotificationManagerCompat managerCompat;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String data = intent.getStringExtra("data");
        managerCompat = NotificationManagerCompat.from(context);
        Toast.makeText(context,"Created Contact " + data,Toast.LENGTH_LONG).show();
        activeIntent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(
                context,
                0,
                activeIntent,
                0);

        notification = new
                NotificationCompat.Builder(context, CHANEL_1_ID)
                .setSmallIcon(R.drawable.notifaction_24dp)
                .setContentTitle(data)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        managerCompat.notify(1, notification);

    }
}


