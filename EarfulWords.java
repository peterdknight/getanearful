package com.example.earfull1;







        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.media.AudioAttributes;
        import android.media.AudioManager;
        import android.media.SoundPool;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.inputmethod.EditorInfo;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.SeekBar;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.view.View;
        import android.view.View.OnKeyListener;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.util.Random;
public class EarfulWords extends Activity implements View.OnKeyListener {







    private SoundPool soundPool;

    private AudioManager audioManager;

    // Maximumn sound stream.
    private static final int MAX_STREAMS = 3;

    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private SoundPool.Builder builder;
    private int matrixSize,maxWords;
    private float volume,noiseLeftVolume,noiseRightVolume;
    private boolean loaded,noiseRunning,noisePaused;
    private String wordName;
    private String[]     fileNames = {"mw00","mw01","mw02","mw03"};
    private String[]     words = {"one","two","three","four"};
    private String[] wordsRead;
    private String[] wordFiles = {"names.txt","w1words.txt"};
    private int soundIdnoise,noiseStreamId,soundIdword,wordStreamId;
    private int wave,noiseWave,wordWaveName;
    private TextView titleTextView;
    private EditText word1Edittext,word2Edittext,word3Edittext,word4Edittext,word5Edittext,word6Edittext;
    private Button word1Button,word2Button,word3Button,word4Button,word5Button,word6Button;
    private Button babbleButton,naturalButton,practiceWordButton,helpButton,exitButton;
    private String nameNoise,waveNoise;
    private String rndFileNames[] ,rndWords[], results[]; //matrix created and typed answers
    private int sentenceStreamId,progressChangedValue;
    private int soundIdSentence;
    private int stopIdnoise;
    private Float leftSentenceVolume,rightSentenceVolume;
    private int maxNoiseVolume;
    private Float speechRate,normalSpeechRate,slowSpeechRate;
    private Uri uri,noiseUri,wordUri;  //uri for sentences
    private SeekBar volumeSeekBar;
    private String wordFileName,lastFileName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earful_words);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        matrixSize = 4;
        results = new String[matrixSize];
        rndWords = new String[matrixSize];
        titleTextView = findViewById(R.id.title);
        titleTextView.setBackgroundColor(getResources().getColor(R.color.Silver));

        word1Edittext = findViewById(R.id.word1Edittext);
        word2Edittext = findViewById(R.id.word2Edittext);
        word3Edittext = findViewById(R.id.word3Edittext);
        word4Edittext = findViewById(R.id.word4Edittext);
        word5Edittext = findViewById(R.id.word5Edittext);
        word6Edittext = findViewById(R.id.word6Edittext);
        word1Button = findViewById(R.id.word1Button);
        word2Button = findViewById(R.id.word2Button);
        word3Button = findViewById(R.id.word3Button);
        word4Button = findViewById(R.id.word4Button);
        word5Button = findViewById(R.id.word5Button);
        word6Button = findViewById(R.id.word6Button);

        word1Edittext.setOnKeyListener(this);
        word2Edittext.setOnKeyListener(this);
        word3Edittext.setOnKeyListener(this);
        word4Edittext.setOnKeyListener(this);
        word5Edittext.setOnKeyListener(this);
        word6Edittext.setOnKeyListener(this);

        word1Edittext.setFocusable(true);
        word2Edittext.setFocusable(true);
        word3Edittext.setFocusable(true);
        word4Edittext.setFocusable(true);
        word5Edittext.setFocusable(true);
        word6Edittext.setFocusable(true);

        word1Edittext.setText("PLEASE PRESS");
        word2Edittext.setText("PLAY BUTTON");
        word3Edittext.setText("THEN TYPE IN");
        word4Edittext.setText("ANSWER AT ");
        word5Edittext.setText("THE CURSOR");
        word6Edittext.setText("");
        word1Button.setText("PRESS NEXT");
        word2Button.setText("TO COMPLETE");
        word3Button.setText("A WORD");
        word4Button.setText("PRESS DONE");
        word5Button.setText("ON LAST WORD");
        word1Edittext.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        word2Edittext.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        word3Edittext.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        word4Edittext.setImeOptions(EditorInfo.IME_ACTION_NEXT);


        word5Edittext.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        word6Edittext.setImeOptions(EditorInfo.IME_ACTION_DONE);word1Button = findViewById(R.id.word1Button);

        helpButton = findViewById(R.id.helpButon);
        exitButton=findViewById(R.id.exitButton);

        babbleButton = findViewById(R.id.babbleButton);
        naturalButton=findViewById(R.id.naturalButton);
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        maxNoiseVolume = volumeSeekBar.getMax();
        practiceWordButton = findViewById(R.id.practiceWordsButton);

        leftSentenceVolume = (float) 1;
        rightSentenceVolume = (float) 1;
        normalSpeechRate= (float) 1.0;
        slowSpeechRate = (float) 0.8;
        speechRate = normalSpeechRate;
        noiseRunning =false;
        nameNoise = "babble";
        noiseLeftVolume = (float) 0.5;
        noiseRightVolume = (float) 0.5;
        Intent intent = getIntent();
        wordFileName = intent.getStringExtra("message1");
       // wordFileName= "w1words.txt";

        readWords();

        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Current volumn Index of particular stream type.
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

        // Get the maximum volume index for a particular stream type.
        float maxVolumeIndex = (float) audioManager.getStreamMaxVolume(streamType);

        // Volumn (0 --> 1)noise
        this.volume = currentVolumeIndex / maxVolumeIndex;
        noiseLeftVolume =  (float) progressChangedValue/maxNoiseVolume;
        noiseRightVolume = (float) progressChangedValue/maxNoiseVolume;

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

    public void playBabble(View view) throws InterruptedException {
        nameNoise = "babble";
        if (noiseRunning){
            soundPool.stop(this.noiseStreamId);
        }
        playNoiseFile();

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
    public void playWordWave() throws InterruptedException {

      //  titleTextView.setText("wave " + wordName);
        wordUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + wordName);

        wordWaveName = getResources().getIdentifier(wordName, "raw", getPackageName());
        this.soundIdword = (int) this.soundPool.load(this, wordWaveName, 1);

        try {
            Thread.sleep(1500); //for some reason babble needs longer than birdnoise
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wordStreamId = this.soundPool.play(this.soundIdword, leftSentenceVolume, rightSentenceVolume, 1, 0, 1f);
       // noiseRunning = true;
    }
    public void playSoundSentence(View view) throws InterruptedException {
        cleareditTexts();
        setupWords();
        //set focus to first editword
        word1Edittext.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(word1Edittext, InputMethodManager.SHOW_IMPLICIT);

        normalSpeechRate= (float) 1.0;
        slowSpeechRate = (float) 0.8;
        speechRate = normalSpeechRate;

        for (int i=0;i<matrixSize;i++) {
            wordName = rndFileNames[i];

            // waveSentence = DecodeSentence.getSentence(sentence);
            //   Toast.makeText(getApplicationContext(),"wave *"+waveName + "*" ,
            //    Toast.LENGTH_LONG).show();
            uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + wordName);

            wave = getResources().getIdentifier(wordName, "raw", getPackageName());
            this.soundIdSentence = (int) this.soundPool.load(this, wave, 1);

            Thread.sleep(1000);
            sentenceStreamId = this.soundPool.play(this.soundIdSentence, leftSentenceVolume, rightSentenceVolume,
                    1, 0, speechRate);
            // viewTheSentence.setText("");
        }
    }
    public void repeatSentence(View view) throws InterruptedException {
        for (int i=0;i<matrixSize;i++) {
            wordName = rndFileNames[i];

            // waveSentence = DecodeSentence.getSentence(sentence);
            //   Toast.makeText(getApplicationContext(),"wave *"+waveName + "*" ,
            //    Toast.LENGTH_LONG).show();
            uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + wordName);

            wave = getResources().getIdentifier(wordName, "raw", getPackageName());
            this.soundIdSentence = (int) this.soundPool.load(this, wave, 1);

            Thread.sleep(1000);
            sentenceStreamId = this.soundPool.play(this.soundIdSentence, leftSentenceVolume, rightSentenceVolume,
                    1, 0, speechRate);
            // viewTheSentence.setText("");
        }
    }
    public void setupWords() {
 // break the words read into filename and word
        String readFileName,readFileWord,readFileLine;
        rndFileNames= new String[matrixSize];

        int pointer;
        Random rndIndex = new Random();
        for (int i=0;i<matrixSize;i++){
            pointer = rndIndex.nextInt(maxWords)  ;
            readFileLine = wordsRead[pointer];
            rndFileNames[i] = DecodeWord.getWave(readFileLine);
            rndWords[i] = DecodeWord.getWord(readFileLine);
        }


    }



    public boolean onKey(View view, int keyCode, KeyEvent event) {

        //   TextView responseText = (TextView) findViewById(R.id.responseText);
        EditText currentWord = (EditText) view;
        int txtUserid = currentWord.getId();

        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            // Perform action on Enter key press
            //   currentWord.clearFocus();


            if (!event.isShiftPressed()) {
                //   Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
                switch (view.getId()) {
                    case R.id.word1Edittext:
                        results[0] = currentWord.toString();

                        break;
                    case R.id.word2Edittext:
                        results[1] = currentWord.toString();

                        break;
                    case R.id.word3Edittext:
                        results[2] = currentWord.toString();
                        break;
                    case R.id.word4Edittext:
                        results[3] = currentWord.toString();
                        break;
                    case R.id.word5Edittext:
                        results[4] = currentWord.toString();
                        break;
                    case R.id.word6Edittext:
                        results[5] = currentWord.toString();
                        break;

                }
                return true;
            }

        }
        return false; // pass on to other listeners.

    }
    public void showResult(View view) {
        String currentText,tempText;
        for (int i=0;i<matrixSize;i++) {

            switch (i) {

                case 0:

                    word1Button.setText(rndWords[0]);
                    tempText = word1Edittext.getText().toString();
                    tempText = tempText.trim();
                    currentText = tempText.toLowerCase();

                    if(  currentText.equals(rndWords[0])){

                        word1Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
                    }
                    else {

                        word1Edittext.setBackgroundColor(getResources().getColor(R.color.Red));
                    }
                    break;
                case 1:

                    word2Button.setText(rndWords[1]);
                    tempText = word2Edittext.getText().toString();
                    tempText = tempText.trim();
                    currentText = tempText.toLowerCase();
                    if(  currentText.equals(rndWords[1]))
                        word2Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
                    else
                        word2Edittext.setBackgroundColor(getResources().getColor(R.color.Red));
                    break;
                case 2:

                    word3Button.setText(rndWords[2]);
                    tempText = word3Edittext.getText().toString();
                    tempText = tempText.trim();
                    currentText = tempText.toLowerCase();
                    if(  currentText.equals(rndWords[2]))
                        word3Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
                    else
                        word3Edittext.setBackgroundColor(getResources().getColor(R.color.Red));
                    break;
                case 3:

                    word4Button.setText(rndWords[3]);
                    tempText = word4Edittext.getText().toString();
                    tempText = tempText.trim();
                    currentText = tempText.toLowerCase();
                    if(  currentText.equals(rndWords[3]))
                        word4Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
                    else
                        word4Edittext.setBackgroundColor(getResources().getColor(R.color.Red));
                    break;
                case 4:

                    word5Button.setText(rndWords[4]);
                    tempText = word5Edittext.getText().toString();
                    tempText = tempText.trim();
                    currentText = tempText.toLowerCase();
                    if(  currentText.equals(rndWords[4]))
                        word5Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
                    else
                        word5Edittext.setBackgroundColor(getResources().getColor(R.color.Red));
                    break;
                case 5:

                    word6Button.setText(rndWords[5]);
                    tempText = word6Edittext.getText().toString();
                    tempText = tempText.trim();
                    currentText = tempText.toLowerCase();
                    if(  currentText.equals(rndWords[5]))
                        word6Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
                    else
                        word5Edittext.setBackgroundColor(getResources().getColor(R.color.Red));
                    break;
            }
        }
    }
    public void cleareditTexts (){

        word1Edittext.setText("");
        word1Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
        word2Edittext.setText("");
        word2Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
        word3Edittext.setText("");
        word3Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
        word4Edittext.setText("");
        word4Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
        word5Edittext.setText("");
        word5Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
        word6Edittext.setText("");
        word6Edittext.setBackgroundColor(getResources().getColor(R.color.Light_Green));
        word1Button.setText("");
        word2Button.setText("");
        word3Button.setText("");
        word4Button.setText("");
        word5Button.setText("");
        word6Button.setText("");
        if( matrixSize == 4) {
            word5Edittext.setVisibility(View.GONE);
            word6Edittext.setVisibility(View.GONE);
            word5Button.setVisibility(View.GONE);
            word6Button.setVisibility(View.GONE);
            word4Edittext.setImeOptions(EditorInfo.IME_ACTION_DONE);}
    }

    public void playWord1(View view) throws InterruptedException {
        wordName = rndFileNames[0];

      playWordWave();
    }
    public void playWord2(View view) throws InterruptedException {
        wordName = rndFileNames[1];
        playWordWave();
    }
    public void playWord3(View view) throws InterruptedException {
        wordName = rndFileNames[2];
        playWordWave();
    }
    public void playWord4(View view) throws InterruptedException {
        wordName = rndFileNames[3];
        playWordWave();
    }
    public void playWord5(View view) throws InterruptedException {
        wordName = rndFileNames[4];
        playWordWave();
    }
    public void playWord6(View view) throws InterruptedException {
        wordName = rndFileNames[5];
        playWordWave();
    }

    public void practiceWords(View view) {
        Intent intent = new Intent(EarfulWords.this, WordPractice.class);
        intent.putExtra("message1",wordFileName);

        // start the activity connect to the specified class
        startActivity(intent);

    }
    public void showHelp (View view)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(EarfulWords.this);
        builder.setTitle("HELP")
                .setMessage("This App selects groups of Words \n" +
                        "Press the Play Button\n" +
                        "Listen to the words. Repeat if You need to.\n" +
                        "Type in the words starting at the red Cursor.\n" +
                        "Press next after each Word.\n" +
                        "Press Check Results to see if You are correct.\n" +
                        "To listen to a word press the Word.\n" +
                        "If you choose Practice Words,\n" +
                        "You can listen to all the Words in the Group.\n" +
                        "You can add Backgroud noise by pressing Nature or Babble\n" +
                        "Adjust the Volume to suit You.")

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
    public void readWords() {
            int wordCounter = 0;
            String line;
            wordsRead = new String[100];
        try {
          InputStream fis = getAssets().open(wordFileName);
          //  InputStream fis = getAssets().open("junk.txt");
            // prepare the file for reading
            InputStreamReader chapterReader = new InputStreamReader(fis);
            BufferedReader buffreader = new BufferedReader(chapterReader);

            maxWords = 0;

            // read every line of the file into the line-variable, on line at the time
            do {
                line = buffreader.readLine();
               wordsRead[wordCounter] = line;
                wordCounter++;


            }
            while (line != null);

           maxWords = wordCounter -1;
            fis.close();
        // titleTextView.setText("lines = "  + maxWords);
            //   textViewSentence.setText("recs" +maxRecs);
            //   viewTheSentence.setText("recs read " + maxRecs);
            //  showSentence.setText("max recs = " + maxRecs);
            //    Toast.makeText(getApplicationContext(),sentences[0].length(),
            //    Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {


        }
    }

    public void writeLastFile() {
    String filename="lastfile.txt";
   // String data=editTextData.getText().toString();
    String data = "";
    FileOutputStream fos;
                try {
        File myFile = new File("/sdcard/"+filename);
        myFile.createNewFile();
        FileOutputStream fOut = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
        myOutWriter.append(data);
        myOutWriter.close();
        fOut.close();
        Toast.makeText(getApplicationContext(),filename + "saved",Toast.LENGTH_LONG).show();
    } catch (
    FileNotFoundException e) {e.printStackTrace();}
                catch (IOException e) {e.printStackTrace();}
}
public void readLastFile() {
    try {
        InputStream fis = getAssets().open("lastfile.txt");
        //  InputStream fis = getAssets().open("junk.txt");
        // prepare the file for reading
        InputStreamReader chapterReader = new InputStreamReader(fis);
        BufferedReader buffreader = new BufferedReader(chapterReader);



        // read every line of the file into the line-variable, on line at the time

            lastFileName = buffreader.readLine();

          fis.close();

    } catch (IOException ex) {


    }
}


    public void goEXIT(View view)
    {

        // finish();

        // Intents are objects of the android.content.Intent type. Your code can send them
        // to the Android system defining the components you are targeting.
        // Intent to start an activity called oneActivity with the following code:

        Intent intent = new Intent(EarfulWords.this, MainActivity.class);

        // start the activity connect to the specified class
        startActivity(intent);
    }
}