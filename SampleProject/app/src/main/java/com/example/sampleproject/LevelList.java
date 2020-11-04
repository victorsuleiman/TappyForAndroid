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

    List<String> levelNames = new ArrayList<>(Arrays.asList("Level 1", "Level 2", "Tic Tac Toe", "SimonSays"));
    List<Integer> levelImg = new ArrayList<>(Arrays.asList(R.drawable.logo_main_activity,R.drawable.logo_main_activity,R.drawable.logo_main_activity,R.drawable.logo_main_activity)); //placeholder TODO: add images

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
                        startActivity(new Intent(LevelList.this, Level2.class));
                        break;
                    case 2:
                        startActivity(new Intent(LevelList.this, TicTacToe.class));
                        break;
                    case 3:
                        startActivity(new Intent(LevelList.this, HangMan.class));
                        break;

                    default:
                        Toast.makeText(LevelList.this, "Yo, something is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}