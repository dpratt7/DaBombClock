package com.group.dabomb.dabomb;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AlarmService extends IntentService {
    private static final String ACTION_ARM = "com.group.dabomb.dabomb.action.ARM";
    private static final String ACTION_DISARM = "com.group.dabomb.dabomb.action.DISARM";
    private static final String EXTRA_TIME = "com.group.dabomb.dabomb.extra.TIME";
    public static final String ACTION_REARM = "com.group.dabomb.dabomb.action.REARM";
    /**
     * Starts this service to perform action Arm with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionArm(Context context, long time) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(ACTION_ARM);
        intent.putExtra(EXTRA_TIME, time);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Disarm. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDisarm(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(ACTION_DISARM);
        context.startService(intent);
    }

    public AlarmService() {

        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ARM.equals(action)) {
                final long time = intent.getLongExtra(EXTRA_TIME, 0);
                handleActionArm(time);
            } else if (ACTION_DISARM.equals(action)) {
                handleActionDisarm();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionArm(long time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        i.putExtra(EXTRA_TIME, time);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, time, pi);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDisarm() {
        // TODO: Handle action Disarm
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createAlarm(){
        //creates the alarm in alarm manager
    }

    /*Recreates alarms from the database*/
    private void recreateAlarms(Intent intent){
        final String action = intent.getAction();
        AlarmDBHelper dbHelper = new AlarmDBHelper(this);
        List<AlarmModel> alarmList = dbHelper.getAlarms();
        if(ACTION_REARM.equals(action)){
            for (int i=0; i <  alarmList.size(); i++){
                alarmList.get(i);
                //handleActionArm();

            }
        }
    }
}
