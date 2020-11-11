package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleproject.Adapters.ScoreAdapter;
import com.example.sampleproject.Models.Score;
import com.example.sampleproject.SupportClasses.DBHelper;

import java.util.ArrayList;
import java.util.List;

//show all scores saved in DB, use a ListView perhaps?
public class ViewScores extends AppCompatActivity {

    DBHelper aDB;
    TextView scoreTextView;
    Spinner sortBySpinner;
    ListView listViewScore;
    ScoreAdapter aScoreAdapter;
    List<Score> scoreList = new ArrayList<Score>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        aDB = new DBHelper(ViewScores.this);
        scoreTextView = findViewById(R.id.scoreResults);
        sortBySpinner = findViewById(R.id.scoreSortSpinner);

        scoreList= aDB.browseScoreRecs();

        listViewScore = findViewById(R.id.scoresListView);


        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        scoreList = aDB.browseScoreRecs();
                        break;
                    case 1:
                        scoreList = aDB.browseScoreRecsByName();
                        break;
                    case 2:
                        scoreList = aDB.browseScoreRecsByGame();
                        break;
                    default:
                        Toast.makeText(ViewScores.this, "Invalid option selected", Toast.LENGTH_SHORT).show();
                    break;
                }
                aScoreAdapter.notifyDataSetChanged();
                listViewScore.invalidateViews();
                aScoreAdapter = new ScoreAdapter(scoreList);
                listViewScore.setAdapter(aScoreAdapter);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        aScoreAdapter = new ScoreAdapter(scoreList);
        listViewScore.setAdapter(aScoreAdapter);
    }
}