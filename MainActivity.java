package com.example.earfull1;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button playButton,menuButton;
    private String stringTitle,wordMenuChoice,message1;
    Boolean activityFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        menuButton = findViewById(R.id.menuButton);

    }
    public void showMenu(View v) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(MainActivity.this, menuButton);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.options_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                wordMenuChoice = item.toString();
                switch (wordMenuChoice) {
                    case ("Names"):
                        message1 = "names.txt";
                        gotoWords();

                    break;
                    case ("Words No 1"):
                        message1 = "w1words.txt";
                        gotoWords();

                    break;
                    case ("Med-El Matrix 1"):
                        message1 = "mat1words.txt";
                        gotoMatrix1();

                    break;

                    case ("Med-El Matrix 2"):
                        message1 = "mat2words.txt";
                        gotoMatrix2();

                        break;
                }
                return true;
            }
        });


        popup.show();//showing popup menu
    }

    public void gotoMatrix1(){
        {

            // Intents are objects of the android.content.Intent type. Your code can send them
            // to the Android system defining the components you are targeting.
            // Intent to start an activity called SecondActivity with the following code:
            String stringRecNo;
            activityFlag = true;
            //  stringRecNo = "Selected Sentence = " + disgotoRecNo;
            Intent intent = new Intent(MainActivity.this, Matrix1.class);
            intent.putExtra("message1",message1);

            startActivity(intent);
        }
    }
    public void gotoMatrix2() {
        {

            // Intents are objects of the android.content.Intent type. Your code can send them
            // to the Android system defining the components you are targeting.
            // Intent to start an activity called SecondActivity with the following code:
            String stringRecNo;
            activityFlag = true;
            //  stringRecNo = "Selected Sentence = " + disgotoRecNo;
            Intent intent = new Intent(MainActivity.this, Matrix2.class);
            intent.putExtra("message1", message1);

            startActivity(intent);
        }
    }
    public void gotoWords(){
        {

            // Intents are objects of the android.content.Intent type. Your code can send them
            // to the Android system defining the components you are targeting.
            // Intent to start an activity called SecondActivity with the following code:
            String stringRecNo;
            activityFlag = true;
          //  stringRecNo = "Selected Sentence = " + disgotoRecNo;
            Intent intent = new Intent(MainActivity.this, EarfulWords.class);
          intent.putExtra("message1",message1);

            startActivity(intent);
        }
    }
    public void gotoLing(View view){
        {

            // Intents are objects of the android.content.Intent type. Your code can send them
            // to the Android system defining the components you are targeting.
            // Intent to start an activity called SecondActivity with the following code:
            String stringRecNo;
            activityFlag = true;
            //  stringRecNo = "Selected Sentence = " + displayRecNo;
            Intent intent = new Intent(MainActivity.this, Ling6.class);

            startActivity(intent);
        }
    }
}
