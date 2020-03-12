package com.example.earfull1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.SweepGradient;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Matrix2 extends AppCompatActivity {
    private Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10;
    private Button button11,button12,button13,button14,button15,button16,exitButton,repeatButton,playButton;
    private Button button17,button18,button19,button20,button21,button22,button23,button24;
    private Button button25,button26,button27,button28,button29,button30,button31,button32,button33,button34,button35,button36;
    private Button noiseButton;
    private String waveName,theFile; // the current word file
    private int wave,waveID,waveStreamID;
    private String selectedWaveName,action; //action "play","check","repeat"
    private int pointer;
    private Uri uri;
    private  int recNumber,first,last,noOfWords,indexForWords;
    private int maxWords,matrixSize;
    private int lineToPlay[]; //contains the lines selected to play from matrix
    private String wordsToPlay[];
    private String aWord,wordFileName;
    private String wordsRead[],lines[],waveFileNames[],waveWordNames[];
    //waveFilenames contains the name of the wave file and wordFileNames contains the spoke word
    // lines to play are the four columns selected
    private String readFileLine[];  ///asset file with wavwnames and spoken words
    private int linesToPlay[]; ////contains pointers to the words to be played
    private String[] words,waveNames;
    private SoundPool soundPool;
    private int sentenceStreamId,progressChangedValue;
    private int soundIdSentence;
    private int soundIdnoise,noiseStreamId,soundIdword,wordStreamId;
    private float volume,noiseLeftVolume,noiseRightVolume;
    private boolean loaded,noiseRunning,noisePaused,wordsRunning;
    private Boolean noiseCheck;  /// used to check if correct button pressed
    private int noiseWave,wordWaveName,matDisplay;
    private Uri noiseUri,wordUri;
    private String nameNoise;
    private int maxNoiseVolume,noiseNumber; //chooses noise file
    private int stopIdnoise;
    private Float leftSentenceVolume,rightSentenceVolume;

    SoundPool.Builder builder;
    private AudioManager audioManager;
    private static final int MAX_STREAMS =3;

    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;

    private Float  normalSpeechRate,slowSpeechRate,    speechRate;
    private SeekBar volumeSeekBar;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix2);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        theFile = intent.getStringExtra("message1");
        //  displaySentence.setText("file=" + theFile);
        //   displayRecNumber = intent.getStringExtra(   "message2");
        //   waveName = intent.getStringExtra("message3");
        title = findViewById((R.id.displaySentence));
        button1 = findViewById((R.id.button1));
        button2 = findViewById((R.id.button2));
        button3 = findViewById((R.id.button3));
        button4 = findViewById((R.id.button4));
        button5 = findViewById((R.id.button5));
        button6 = findViewById((R.id.button6));
        button7 = findViewById((R.id.button7));
        button8 = findViewById((R.id.button8));
        button9 = findViewById((R.id.button9));
        button10 = findViewById((R.id.button10));
        button11 = findViewById((R.id.button11));
        button12 = findViewById((R.id.button12));
        button13 = findViewById((R.id.button13));
        button14 = findViewById((R.id.button14));
        button15 = findViewById((R.id.button15));
        button16 = findViewById((R.id.button16));
        button17 = findViewById((R.id.button17));
        button18 = findViewById((R.id.button18));
        button19 = findViewById((R.id.button19));
        button20 = findViewById((R.id.button20));
        button21 = findViewById((R.id.button21));
        button22 = findViewById((R.id.button22));
        button23 = findViewById((R.id.button23));
        button24 = findViewById((R.id.button24));
        button25 = findViewById((R.id.button25));
        button26 = findViewById((R.id.button26));
        button27 = findViewById((R.id.button27));
        button28 = findViewById((R.id.button28));
        button29 = findViewById((R.id.button29));
        button30 = findViewById((R.id.button30));
        button31 = findViewById((R.id.button31));
        button32 = findViewById((R.id.button32));
        button33 = findViewById((R.id.button33));
        button34 = findViewById((R.id.button34));
        button35 = findViewById((R.id.button35));
        button36 = findViewById((R.id.button36));
        playButton = findViewById((R.id.playButton));
        exitButton = findViewById((R.id.exitButton));
        repeatButton = findViewById((R.id.repeatButton));
        noiseButton = findViewById(R.id.noiseButton);
        noiseNumber = 1;
        noiseButton.setText("BABBLE");
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        maxNoiseVolume = volumeSeekBar.getMax();
        wordsRunning = false;

        matrixSize = 6;
        linesToPlay = new int [6];
        theFile = "mat2words.txt";
        leftSentenceVolume = (float) 1;
        rightSentenceVolume = (float) 1;
        normalSpeechRate= (float) 1.0;
        slowSpeechRate = (float) 0.8;
        readWords();
        waveFileNames= new String[maxWords];
        waveWordNames = new String[maxWords];
        matDisplay = 1; // show matrix on first screen 1 = no matrix 2 = get ready to play 3 = show
        decodeWords();
        setWords();/// setup button display
        unsetWords(); // make matrix invisible
        playButton.setText("PLAY");

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
                loaded = true;
            }
        });

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;

                noiseLeftVolume =  (float) progressChangedValue/maxNoiseVolume;
                noiseRightVolume = (float) progressChangedValue/maxNoiseVolume;

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                if (noiseRunning) {
                    soundPool.stop(noiseStreamId);

                    try {
                        playNoiseFile();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }  //endif
            }
        });
    }

    public void goNoise(View view) throws InterruptedException {

        switch (noiseNumber) {
            case 1:
                nameNoise = "babble";
                noiseButton.setText("BABBLE");
                        break;
            case 2:
                nameNoise = "birdnoise";  // sa bird recording
                noiseButton.setText("NATURAL");
                        break;
            case 3:
                nameNoise = "gardenbirdsmix";
                noiseButton.setText("GARDEN BIRDS");
                break;
            case 4:
                nameNoise = "cafe";
                noiseButton.setText("CAFE");

        }

        if (noiseRunning){
            soundPool.stop(this.noiseStreamId);
        }
        playNoiseFile();
        noiseNumber = noiseNumber + 1;
        if ( noiseNumber > 4) noiseNumber = 1;
    }

    public void playNatural(View view) throws InterruptedException {
        nameNoise = "birdnoise";
        if (noiseRunning){
            soundPool.stop(this.noiseStreamId);
        }
        playNoiseFile();


    }
    // When users click on the button "Babble"
    public void playNoiseFile() throws InterruptedException {


        noiseUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + nameNoise);

        noiseWave = getResources().getIdentifier(nameNoise, "raw", getPackageName());
        this.soundIdnoise = (int) this.soundPool.load(this, noiseWave, 1);

        try {
            Thread.sleep(1500); //for some reason babble needs longer than birdnoise
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        noiseStreamId = this.soundPool.play(this.soundIdnoise, noiseLeftVolume,noiseRightVolume, 1, -1, 1f);
        noiseRunning = true;
    }
    public void playSound()  {
        int linePointer;
            String names;
            names="";
        normalSpeechRate= (float) 1.0;
        slowSpeechRate = (float) 0.8;
        speechRate = normalSpeechRate;

        for (int i=0;i<matrixSize;i++) {
            linePointer = linesToPlay[i];
            wordFileName = waveFileNames[linePointer];
            names = names + wordFileName + " ";

            //   title.setText("file=" + wordFileName);
            try {

                uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + wordFileName);

                wave = getResources().getIdentifier(wordFileName, "raw", getPackageName());
               this.soundIdSentence = (int) this.soundPool.load(this, wave, 1);

                Thread.sleep(1000);
                sentenceStreamId = this.soundPool.play(this.soundIdSentence, leftSentenceVolume, rightSentenceVolume,
                        1, 0, speechRate);
                // viewTheSentence.setText("");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                //title.setText("trying to play "  + wordFileName);}
            }
//title.setText(names);
            wordsRunning = true;
        }
    }

    public void playRepeat() throws InterruptedException {
        int linePointer;


        for (int i=0;i<matrixSize;i++) {
            linePointer = linesToPlay[i];
            wordFileName = waveFileNames[linePointer];
            uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + wordFileName);
            wave = getResources().getIdentifier(wordFileName, "raw", getPackageName());
            this.soundIdSentence = (int) this.soundPool.load(this, wave, 1);
            Thread.sleep(1000);
            sentenceStreamId = this.soundPool.play(this.soundIdSentence, leftSentenceVolume, rightSentenceVolume,
                    1, 0, speechRate);

        }
    }



    public void setWords() {
        for (int buttonNo = 0; buttonNo < maxWords; buttonNo++) {
            aWord = waveWordNames[buttonNo];
            switch (buttonNo) {
                case 0:
                    button1.setText(aWord);
                    button1.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    button1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    button2.setText(aWord);
                    button2.setVisibility(View.VISIBLE);
                    button2.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;

                case 2:
                    button3.setText(aWord);
                    button3.setVisibility(View.VISIBLE);
                    button3.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 3:
                    button4.setText(aWord);
                    button4.setVisibility(View.VISIBLE);
                    button4.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 4:
                    button5.setText(aWord);
                    button5.setVisibility(View.VISIBLE);
                    button5.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 5:
                    button6.setText(aWord);
                    button6.setVisibility(View.VISIBLE);
                    button6.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 6:
                    button7.setText(aWord);
                    button7.setVisibility(View.VISIBLE);
                    button7.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 7:
                    button8.setText(aWord);
                    button8.setVisibility(View.VISIBLE);
                    button8.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 8:
                    button9.setText(aWord);
                    button9.setVisibility(View.VISIBLE);
                    button9.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 9:
                    button10.setText(aWord);
                    button10.setVisibility(View.VISIBLE);
                    button10.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 10:
                    button11.setText(aWord);
                    button11.setVisibility(View.VISIBLE);
                    button11.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 11:
                    button12.setText(aWord);
                    button12.setVisibility(View.VISIBLE);
                    button12.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 12:
                    button13.setText(aWord);
                    button13.setVisibility(View.VISIBLE);
                    button13.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 13:
                    button14.setText(aWord);
                    button14.setVisibility(View.VISIBLE);
                    button14.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 14:
                    button15.setText(aWord);
                    button15.setVisibility(View.VISIBLE);
                    button15.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 15:
                    button16.setText(aWord);
                    button16.setVisibility(View.VISIBLE);
                    button16.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 16:
                    button17.setText(aWord);
                    button17.setVisibility(View.VISIBLE);
                    button17.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 17:
                    button18.setText(aWord);
                    button18.setVisibility(View.VISIBLE);
                    button18.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 18:
                    button19.setText(aWord);
                    button19.setVisibility(View.VISIBLE);
                    button19.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 19:
                    button20.setText(aWord);
                    button20.setVisibility(View.VISIBLE);
                    button20.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 20:
                    button21.setText(aWord);
                    button21.setVisibility(View.VISIBLE);
                    button21.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 21:
                    button22.setText(aWord);
                    button22.setVisibility(View.VISIBLE);
                    button22.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 22:
                    button23.setText(aWord);
                    button23.setVisibility(View.VISIBLE);
                    button23.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 23:
                    button24.setText(aWord);
                    button24.setVisibility(View.VISIBLE);
                    button24.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 24:
                    button25.setText(aWord);
                    button25.setVisibility(View.VISIBLE);
                    button25.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 25:
                    button26.setText(aWord);
                    button26.setVisibility(View.VISIBLE);
                    button26.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 26:
                    button27.setText(aWord);
                    button27.setVisibility(View.VISIBLE);
                    button27.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 27:
                    button28.setText(aWord);
                    button28.setVisibility(View.VISIBLE);
                    button28.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 28:
                    button29.setText(aWord);
                    button29.setVisibility(View.VISIBLE);
                    button29.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 29:
                    button30.setText(aWord);
                    button30.setVisibility(View.VISIBLE);
                    button30.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 30:
                    button31.setText(aWord);
                    button31.setVisibility(View.VISIBLE);
                    button31.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 31:
                    button32.setText(aWord);
                    button32.setVisibility(View.VISIBLE);
                    button32.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 32:
                    button33.setText(aWord);
                    button33.setVisibility(View.VISIBLE);
                    button33.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 33:
                    button34.setText(aWord);
                    button34.setVisibility(View.VISIBLE);
                    button34.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 34:
                    button35.setText(aWord);
                    button35.setVisibility(View.VISIBLE);
                    button35.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;
                case 35:
                    button36.setText(aWord);
                    button36.setVisibility(View.VISIBLE);
                    button36.setBackgroundColor(getResources().getColor(R.color.Light_Blue));
                    break;



            }
        } //end
    }

    public void unsetWords() {
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        button4.setVisibility(View.GONE);
        button5.setVisibility(View.GONE);
        button6.setVisibility(View.GONE);
        button7.setVisibility(View.GONE);
        button8.setVisibility(View.GONE);
        button9.setVisibility(View.GONE);
        button10.setVisibility(View.GONE);
        button11.setVisibility(View.GONE);
        button12.setVisibility(View.GONE);
        button13.setVisibility(View.GONE);
        button14.setVisibility(View.GONE);
        button15.setVisibility(View.GONE);
        button16.setVisibility(View.GONE);
        button17.setVisibility(View.GONE);
        button18.setVisibility(View.GONE);
        button19.setVisibility(View.GONE);
        button20.setVisibility(View.GONE);
        button21.setVisibility(View.GONE);
        button22.setVisibility(View.GONE);
        button23.setVisibility(View.GONE);
        button24.setVisibility(View.GONE);
        button25.setVisibility(View.GONE);
        button26.setVisibility(View.GONE);
        button27.setVisibility(View.GONE);
        button28.setVisibility(View.GONE);
        button29.setVisibility(View.GONE);
        button30.setVisibility(View.GONE);
        button31.setVisibility(View.GONE);
        button32.setVisibility(View.GONE);
        button33.setVisibility(View.GONE);
        button34.setVisibility(View.GONE);
        button35.setVisibility(View.GONE);
        button36.setVisibility(View.GONE);

    }
    public Boolean checkButton(int buttonIndex){

        for (int i=0;i<matrixSize;i++){
            if (buttonIndex == linesToPlay[i] ) return true;

        }

        return false;
    }

    public void goPlay(View view)  {
        //MAKE MATRIX INVISIBLE  WHEN PLAYING
        switch (matDisplay) {
            case (1) :

                playButton.setText("SHOW MATRIX");
                setupWords();
                if (wordsRunning) {
                    soundPool.stop(this.sentenceStreamId);
                    wordsRunning = false;
                }
                playSound();
                matDisplay = 2;
                break;
            case (2):

                setWords();

                playButton.setText("NEXT TEST");
                matDisplay = 3;
                break;
            case (3):

                matDisplay = 1;
                playButton.setText("PLAY");
                unsetWords();
        }
    }

    public void goRepeat(View view) throws InterruptedException {
        playRepeat();
    }



    public void gob1(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(0);

        if (buttonChecked)
            button1.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button1.setBackgroundColor(getResources().getColor(R.color.Red));
    }

    public void gob2(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(1);

        if (buttonChecked)
            button2.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button2.setBackgroundColor(getResources().getColor(R.color.Red));
    }

    public void gob3(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(2);

        if (buttonChecked)
            button3.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button3.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob4(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(3);

        if (buttonChecked)
            button4.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button4.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob5(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(4);

        if (buttonChecked)
            button5.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button5.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob6(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(5);

        if (buttonChecked)
            button6.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button6.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob7(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(6);

        if (buttonChecked)
            button7.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button7.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob8(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(7);

        if (buttonChecked)
            button8.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button8.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob9(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(8);

        if (buttonChecked)
            button9.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button9.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob10(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(9);

        if (buttonChecked)
            button10.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button10.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob11(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(10);

        if (buttonChecked)
            button11.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button11.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob12(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(11);

        if (buttonChecked)
            button12.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button12.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob13(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(12);

        if (buttonChecked)
            button13.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button13.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob14(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(13);

        if (buttonChecked)
            button14.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button14.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob15(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(14);

        if (buttonChecked)
            button15.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button15.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob16(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(15);

        if (buttonChecked)
            button16.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button16.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob17(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(16);

        if (buttonChecked)
            button17.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button17.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob18(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(17);

        if (buttonChecked)
            button18.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button18.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob19(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(18);

        if (buttonChecked)
            button19.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button19.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob20(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(19);

        if (buttonChecked)
            button20.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button20.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob21(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(20);

        if (buttonChecked)
            button21.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button21.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob22(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(21);

        if (buttonChecked)
            button22.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button22.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob23(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(22);

        if (buttonChecked)
            button23.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button23.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob24(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(23);

        if (buttonChecked)
            button24.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button24.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob25(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(24);

        if (buttonChecked)
            button25.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button25.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob26(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(25);

        if (buttonChecked)
            button26.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button26.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob27(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(26);

        if (buttonChecked)
            button27.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button27.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob28(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(27);

        if (buttonChecked)
            button28.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button28.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob29(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(28);

        if (buttonChecked)
            button29.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button29.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob30(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(29);

        if (buttonChecked)
            button30.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button30.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob31(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(30);

        if (buttonChecked)
            button31.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button31.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob32(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(31);

        if (buttonChecked)
            button32.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button32.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob33(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(32);

        if (buttonChecked)
            button33.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button33.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob34(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(33);

        if (buttonChecked)
            button34.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button34.setBackgroundColor(getResources().getColor(R.color.Red));
    }
    public void gob35(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(34);

        if (buttonChecked)
            button35.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button35.setBackgroundColor(getResources().getColor(R.color.Red));
    }


    public void gob36(View view) throws InterruptedException {
        Boolean buttonChecked;
        buttonChecked = checkButton(35);

        if (buttonChecked)
            button36.setBackgroundColor(getResources().getColor(R.color.Green));
        else
            button36.setBackgroundColor(getResources().getColor(R.color.Red));
    }

    protected void onResume() {
        super.onResume();
        noiseLeftVolume = (float) 0.5;
        noiseRightVolume = (float) 0.5;
        soundPool.setVolume(noiseStreamId,noiseLeftVolume,noiseRightVolume);
        speechRate = normalSpeechRate;
        soundPool.setRate(sentenceStreamId,speechRate);


        //   normalSpeech.setChecked(true);
        //  Log.i(TAG, "onResume");
    }

    protected void onPause() {
        super.onPause();
        if ( noiseRunning ) {
            soundPool.pause(this.noiseStreamId);
            noisePaused = true;
        }
        // Log.i(TAG, "onPause");
    }
    // pick random columns from matrix rows
    public void setupWords() {
        Random rndNumber = new Random();
        int i,index,linePointer;
        int arrayIndex[] = new int[6];
        linesToPlay = new int[6];

        for (i=0;i<6;i++){ /// row
            index = rndNumber.nextInt(6);
            arrayIndex[i] = index;  // column
            linePointer = index*6 +i; // position of soundfile in list
            linesToPlay[i] = linePointer;
        }

        //   title.setText("toplay=" + linesToPlay[0] + " " + linesToPlay[1] + " " + linesToPlay[2] + " " + linesToPlay[3]);
    }

    // decode the actual filenames and words to play
    public void decodeWords() {
        // break the words read into filename and word

        String aLine;
        int pointer;

        for (int i=0;i<maxWords;i++){

            aLine = readFileLine[i];
            waveFileNames[i] = DecodeWord.getWave(aLine);
            waveWordNames[i] = DecodeWord.getWord(aLine);
        }

        // title.setText("file=" + waveFileNames[0]);
    }

    /*
    read lines from file (filename , word)

    */



    public void readWords() {
        int wordCounter = 0;
        String line;
        readFileLine= new String[40];

        try {
            InputStream fis = getAssets().open(theFile);
            //  InputStream fis = getAssets().open("junk.txt");
            // prepare the file for reading

            InputStreamReader chapterReader = new InputStreamReader(fis);
            BufferedReader buffreader = new BufferedReader(chapterReader);

            maxWords = 0;

            // read every line of the file into the line-variable, on line at the time
            do {
                line = buffreader.readLine();
                readFileLine[wordCounter] = line;
                wordCounter++;
            }
            while (line != null);

            maxWords = wordCounter -1;

            //   displaySentence.setText("lines=" + maxWords);

            fis.close();

            //   textViewSentence.setText("recs" +maxRecs);
            //   viewTheSentence.setText("recs read " + maxRecs);
            //  showSentence.setText("max recs = " + maxRecs);
            //    Toast.makeText(getApplicationContext(),sentences[0].length(),
            //    Toast.LENGTH_SHORT).show();

            // title.setText("maxwords " + maxWords + " " + readFileLine[0]);
        }
        catch (IOException ex){


        }
    }
    public void goHelp (View view)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(Matrix2.this);
        builder.setTitle("HELP")
                .setMessage("This App plays from a Matrix of Words \n" +
                        "Press the Play Button\n" +
                        "Listen to the words. Repeat if You need to.\n" +
                        "When You are ready " +
                        "Press 'SHOW MATRIX' and then " +
                        "Press the words You hear.\n" +
                        "Correct Words will display Green.\n" +
                        "Incorrect Words will show Red.\n" +
                        "Press NEXT TEST  for another go.\n" +
                        "You can add Background noise by pressing SELECT NOISE\n" +
                        "First press will play Babble, press again\n" +
                        "for another 3 choices\n" +
                        "Adjust the Volume to suit You.\n" +
                        "Check https://www.medel.com/support/rehab/rehabilitation\n")

                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(MainActivity.this,"Selected Option: YES",Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog dialog  = builder.create();
        dialog.show();
    }
    public void goExit(View view)
    {

        // finish();

        // Intents are objects of the android.content.Intent type. Your code can send them
        // to the Android system defining the components you are targeting.
        // Intent to start an activity called oneActivity with the following code:

        Intent intent = new Intent(Matrix2.this, MainActivity.class);

        // start the activity connect to the specified class
        startActivity(intent);
    }
}

