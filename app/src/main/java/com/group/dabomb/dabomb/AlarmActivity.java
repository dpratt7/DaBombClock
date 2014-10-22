package com.group.dabomb.dabomb;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


public class AlarmActivity extends Activity {

    private CountDownTimer countdown;
    private TextView countdownText;
    private AudioManager audioManager;
    private MediaPlayer sound;
    private int currentVolume;
    private int stepVolume;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_alarm );

        countdownText = (TextView) findViewById( R.id.countdown );

        Button disarmButton = (Button) findViewById( R.id.disarm_button );
        disarmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sound.stop();
                countdown.cancel();
                finish();
            }
        });

        startSound();
        startCountdown();
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.alarm, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if ( id == R.id.action_settings ) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void startSound() {
        audioManager = (AudioManager) getSystemService( Context.AUDIO_SERVICE );
        currentVolume = audioManager.getStreamMaxVolume( AudioManager.STREAM_ALARM ) / 2;
        stepVolume = Math.max( 1, currentVolume / 4 );
        audioManager.setStreamVolume( AudioManager.STREAM_ALARM, currentVolume, AudioManager.FLAG_PLAY_SOUND );
        System.out.println( "Volume set to " + audioManager.getStreamVolume( AudioManager.STREAM_ALARM ) );

        sound = new MediaPlayer();
        sound.setAudioStreamType( AudioManager.STREAM_ALARM );
        try {
            Resources res = getResources();
            AssetFileDescriptor afd = res.openRawResourceFd( R.raw.bomb_alert_1 );
            sound.setDataSource( afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength() );
            sound.setLooping(true);
            sound.prepare();
            sound.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startCountdown() {
        countdown = new CountDownTimer( 10000, 1000 ) {

            public void onTick( long millisUntilFinished ) {
                int seconds = (int) millisUntilFinished / 1000;
                countdownText.setText( String.format( "%d:%d", seconds/60, seconds%60 ) );
            }

            public void onFinish() {
                countdownText.setText( "BOOM!" );
                audioManager.setStreamVolume( AudioManager.STREAM_ALARM, currentVolume += stepVolume, AudioManager.FLAG_PLAY_SOUND );
                System.out.println( "Volume set to " + audioManager.getStreamVolume( AudioManager.STREAM_ALARM ) );
                countdown.start();
            }
        };
        countdown.start();
    }
}
