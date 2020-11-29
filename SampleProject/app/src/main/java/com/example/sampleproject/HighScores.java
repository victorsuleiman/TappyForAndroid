package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleproject.Adapters.LevelListAdapter;
import com.example.sampleproject.Adapters.ScoreAdapter;
import com.example.sampleproject.Models.Score;
import com.example.sampleproject.SupportClasses.JamesUtilities;

import java.util.ArrayList;
import java.util.List;

//show all scores saved in DB, use a ListView perhaps?
public class HighScores extends AppCompatActivity {

//    SQLiteDatabase tappyDB;
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

        aDB = new DBHelper(HighScores.this);
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
                        Toast.makeText(HighScores.this, "Invalid option selected", Toast.LENGTH_SHORT).show();
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


//    private List<Score> browseGradeRecs()
//    {
//        List<Score> allScores = new ArrayList<Score>();
//        String queryStr = "SELECT * FROM scores;";
//
////        Score headRec = new Score("Username", "Game Name", "Score", "Additional Info");
////
//        //TODO: work out headRec
//
//        try
//        {
//            Cursor cursor = tappyDB.rawQuery(queryStr, null);
//            if (cursor != null)
//            {
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast())
//                {
//                    Score eachScore = new Score(cursor.getString(0), cursor.getString(1), cursor.getLong(2), cursor.getString(3));
//                    allScores.add(eachScore);
//                    cursor.moveToNext();
//                }
//            }
//            return allScores;
//        }
//        catch (Exception ex)
//        {
//            Log.d("DB Tappy", "Querying score error " + ex.getMessage());
//            return null;
//        }
//    }

//    private void browseGradeRecsLegacy()
//    {
//        String queryStr = "SELECT * FROM scores;";
//
//        String headRec = String.format("%-15s%-15s%-15s%-15s\n", "Username", "Game", "Score", "Additional info");
//
//        outputText.append(headRec);
//
//        try
//        {
//            Cursor cursor = tappyDB.rawQuery(queryStr, null);
//            if (cursor != null)
//            {
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast())
//                {
//                    String eachRec = String.format("%-15s%-15s%-15s%-15s\n",
//                            cursor.getString(0), cursor.getString(1), JamesUtilities.formatMilliseconds(cursor.getLong(2)), cursor.getString(3));
//                    outputText.append(eachRec);
//                    cursor.moveToNext();
//                }
//            }
//        }
//        catch (Exception ex)
//        {
//            Log.d("DB Tappy", "Querying score error " + ex.getMessage());
//        }
//    }
}