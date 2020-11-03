package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class HangMan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_man);

        final String[] wordsAnimal=new String[]{"MOKNEY","RHINO","CAT","DOG","BIRD","HIPPO","COW","HORSE","WHALE",
        "TURTLE","FISH","GOAT","RABBIT","SNAKE"};
        final String[] wordsActivities=new String[]{"SKI","RUN","JUMP","SING","DANCE","WRITE","BIKE","DRIVE","BIKE",
        "DRIVE","PARTY","EAT","DRINK","CLEAN","STUDY","DIE"};
        final String[] words2020=new String[]{"QUARANTINE","COVID","CORONA","WWIII","BLACK LIVES MATTER"};

        final Random random=new Random();
        final int MAX_ERRORS = 6;
        String wordChosen;
        char[] lettersChosen;
        ArrayList<String> letters = new ArrayList<>();
         ImageView img;
         TextView wordTv;
         TextView wordToFindTv;




    }
}