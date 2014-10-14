package com.group.dabomb.dabomb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Stefan on 10/10/2014.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
    //NEW STUFF HERE
        /**Create a notification**/

        /**Show notification**/
        /**Launch alarmActivity**/
        Intent i = new Intent();
        i.setClassName("com.group.dabomb.dabomb","AlarmActivity.java");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        }

}