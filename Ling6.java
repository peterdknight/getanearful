package com.example.earfull1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Ling6 extends AppCompatActivity {
    private Button ahButton, eeButton, mmButton, ooButton, ssButton, shButton, exitButton;
    private Button listenButton, nextButton, playButton,infoButton;
    private String waveFileName[] = {"lingah", "lingee", "lingmm", "lingoo", "lingsh", "lingss"};
    private String waveNames[] = {"ah", "ee", "mm", "oo", "sh", "ss"};
    private String waveName,FileName;
    private int wave, waveID, waveStreamID; // used to play wave
    private Uri uri;
    private SoundPool.Builder builder;
    private SoundPool soundPool;
    private AudioManager audioManager;
    private static final int MAX_STREAMS = 1;
    private float volume;
    private String stars[] = {"*","**","***","****","*****","******"};
    private int tryCounter,buttonPressed,mistakes;
    private Boolean gameRunning,repeatSound;
    private String info;
    private int arrayPointer[];
    int index;   // pointer to array pointer table
    private  RandomArray rTable;
    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private TextView titleTextView,starstextView,errortextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ling6);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ahButton = findViewById(R.id.ahButton);
        eeButton = findViewById(R.id.eeButton);
        shButton = findViewById((R.id.shButton));
        ssButton = findViewById((R.id.ssButton));
        mmButton = findViewById((R.id.mmButton));
        ooButton = findViewById((R.id.ooButton));
        eeButton = findViewById((R.id.eeButton));
        errortextView = findViewById((R.id.errortextView));
        playButton = findViewById(R.id.playButton);
        listenButton = findViewById(R.id.listenButton);
        starstextView = findViewById(R.id.starstextView);
        infoButton =findViewById((R.id.infoButton));
        titleTextView = findViewById(R.id.titletextView);
        gameRunning = false;  // set for listening to sounds
        repeatSound = false;
        starstextView.setVisibility(View.GONE);
        errortextView.setVisibility(View.GONE);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        // Current volumn Index of particular stream type.
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);
        // Get the maximum volume index for a particular stream type.
        float maxVolumeIndex = (float) audioManager.getStreamMaxVolume(streamType);
        // Volumn (0 --> 1)babblePoo
        this.volume = currentVolumeIndex / maxVolumeIndex;
        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        this.setVolumeControlStream(streamType);
        // For Android SDK >= 21
        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);
            this.soundPool = builder.build();
        }
        // for Android SDK < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        // When Sound Pool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            }
        });

    }

    public void playSound() throws InterruptedException {


        uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + waveName);

        wave = getResources().getIdentifier(waveName, "raw", getPackageName());


        this.waveID = (int) this.soundPool.load(this, wave, 1);


        Thread.sleep(1000);
        waveStreamID = this.soundPool.play(this.waveID, 1, 1,
                1, 0, 1);  //speechrate set to 1
    }

    public void goPlay (View view ) throws InterruptedException {

        if (tryCounter > 5) tryCounter = 0;
        if (tryCounter == 0){// setup random array from 0-5
         rTable = new RandomArray();
         arrayPointer = rTable.setMultiplier(6);
         mistakes=0;
            starstextView.setVisibility(View.VISIBLE);
         //   errortextView.setVisibility(View.VISIBLE);
         starstextView.setText("");
         errortextView.setText("");
    //      titleTextView.setText(arrayPointer[0] + " " + arrayPointer[1] +" "+arrayPointer[2]
             //     + arrayPointer[3] + arrayPointer[4]+ arrayPointer[5]);
        }
        gameRunning = true;
        setGrey();  // sound buttons grey
        //***************************************temp
        index  = arrayPointer[tryCounter] -1 ;
        waveName = waveFileName[index];
        playSound();

    }
    public void goListen(View view){
        gameRunning = false;
        setBlue();
        starstextView.setVisibility(View.GONE);
        errortextView.setVisibility(View.GONE);
        tryCounter = 0;
        mistakes = 0;
        setBlue();
        }




  public void goAh(View view) throws InterruptedException {
        if (gameRunning) {
            buttonPressed = 0;
          setRedorGreen();

        }
        else {
            waveName = waveFileName[0];
            playSound();
        }
    }

    public void goEe(View view) throws InterruptedException {
        buttonPressed = 1;
        if (gameRunning) {

            setRedorGreen();

        }
        else {
            waveName = waveFileName[1];
            playSound();
        }
    }

    public void goMm(View view) throws InterruptedException {
        buttonPressed = 2;
        if (gameRunning) {

            setRedorGreen();

        }
        else {
            waveName = waveFileName[2];
            playSound();
        }
    }

    public void goOo(View view) throws InterruptedException {
        buttonPressed = 3;
        if (gameRunning) {

            setRedorGreen();

        }
        else {
            waveName = waveFileName[3];
            playSound();
        }
    }

    public void goSh(View view) throws InterruptedException {
        buttonPressed = 4;
        if (gameRunning) {

            setRedorGreen();

        }
        else {
            waveName = waveFileName[4];
            playSound();
        }
    }

    public void goSs(View view) throws InterruptedException {
        buttonPressed = 5;
        if (gameRunning) {

            setRedorGreen();

        }
        else {
            waveName = waveFileName[5];
            playSound();
        }

    }
    public void setGrey() {

        ahButton.setBackgroundColor(getResources().getColor(R.color.Silver));
        eeButton.setBackgroundColor(getResources().getColor(R.color.Silver));
        mmButton.setBackgroundColor(getResources().getColor(R.color.Silver));
        ooButton.setBackgroundColor(getResources().getColor(R.color.Silver));
        shButton.setBackgroundColor(getResources().getColor(R.color.Silver));
        ssButton.setBackgroundColor(getResources().getColor(R.color.Silver));
    }

    public void setBlue() {
        ahButton.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
        eeButton.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
        mmButton.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
        ooButton.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
        shButton.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
        ssButton.setBackgroundColor(getResources().getColor(R.color.Light_Blue));

    }
    public void setRedorGreen () {

        switch (buttonPressed) {

            case (0):


                if (waveNames[index].equals("ah")) {
                    ahButton.setBackgroundColor(getResources().getColor(R.color.Green));
                    starstextView.setVisibility(View.VISIBLE);
                    starstextView.setText(stars[tryCounter]);
                    playButton.setText("PLAY");
                    tryCounter = tryCounter + 1;
                    repeatSound = false;


                } else {
                    ahButton.setBackgroundColor(getResources().getColor(R.color.Red));
                    playButton.setText("REPEAT");
                    repeatSound = true;
                    mistakes = mistakes + 1;
                }
                break;

            case (1):


                if (waveNames[index].equals("ee")) {

                    eeButton.setBackgroundColor(getResources().getColor(R.color.Green));
                    starstextView.setVisibility(View.VISIBLE);
                    starstextView.setText(stars[tryCounter]);
                    playButton.setText("PLAY");
                    tryCounter = tryCounter + 1;
                    repeatSound = false;
                } else {
                    eeButton.setBackgroundColor(getResources().getColor(R.color.Red));
                    playButton.setText("REPEAT");
                    repeatSound = true;
                    mistakes = mistakes + 1;
                }
                break;

            case (2):


                if (waveNames[index].equals("mm")) {

                    mmButton.setBackgroundColor(getResources().getColor(R.color.Green));
                    starstextView.setVisibility(View.VISIBLE);
                    starstextView.setText(stars[tryCounter]);
                    playButton.setText("PLAY");
                    tryCounter = tryCounter + 1;
                    repeatSound = false;
                } else {
                    mmButton.setBackgroundColor(getResources().getColor(R.color.Red));
                    playButton.setText("REPEAT");
                    repeatSound = true;
                    mistakes = mistakes + 1;
                }
                break;

            case (3):


                if (waveNames[index].equals("oo")) {

                    ooButton.setBackgroundColor(getResources().getColor(R.color.Green));
                    starstextView.setVisibility(View.VISIBLE);
                    starstextView.setText(stars[tryCounter]);
                    playButton.setText("PLAY");
                    tryCounter = tryCounter + 1;
                    repeatSound = false;
                } else {
                    ooButton.setBackgroundColor(getResources().getColor(R.color.Red));
                    playButton.setText("REPEAT");
                    repeatSound = true;
                    mistakes = mistakes + 1;
                }
                break;

            case (4):


                if (waveNames[index].equals("sh")) {

                    shButton.setBackgroundColor(getResources().getColor(R.color.Green));
                    starstextView.setVisibility(View.VISIBLE);
                    starstextView.setText(stars[tryCounter]);
                    playButton.setText("PLAY");
                    tryCounter = tryCounter + 1;
                    repeatSound = false;
                } else {
                    shButton.setBackgroundColor(getResources().getColor(R.color.Red));
                    playButton.setText("REPEAT");
                    repeatSound = true;
                    mistakes = mistakes + 1;
                }
                break;

            case (5):


                if (waveNames[index].equals("ss")) {

                    ssButton.setBackgroundColor(getResources().getColor(R.color.Green));
                    starstextView.setVisibility(View.VISIBLE);
                    starstextView.setText(stars[tryCounter]);
                    playButton.setText("PLAY");
                    tryCounter = tryCounter + 1;
                    repeatSound = false;
                } else {
                    ssButton.setBackgroundColor(getResources().getColor(R.color.Red));
                    playButton.setText("REPEAT");
                    repeatSound = true;
                    mistakes = mistakes + 1;
                }
                break;


        }
        if (tryCounter > 5) {
            if (mistakes > 0) {
                errortextView.setVisibility(View.VISIBLE);
                errortextView.setText(mistakes + " WRONG");
                starstextView.setText("ALL DONE");
            } else {
                starstextView.setText("ALL CORRECT");
            }
        }

    }
    public void goInfo(View view)
    {

        // finish();

        // Intents are objects of the android.content.Intent type. Your code can send them
        // to the Android system defining the components you are targeting.
        // Intent to start an activity called oneActivity with the following code:

        Intent intent = new Intent(Ling6.this, Lin6Info.class);

        // start the activity connect to the specified class
        startActivity(intent);
    }
    public void goExit(View view)
    {

        // finish();

        // Intents are objects of the android.content.Intent type. Your code can send them
        // to the Android system defining the components you are targeting.
        // Intent to start an activity called oneActivity with the following code:

        Intent intent = new Intent(Ling6.this, MainActivity.class);

        // start the activity connect to the specified class
        startActivity(intent);
    }
    
}
