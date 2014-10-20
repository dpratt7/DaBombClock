package com.group.dabomb.dabomb;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View.OnFocusChangeListener;

import java.util.Calendar;


public class Schedule extends Activity implements View.OnClickListener {

    private TimePicker tPicker;
    private DatePicker dPicker;
    private Button cancel;
    private Button submit;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule);

        initButtons();

    }

    private void initButtons() {

        tPicker = (TimePicker) findViewById(R.id.timePicker1);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        submit = (Button) findViewById(R.id.dialog_ok);
        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
        Button button = (Button)findViewById(R.id.monday);
        button.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean gainFocus) {
                if (gainFocus) {
                    System.out.println("monday");
                }
            }
        });
        button = (Button)findViewById(R.id.tuesday);
        button = (Button)findViewById(R.id.wednesday);
        button = (Button)findViewById(R.id.thursday);
        button = (Button)findViewById(R.id.friday);
        button = (Button)findViewById(R.id.saturday);
        button = (Button)findViewById(R.id.sunday);
    }

    public void onClick(View v) {

        switch (v.getId()) {

//If Done button pressed get time selected by user

            case R.id.dialog_cancel:
                Calendar now = Calendar.getInstance();
                tPicker.clearFocus();
                tPicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
                tPicker.setCurrentMinute(now.get(Calendar.MINUTE));
                break;
            case R.id.dialog_ok:
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY,tPicker.getCurrentHour().intValue());
                cal.set(Calendar.MINUTE,tPicker.getCurrentMinute().intValue());
                AlarmService.startActionArm(this, cal.getTimeInMillis());
                break;

            default:
                break;

        }

    }

}
