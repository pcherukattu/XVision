package com.example.ceaser007.jo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ceaser007 on 6/11/2016.
 */
public class BroadCastReceiver extends BroadcastReceiver {
    public static final String ACTION_SHOWN_NOTIFICATION="com.android.joe.SHOW_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1=new Intent(context,MainActivity.class);
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,0);
     Notification.Builder notification=new Notification.Builder(context).setSmallIcon(R.drawable.close).setContentTitle("Notification").setContentText(intent.getStringExtra("Content")).setContentIntent(pendingIntent);
        context.sendBroadcast(new Intent(ACTION_SHOWN_NOTIFICATION));
        manager.notify(0,notification.build());

    }
}
