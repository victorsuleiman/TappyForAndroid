package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sampleproject.Adapters.LevelListAdapter;
import com.example.sampleproject.Adapters.ScoreAdapter;
import com.example.sampleproject.Models.Score;
import com.example.sampleproject.SupportClasses.JamesUtilities;

import java.util.ArrayList;
import java.util.List;

//show all scores saved in DB, use a ListView perhaps?
public class HighScores extends AppCompatActivity {

    SQLiteDatabase tappyDB;
    StringBuilder outputText = new StringBuilder();
    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        scoreTextView = findViewById(R.id.scoreResults);

        openDB();

        browseGradeRecsLegacy();

        ListView listViewScore = findViewById(R.id.scoresListView);
        ScoreAdapter aScoreAdapter = new ScoreAdapter(browseGradeRecs());
        listViewScore.setAdapter(aScoreAdapter);

        scoreTextView.setText(outputText.toString());

    }



    private void openDB()
    {
        try
        {
            tappyDB = openOrCreateDatabase("tappy.db", MODE_PRIVATE, null);
        }
        catch (Exception e)
        {
            Log.d("DB Tappy", "Database opening error" + e.getMessage());
        }
    }

    private List<Score> browseGradeRecs()
    {
        List<Score> allScores = new ArrayList<Score>();
        String queryStr = "SELECT * FROM scores;";

//        Score headRec = new Score("Username", "Game Name", "Score", "Additional Info");
//
        //TODO: work out headRec

        try
        {
            Cursor cursor = tappyDB.rawQuery(queryStr, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    Score eachScore = new Score(cursor.getString(0), cursor.getString(1), cursor.getLong(2), cursor.getString(3));
                    allScores.add(eachScore);
                    cursor.moveToNext();
                }
            }
            return allScores;
        }
        catch (Exception ex)
        {
            Log.d("DB Tappy", "Querying score error " + ex.getMessage());
            return null;
        }
    }

    private void browseGradeRecsLegacy()
    {
        String queryStr = "SELECT * FROM scores;";

        String headRec = String.format("%-15s%-15s%-15s%-15s\n", "Username", "Game", "Score", "Additional info");

        outputText.append(headRec);

        try
        {
            Cursor cursor = tappyDB.rawQuery(queryStr, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    String eachRec = String.format("%-15s%-15s%-15s%-15s\n",
                            cursor.getString(0), cursor.getString(1), JamesUtilities.formatMilliseconds(cursor.getLong(2)), cursor.getString(3));
                    outputText.append(eachRec);
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception ex)
        {
            Log.d("DB Tappy", "Querying score error " + ex.getMessage());
        }
    }
}