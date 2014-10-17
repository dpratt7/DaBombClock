package com.group.dabomb.dabomb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Stefan on 10/15/2014.
 */
public class AlarmSetter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Intent setter = new Intent(context,AlarmService.class);
        //setter.setAction((AlarmService.CREATE));
        context.startService(setter);

    }
}
