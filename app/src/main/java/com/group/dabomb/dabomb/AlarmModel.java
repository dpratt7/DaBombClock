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

    public long id;
    public int timeHour;
    public int timeMinute;
    private boolean repeatingDays[];
    public boolean repeatWeekly;
    public Uri alarmTone;
    public String name;
    public boolean isEnabled;

    public AlarmModel(Calendar c) {
        timeHour = c.get(Calendar.HOUR_OF_DAY);
        timeMinute = c.get(Calendar.MINUTE);
        repeatingDays = new boolean[7];
    }

    public AlarmModel() {
        repeatingDays = new boolean[7];
    }

    public void setRepeatingDay(int dayOfWeek, boolean value) {
        repeatingDays[dayOfWeek] = value;
    }

    public boolean getRepeatingDay(int dayOfWeek) {
        return repeatingDays[dayOfWeek];
    }

}