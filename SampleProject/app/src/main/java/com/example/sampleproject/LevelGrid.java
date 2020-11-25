package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.sampleproject.Adapters.GridAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelGrid extends AppCompatActivity {
    List<Integer> lvlImg = new ArrayList<>(Arrays.asList(R.drawable.touch,R.drawable.tictactoe,
            R.drawable.hangmangame,R.drawable.headphones,R.drawable.foursquares,R.drawable.apps));
    List<String> levelName=new ArrayList<>(Arrays.asList("Tap","TIC TAP TOE","TapMan",
            "Tap That Song","Simon Tap","Reaction Tap"));



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_grid);


        GridView gridViewImages = findViewById(R.id.gridView);

        gridViewImages.setAdapter(new GridAdapter(levelName,lvlImg));
        gridViewImages.setHorizontalSpacing(20);
        gridViewImages.setVerticalSpacing(20);
        gridViewImages.setNumColumns(2);



    }


}