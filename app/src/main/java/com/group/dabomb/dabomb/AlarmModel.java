package com.group.dabomb.dabomb;

/**
 * Created by Stefano on 10/17/2014.
 */
import android.net.Uri;
import java.util.Calendar;
import java.util.Date;


public class AlarmModel {

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    public long time;
    public long id;

    public AlarmModel(){}

    public AlarmModel(Calendar c) {
        time = c.getTimeInMillis();
    }

    public AlarmModel(long millis){
        this.time = time;
    }

}