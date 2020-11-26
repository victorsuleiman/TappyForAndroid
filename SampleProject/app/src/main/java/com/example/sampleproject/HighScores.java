package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

        outputText.append("Something is going on weeeee \n");
        browseGradeRecs();

        scoreTextView.setText(outputText.toString());

    }

    private void openDB()
    {
        try {
            tappyDB = openOrCreateDatabase("tappy.db", MODE_PRIVATE, null);
        }
        catch (Exception e)
        {
            Log.d("DB Tappy", "Database opening error" + e.getMessage());
        }
    }

    private void browseGradeRecs()
    {
        String queryStr = "SELECT * FROM scores;";

        String headRec = String.format("%-15s%-15s%-15s\n", "Username", "Game", "Score");

        outputText.append(headRec);

        try
        {
            Cursor cursor = tappyDB.rawQuery(queryStr, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    String eachRec = String.format("%-15s%-15s%-15s\n",
                            cursor.getString(0), cursor.getString(1), cursor.getLong(2));
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