package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HangMan extends AppCompatActivity {
    Spinner category;
    final String[] wordsAnimal = new String[]{"MOKNEY", "RHINO", "CAT", "DOG", "BIRD", "HIPPO", "COW", "HORSE", "WHALE",
            "TURTLE", "FISH", "GOAT", "RABBIT", "SNAKE"};
    final String[] wordsActivities = new String[]{"SKI", "RUN", "JUMP", "SING", "DANCE", "WRITE", "BIKE", "DRIVE", "BIKE",
            "DRIVE", "PARTY", "EAT", "DRINK", "CLEAN", "STUDY", "DIE"};
    final String[] words2020 = new String[]{"QUARANTINE", "COVID", "CORONA", "WWIII", "BLACK LIVES MATTER"};

    String[] button=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U"
                                  ,"V","W","X","Y","Z"};

    List<Integer> hangManPics = new ArrayList<>(Arrays.asList(R.drawable.hangman_lvl0,
            R.drawable.hangman_lvl1,R.drawable.hangman_lvl2,R.drawable.hangman_lvl3,
            R.drawable.hangman_lvl4,R.drawable.hangman_lvl5,R.drawable.hangman_lvl6));



    String wordChosen;
    ImageView hangManImg;
    TextView dashBox;
    TextView msgBox;
    String dash="";
    int tryCounter = 0; //amount of times the user guessed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_man);


        hangManImg = findViewById(R.id.hangManImg);
        category = findViewById(R.id.categorySpin);
        dashBox=findViewById(R.id.dashBox);
        msgBox=findViewById(R.id.msgBox);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              //  Toast.makeText(HangMan.this, "Kar kon", Toast.LENGTH_SHORT).show();
                //reset game when changed
                dash =""; //reset dash board
                //reset picture
                hangManImg.setImageResource(R.drawable.hangman_lvl0);
                //reset tries
                tryCounter = 0;
                //reset message box
                msgBox.setText("");
                letterColorBack(button);
                switch (position) {
                    case 0:
                        wordChosen="";
                        Toast.makeText(HangMan.this, "Please choose a category", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                       wordChosen= getRandom(wordsAnimal);
                        break;
                    case 2:
                        wordChosen=getRandom(wordsActivities);
                        break;
                    case 3:
                        wordChosen=getRandom(words2020);
                        break;
                }

                for(int i=0;i<wordChosen.length();i++){
                    dash=dash+"_";


                }
                String displayText = interlace(dash);
                dashBox.setText(displayText);
               // msgBox.setText(wordChosen);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static String interlace(String aString)
    {
        String interlaced = " ";
        for (int i = 0; i < aString.length(); i++)
        {
            interlaced += aString.charAt(i) + " ";
        }
        return interlaced;
    }

    public void touchLetter(View view) {
        Button btn=(Button)view;
        String dash2="";
       // Toast.makeText(this, "Letter entered "+btn.getText(), Toast.LENGTH_LONG).show();
        char letterChosen=btn.getText().charAt(0);
        boolean correct = false; //initially false

        //only run code if there are still tries left
        if (tryCounter > 5) //if out of tries (check for lose)
        {
            msgBox.setText("Game over Bruh!");

        }
        else //still have tries
        {
            for (int i = 0; i < wordChosen.length(); i++) {
                if (letterChosen == wordChosen.charAt(i)) {
                    dash2 = dash2 + letterChosen;
                    correct = true; //user did get the correct letter


                } else {
                    dash2 = dash2 + dash.charAt(i);
                    btn.setBackgroundColor(getResources().getColor(R.color.lightRed));
                }
            }

            //if user guessed wrong
            if (correct == false)
            {
                tryCounter++;
                int imgIs = hangManPics.get(tryCounter);
                hangManImg.setImageResource(imgIs);
                Toast.makeText(this, "Wrong letter" , Toast.LENGTH_SHORT).show();
            }
            else //user guessed right
            {
                if (dash2.indexOf("_") == -1) //no _ means we've won!
                {
                    //Win condition
                    hangManImg.setImageResource(R.drawable.hangman_winpose);
                    msgBox.setText("You Won!");
                }
            }

            dash = dash2;
            String displayText = interlace(dash);
            dashBox.setText(displayText);
        } //end else (still have tries)
    }


    public void letterColorBack(String[] letters){
        Button[] letterButton = new Button[26];

        for(int i=0;i<letters.length;i++) {
            String buttonNumber = "button" + letters[i];
            int buttonId=getResources().getIdentifier(buttonNumber,"id",getPackageName());
            Log.e("TAG", "button "+buttonId);
            letterButton[i]=findViewById(buttonId);
            letterButton[i].setBackgroundColor(getResources().getColor(R.color.buttonHangMan));

        }


   }

}
