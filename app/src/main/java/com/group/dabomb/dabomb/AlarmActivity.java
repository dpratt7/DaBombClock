package com.group.dabomb.dabomb;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;


public class AlarmActivity extends Activity {

    private CountDownTimer countdown;
    private TextView countdownText;
    private ImageView wires;
    private AudioManager audioManager;
    private MediaPlayer sound;
    private int currentVolume;
    private int stepVolume;
    private int wire;
    private int wireLeftBound;
    private int wireRightBound;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_alarm );

        countdownText = (TextView) findViewById( R.id.countdown );
        wires = (ImageView) findViewById( R.id.wires );
        TextView prompt = (TextView) findViewById( R.id.prompt );

        wire = new Random().nextInt( 5 );
        prompt.setText( "Cut the " + (
                wire == 0 ? "white" :
                wire == 1 ? "blue" :
                wire == 2 ? "green" :
                wire == 3 ? "yellow" :
                "red" ) + " wire!" );

        wires.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if( MotionEvent.ACTION_UP == event.getAction() ) {
                    System.out.println( "X: " + event.getX() + "; Y: " + event.getY() + "; leftBound: " + wireLeftBound + "; rightBound: " + wireRightBound );
                    if( event.getX() > wireLeftBound && event.getX() < wireRightBound ) {
                        sound.stop();
                        countdown.cancel();
                        wires.setImageResource( wire == 0 ? R.drawable.wires_grey_cut :
                                wire == 1 ? R.drawable.wires_blue_cut :
                                wire == 2 ? R.drawable.wires_green_cut :
                                wire == 3 ? R.drawable.wires_yellow_cut :
                                R.drawable.wires_red_cut );
                        finish();
                    } else {
                        countdown.onFinish(); //FIXME this is not the right way to do this
                    }
                }
                return true;
            }
        });

        startSound();
        startCountdown();
    }

    @Override
    public void onWindowFocusChanged( boolean hasFocus ) {
        ImageView wires = (ImageView) findViewById( R.id.wires );
        wireLeftBound = wire * wires.getWidth() / 5;
        wireRightBound = (wire + 1) * wires.getWidth() / 5;
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
