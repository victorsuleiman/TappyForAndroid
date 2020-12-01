package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sampleproject.Adapters.GridAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelGrid extends AppCompatActivity {
    List<Integer> lvlImg = new ArrayList<>(Arrays.asList(R.drawable.touch,R.drawable.tictactoe,
            R.drawable.hangmangame,R.drawable.headphones,R.drawable.foursquares,R.drawable.apps));
    List<String> levelName=new ArrayList<>(Arrays.asList("TapTorial","Tic Tap Toe","TapMan",
            "Tap That Song","Simon Tap","Reaction Tap"));



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_grid);


        GridView gridViewImages = findViewById(R.id.gridView);

        gridViewImages.setAdapter(new GridAdapter(levelName,lvlImg));
        gridViewImages.setHorizontalSpacing(20);
        gridViewImages.setVerticalSpacing(40);
        gridViewImages.setNumColumns(2);

        gridViewImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                case 0:
                startActivity(new Intent(LevelGrid.this, TapTorial.class));
                break;
                case 1:
                startActivity(new Intent(LevelGrid.this, TicTacToe.class));
                break;
                case 2:
                startActivity(new Intent(LevelGrid.this, HangMan.class));
                break;
                case 3:
                startActivity(new Intent(LevelGrid.this, NameThatSong.class));
                break;
                case 4:
                startActivity(new Intent(LevelGrid.this, SimonSays.class)); //TODO: change back to simon says
                break;
                case 5:
                startActivity(new Intent(LevelGrid.this,ReactionTap.class));
                break;
                default:
                Toast.makeText(LevelGrid.this, "Yo, something is wrong", Toast.LENGTH_SHORT).show();
            }}


        });

    }


    //returns to Level List when back button is pressed
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }
}