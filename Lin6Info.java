package com.example.earfull1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Lin6Info extends AppCompatActivity {
private String info;
private TextView infotextView;
private Button exitButton,helpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_lin6_info);

    infotextView = findViewById(R.id.infotextView);
    exitButton = findViewById(R.id.exitButton);
    helpButton = findViewById(R.id.helpButon);
    readInfo();
    infotextView.setText(info);
    }
    public void goHelp (View view)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(Lin6Info.this);
      //  builder.setTitle("HELP")
          builder      .setMessage("Press LISTEN , You can listen to all the sounds \n" +
                        "Press PLAY\n" +
                        "The app will play the 6 sounds .\n" +
                        "chosen in a random order \n" +
                        "When a sound has played\n" +
                        "press the sound You think you heard\n" +
                        "if You are correct the Button will show green\n" +
                        "if you are wrong it will show red\n" +
                        "the PLAY button will show REPEAT\n" +
                        "When you press the correct sound button\n" +
                        "it will show green and you can continue\n" +
                        "untill You have ALL CORRECT.")

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

    public void readInfo() {
        StringBuffer buffer = new StringBuffer();
        try {
            InputStream fis = getAssets().open("ling6test.txt");

            //  FileInputStream fis = new FileInputStream("africaans10.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF8");
            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1) {
                buffer.append((char) ch);
            }
            in.close();
            info = buffer.toString();


        } catch (IOException ex) {
            // textViewSentence.setText(ex.toString());

        }
    }
        public void goEXIT(View view)
        {

            // finish();

            // Intents are objects of the android.content.Intent type. Your code can send them
            // to the Android system defining the components you are targeting.
            // Intent to start an activity called oneActivity with the following code:

            Intent intent = new Intent(Lin6Info.this, Ling6.class);

            // start the activity connect to the specified class
            startActivity(intent);
        }
}
