package com.group.dabomb.dabomb;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class AlarmActivity extends Activity {

    private MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);

        Button disarmButton = (Button) findViewById(R.id.disarm_button);
        disarmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sound.stop();
                finish();
            }
        });

        sound = new MediaPlayer();
        RingtoneManager toneManager = new RingtoneManager(this);
        Uri toneUri = toneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        try {
            sound.setDataSource(this, toneUri);
            sound.setAudioStreamType(AudioManager.STREAM_ALARM);
            sound.setLooping(true);
            sound.prepare();
            sound.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
