package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

    List<Integer> tuneListPic = new ArrayList<>(Arrays.asList(R.drawable.hangman_lvl0,
            R.drawable.hangman_lvl1,R.drawable.hangman_lvl2,R.drawable.hangman_lvl3,R.drawable.hangman_lvl4,R.drawable.hangman_lvl5,R.drawable.hangman_lvl6));

    final int MAX_ERRORS = 6;
    String wordChosen;
    ArrayList<String> letters = new ArrayList<>();
    ImageView img;
    TextView dashBox;
    TextView msgBox;
    String dash="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_man);


        category = findViewById(R.id.categorySpin);
        dashBox=findViewById(R.id.dashBox);
        msgBox=findViewById(R.id.msgBox);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              //  Toast.makeText(HangMan.this, "Kar kon", Toast.LENGTH_SHORT).show();

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
                dashBox.setText(dash);
                msgBox.setText(wordChosen);

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

    public void touchLetter(View view) {
        Button btn=(Button)view;
        String dash2="";
        Toast.makeText(this, "Letter entered "+btn.getText(), Toast.LENGTH_LONG).show();
        char letterChosen=btn.getText().charAt(0);
        for(int i=0;i<wordChosen.length();i++){
            if(letterChosen==wordChosen.charAt(i)){
                dash2=dash2+letterChosen;
            }else{
                dash2=dash2+dash.charAt(i);
            }
        }
        dash=dash2;
        dashBox.setText(dash);





    }


}