package com.group.dabomb.dabomb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.List;
import java.util.ArrayList;

import static com.group.dabomb.dabomb.AlarmContract.*;

/**
 * Created by Stefano on 10/17/2014.
 */
public class AlarmDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarmclock.db";

    private static final String SQL_CREATE_ALARM =
            "CREATE TABLE " + Alarm.TABLE_NAME + " (" +
                    Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Alarm.COLUMN_NAME_ALARM_TIME + " LONG )";

    private static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXISTS " + Alarm.TABLE_NAME;

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALARM);
        onCreate(db);
    }

    private AlarmModel populateModel(Cursor c) {
        AlarmModel model = new AlarmModel();
        model.id = c.getLong(c.getColumnIndex(Alarm._ID));
        model.time = c.getLong(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME));

        return model;
    }

    private ContentValues populateContent(AlarmModel model) {
        ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_ALARM_TIME, model.time);
        return values;
    }

    public long createAlarm(AlarmModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().insert(Alarm.TABLE_NAME, null, values);
    }

    public AlarmModel getAlarm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME + " WHERE " + Alarm._ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateModel(c);
        }

        return null;
    }

    public long updateAlarm(AlarmModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().update(Alarm.TABLE_NAME, values, Alarm._ID + " = ?", new String[] { String.valueOf(model.id) });
    }

    public int deleteAlarm(long id) {
        return getWritableDatabase().delete(Alarm.TABLE_NAME, Alarm._ID + " = ?", new String[] { String.valueOf(id) });
    }

    public List<AlarmModel> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME;

        Cursor c = db.rawQuery(select, null);

        List<AlarmModel> alarmList = new ArrayList<AlarmModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }
}