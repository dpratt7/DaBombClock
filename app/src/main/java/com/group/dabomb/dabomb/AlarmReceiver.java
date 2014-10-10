package com.group.dabomb.dabomb;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Stefan on 10/10/2014.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        long id = intent.getLongExtra("id",0);
        String message = intent.getStringExtra("message");

        Notification notification = new Notification(R.drawable.ic_launcher,message,System.currentTimeMillis());

        PendingIntent pend = PendingIntent.getActivity(context,0,new Intent(),0);

        notification.setLatestEventInfo(context,"Alarm", message, pend);
       // notification.sound

        NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notManager.notify((int)id, notification);
    }

}