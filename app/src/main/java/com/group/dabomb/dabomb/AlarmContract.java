package com.group.dabomb.dabomb;

/**
 * Created by Stefano on 10/17/2014.
 */
import android.provider.BaseColumns;

public final class AlarmContract {

    public AlarmContract() {}

    public static abstract class Alarm implements BaseColumns {
        public static final String TABLE_NAME = "alarm";
        public static final String COLUMN_NAME_ALARM_TIME = "time";
    }

}