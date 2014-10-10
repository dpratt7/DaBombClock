package com.group.dabomb.dabomb;

import android.content.BroadcastReceiver;
import android.content.Context;

/**
 * Created by Stefan on 10/10/2014.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        long id = intent.getLongExtra("id",0);
        String message = intent.getStringExtrea("message");

        Notification notification = new Notification(R.drawable.ic_launcher,message,System.currentTimeMillis());

        PendingIntent pend = PendingIntent.getActivity(context,0,new Intent(),0);

        notification.setLatestEventInfo(context,"Alarm", message, pend);
        notification.sound = Uri.parse(RemindMe.getRingtone());

        NotificationManager notManager = (NotificationManager) context.getSystemSerivce(Context.NOTIFICATION_SERVICE);

        notManager.notify((int)id, notification);
    }

}