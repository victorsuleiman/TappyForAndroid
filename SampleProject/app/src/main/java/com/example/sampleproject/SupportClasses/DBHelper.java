package com.example.sampleproject.SupportClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.sampleproject.Models.Score;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{
    public static SQLiteDatabase tappyDB;
    public static final String TABLE_NAME = "scores";


    public DBHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, 1);
    }


    // Called on creation for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase tappyDB) {

        String createScoresTableCmd =
                "CREATE TABLE " + TABLE_NAME
                + " (username TEXT, " +
                    "game TEXT, " +
                    "score REAL, " +
                    "info TEXT);";

        tappyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        tappyDB.execSQL(createScoresTableCmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // if the two DB version doesn't
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);
        }
    }

    public List<Score> browseScoreRecs()
    {
        List<Score> allScores = new ArrayList<Score>();
        String queryStr = "SELECT * FROM scores;";

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

    public List<Score> browseScoreRecsByName()
    {
        List<Score> allScores = new ArrayList<Score>();
        String queryStr = "SELECT * FROM scores ORDER BY username;";

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

    public List<Score> browseScoreRecsByGame()
    {
        List<Score> allScores = new ArrayList<Score>();
        String queryStr = "SELECT * FROM scores ORDER BY game;";

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



//    public List<String[]> browseScoresRecs()
//    {
//        List<String[]> ScoreList = new ArrayList<>();
//
//        String[] headRec = new String[3];
//        headRec[0] = "username";
//        headRec[1] = "game";
//        headRec[2] = "score";
//
//        ScoreList.add(headRec);
//
//        String queryStr = "SELECT * FROM scores";
//
//        try {
//            Cursor cursor = tappyDB.rawQuery(queryStr, null);
//            if (cursor != null)
//            {
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast())
//                {
//                    String[] eachRecArray = new String[3];
//                    eachRecArray[0] = cursor.getString(0); //correspond to username
//                    eachRecArray[1] = cursor.getString(1); // game
//                    eachRecArray[2] = cursor.getString(2); // score
//
//                    ScoreList.add(eachRecArray);
//                    cursor.moveToNext();
//                }
//            }
//        }
//        catch (Exception ex)
//        {
//            Log.d("DB Tappy", "Querying user  recs error " + ex.getMessage());
//        }
//
//        return ScoreList;
//    } //ends browse Scores

    public static void addUserScore (String username, String game, long score)
    {
        long result = 0;
        ContentValues val = new ContentValues();
        val.put("username", username);
        val.put("game", game);
        val.put("score", score);

        result = tappyDB.insert("scores", null, val);

        if (result != -1)
        {
            Log.d("DB Tappy", "Added score for user " + username );
        }
        else
        {
            Log.d("DB Tappy", "Error adding score for user " + username );
        }
    }

    public static void addUserScore (String username, String game, long score, String info)
    {
        long result = 0;
        ContentValues val = new ContentValues();
        val.put("username", username);
        val.put("game", game);
        val.put("score", score);
        val.put("info", info);

        result = tappyDB.insert("scores", null, val);

        if (result != -1)
        {
            Log.d("DB Tappy", "Added score for user " + username );
        }
        else
        {
            Log.d("DB Tappy", "Error adding score for user " + username );
        }
    }


}

