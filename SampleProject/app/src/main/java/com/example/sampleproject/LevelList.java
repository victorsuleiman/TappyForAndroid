package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sampleproject.Adapters.LevelListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelList extends AppCompatActivity {

    List<String> levelNames = new ArrayList<>(Arrays.asList("Level 1", "Name That Song", "Tic Tac Toe", "SimonSays","HangMan",
            "Level 2","Reaction Tap"));
    List<Integer> levelImg = new ArrayList<>(Arrays.asList(
            R.drawable.logo_main_activity, //Level 1
            R.drawable.nts_logo, //Name that song
            R.drawable.logo_main_activity, //Tic Tac Toe
            R.drawable.logo_main_activity, //Simons Says
            R.drawable.hangman_winpose2, //Hangman
            R.drawable.logo_main_activity, //Level 2
            R.drawable.logo_main_activity //Reaction tap
    )); //placeholder TODO: add images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);

        //creates ListView object, Adapter object then linking the two
        ListView listViewLevel = findViewById(R.id.listViewLevels);
        LevelListAdapter levelAdapter = new LevelListAdapter(levelNames, levelImg);
        listViewLevel.setAdapter(levelAdapter);

        listViewLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                switch(i)
                {
                    case 0:
                        startActivity(new Intent(LevelList.this, Level1.class));
                        break;
                    case 1:
                        startActivity(new Intent(LevelList.this, NameThatSong.class));
                        break;
                    case 2:
                        startActivity(new Intent(LevelList.this, TicTacToe.class));
                        break;
                    case 3:
                        startActivity(new Intent(LevelList.this, SimonSays.class));
                        break;
                    case 4:
                        startActivity(new Intent(LevelList.this, HangMan.class));
                        break;
                    case 5:
                        startActivity(new Intent(LevelList.this, Level2.class));
                        break;
                    case 6:
                        startActivity(new Intent(LevelList.this,ReactionTap.class));
                        break;
                    default:
                        Toast.makeText(LevelList.this, "Yo, something is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //returns to Level List when back button is pressed
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,MainActivity.class));

    }
}