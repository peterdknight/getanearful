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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class WordPractice extends AppCompatActivity {
    private Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10;
    private Button button11,button12,button13,button14,button15,button16,button17,button18,button19,button20;
    private Button button21,button22,button23,button24,button25,button26,exitButton;
    private Button button27,button28,button29,button30;
    private TextView displaySentence,errMesg;
    private String waveName,theFile; // the current word file
    private int wave,waveID,waveStreamID;
    private Uri uri;
    private  int recNumber,first,last,noOfWords,indexForWords;
    private int maxWords;
    private String theSentence,aWord,displayRecNumber;
    private String wordsRead[],lines[];
    private String wordFileName; //passed from EarfullWords
    private String[] words,waveNames;
    private SoundPool soundPool;
    SoundPool.Builder builder;
    private AudioManager audioManager;
    private static final int MAX_STREAMS = 1;
    private float volume;
    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private boolean loaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        words = new String[26];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_practice);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        displaySentence = findViewById(R.id.displaySentence);
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
        exitButton = findViewById((R.id.EXIT));




        Intent intent = getIntent();
        theFile = intent.getStringExtra("message1");
      //  displaySentence.setText("file=" + theFile);
     //   displayRecNumber = intent.getStringExtra(   "message2");
     //   waveName = intent.getStringExtra("message3");

       readWords();
       decodeLines();

       makeInvisible();
        setWords();
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
    }

    public void makeInvisible() {
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


    }

    public void setWords() {
        for(int buttonNo = 0;buttonNo < maxWords ;buttonNo++) {
            aWord = wordsRead[buttonNo];
            switch (buttonNo) {
                case 0:
                    button1.setText(aWord);

                    button1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    button2.setText(aWord);
                    button2.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    button3.setText(aWord);
                    button3.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    button4.setText(aWord);
                    button4.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    button5.setText(aWord);
                    button5.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    button6.setText(aWord);
                    button6.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    button7.setText(aWord);
                    button7.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    button8.setText(aWord);
                    button8.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    button9.setText(aWord);
                    button9.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    button10.setText(aWord);
                    button10.setVisibility(View.VISIBLE);
                    break;
                case 10:
                    button11.setText(aWord);
                    button11.setVisibility(View.VISIBLE);
                    break;
                case 11:
                    button12.setText(aWord);
                    button12.setVisibility(View.VISIBLE);
                    break;
                case 12:
                    button13.setText(aWord);
                    button13.setVisibility(View.VISIBLE);
                    break;
                case 13:
                    button14.setText(aWord);
                    button14.setVisibility(View.VISIBLE);
                    break;
                case 14:
                    button15.setText(aWord);
                    button15.setVisibility(View.VISIBLE);
                    break;
                case 15:
                    button16.setText(aWord);
                    button16.setVisibility(View.VISIBLE);
                    break;


                case 16:
                    button17.setText(aWord);
                    button17.setVisibility(View.VISIBLE);
                    break;
                case 17:
                    button18.setText(aWord);
                    button18.setVisibility(View.VISIBLE);
                    break;
                case 18:
                    button19.setText(aWord);
                    button19.setVisibility(View.VISIBLE);
                    break;
                case 19:
                    button20.setText(aWord);
                    button20.setVisibility(View.VISIBLE);
                    break;
                case 20:
                    button21.setText(aWord);
                    button21.setVisibility(View.VISIBLE);
                    break;
                case 21:
                    button22.setText(aWord);
                    button22.setVisibility(View.VISIBLE);
                    break;
                case 22:
                    button23.setText(aWord);
                    button23.setVisibility(View.VISIBLE);
                    break;
                case 23:
                    button24.setText(aWord);
                    button24.setVisibility(View.VISIBLE);
                    break;
                case 24:
                    button25.setText(aWord);
                    button25.setVisibility(View.VISIBLE);
                    break;
                case 25:
                    button26.setText(aWord);
                    button26.setVisibility(View.VISIBLE);
                    break;
                case 26:
                    button27.setText(aWord);
                    button27.setVisibility(View.VISIBLE);
                    break;
                case 27:
                    button28.setText(aWord);
                    button28.setVisibility(View.VISIBLE);
                    break;
                case 28:
                    button29.setText(aWord);
                    button29.setVisibility(View.VISIBLE);
                    break;
                case 29:
                    button30.setText(aWord);
                    button30.setVisibility(View.VISIBLE);
                    break;

            }
        } //end for loop
    }
    public void gob1(View view) throws InterruptedException {
        waveName = waveNames[0];
        playSound();
    }

    public void gob2(View view) throws InterruptedException {
        waveName = waveNames[1];
        playSound();
    }
    public void gob3(View view) throws InterruptedException {
        waveName = waveNames[2];
        playSound();
    }
    public void gob4(View view) throws InterruptedException {
        waveName = waveNames[3];
        playSound();
    }
    public void gob5(View view) throws InterruptedException {
        waveName = waveNames[4];
        playSound();
    }
    public void gob6(View view) throws InterruptedException {
        waveName = waveNames[5];
        playSound();
    }
    public void gob7(View view) throws InterruptedException {
        waveName = waveNames[6];
        playSound();
    }
    public void gob8(View view) throws InterruptedException {
        waveName = waveNames[7];
        playSound();
    }
    public void gob9(View view) throws InterruptedException {
        waveName = waveNames[8];
        playSound();
    }
    public void gob10(View view) throws InterruptedException {
        waveName = waveNames[9];
        playSound();
    }
    public void gob11(View view) throws InterruptedException {
        waveName = waveNames[10];
        playSound();
    }
    public void gob12(View view) throws InterruptedException {
        waveName = waveNames[11];
        playSound();
    }
    public void gob13(View view) throws InterruptedException {
        waveName = waveNames[12];
        playSound();
    }
    public void gob14(View view) throws InterruptedException {
        waveName = waveNames[13];
        playSound();
    }
    public void gob15(View view) throws InterruptedException {
        waveName = waveNames[14];
        playSound();
    }
    public void gob16(View view) throws InterruptedException {
        waveName = waveNames[15];
        playSound();
    }
    public void gob17(View view) throws InterruptedException {
        waveName = waveNames[16];
        playSound();
    }
    public void gob18(View view) throws InterruptedException {
        waveName = waveNames[17];
        playSound();
    }
    public void gob19(View view) throws InterruptedException {
        waveName = waveNames[18];
        playSound();
    }
    public void gob20(View view) throws InterruptedException {
        waveName = waveNames[19];
        playSound();
    }
    public void gob21(View view) throws InterruptedException {
        waveName = waveNames[20];
        playSound();
    }
    public void gob22(View view) throws InterruptedException {
        waveName = waveNames[21];
        playSound();
    }
    public void gob23(View view) throws InterruptedException {
        waveName = waveNames[22];
        playSound();
    }
    public void gob24(View view) throws InterruptedException {
        waveName = waveNames[23];
        playSound();
    }
    public void gob25(View view) throws InterruptedException {
        waveName = waveNames[24];
        playSound();
    }
    public void gob26(View view) throws InterruptedException {
        waveName = waveNames[25];
        playSound();
    }
    public void gob27(View view) throws InterruptedException {
        waveName = waveNames[26];
        playSound();
    }
    public void gob28(View view) throws InterruptedException {
        waveName = waveNames[27];
        playSound();
    }
    public void gob29(View view) throws InterruptedException {
        waveName = waveNames[28];
        playSound();
    }
    public void gob30(View view) throws InterruptedException {
        waveName = waveNames[29];
        playSound();
    }

    public void playSound() throws InterruptedException {


        uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + waveName);

        wave = getResources().getIdentifier(waveName, "raw", getPackageName());
        this.waveID = (int) this.soundPool.load(this, wave, 1);
        Thread.sleep(1000);
        waveStreamID = this.soundPool.play(this.waveID, 1, 1,
                1, 0, 1);  //speechrate set to 1

    }
     public void decodeLines() {
         wordsRead = new String[40];
         waveNames = new String[40];

         for (int pointer = 0;pointer<maxWords;pointer++){
           waveNames[pointer]  = DecodeWord.getWave((lines[pointer]));
           wordsRead[pointer] = DecodeWord.getWord(lines[pointer]);


         }

     }

    public void readWords() {
        int wordCounter = 0;
        String line;
        lines = new String[40];

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
                lines[wordCounter] = line;
                wordCounter++;


            }
            while (line != null);

            maxWords = wordCounter -1;

          //   displaySentence.setText("lines=" + maxWords);
            if (maxWords >30) maxWords = 30; // only got 30 buttons to display
            fis.close();

            //   textViewSentence.setText("recs" +maxRecs);
            //   viewTheSentence.setText("recs read " + maxRecs);
            //  showSentence.setText("max recs = " + maxRecs);
            //    Toast.makeText(getApplicationContext(),sentences[0].length(),
            //    Toast.LENGTH_SHORT).show();


        }
        catch (IOException ex){


        }
    }
    public void goEXIT(View view)
    {

        // finish();

        // Intents are objects of the android.content.Intent type. Your code can send them
        // to the Android system defining the components you are targeting.
        // Intent to start an activity called oneActivity with the following code:

        Intent intent = new Intent(WordPractice.this, EarfulWords.class);

        // start the activity connect to the specified class
        startActivity(intent);
    }
}

