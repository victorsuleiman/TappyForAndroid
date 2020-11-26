package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME_CURRENT = "Username";

    SQLiteDatabase tappyDB;

    ImageView img;
    ImageButton buttonToScore;
    Animation rotateAnim;
    EditText editText;
    String username;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonToScore = findViewById(R.id.buttonScoreList);
        editText=findViewById(R.id.userName);
        img=findViewById(R.id.playButton);
        img.setClickable(true);
        rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);

        createDB();
        createTables();

        final Intent intent=new Intent(MainActivity.this,LevelGrid.class);

        buttonToScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HighScores.class));
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText.getText().toString();
                if (username.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
                else if (username.contains(" "))
                    Toast.makeText(MainActivity.this, "Please enter a username without any spaces.", Toast.LENGTH_LONG).show();
                else {
                    saveUser(username);
                    img.startAnimation(rotateAnim);
                }

            }
        });

        rotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

//        Bundle bundle = new Bundle();
//        bundle.getString("UserName",editText.getText().toString());
//        intent.putExtras(bundle);

    }

    /*save username into shared pref*/
    public void saveUser(String username)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPref.edit(); //editor needed to put content in

        editor.putString(USERNAME_CURRENT, username);
        editor.commit();

        Toast.makeText(this, username + " written to sharedpref", Toast.LENGTH_SHORT).show();
        //TODO: check if existing user or not
    }

    public void loadData()
    {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        currentUser = sharedPref.getString(USERNAME_CURRENT, "Anonymous"); //if user not found, make username "Anonymous"
    }

    /*create main DB*/
    private void createDB()
    {
        try {
            tappyDB = openOrCreateDatabase("tappy.db", MODE_PRIVATE, null);

            Log.d("DB Tappy", "DB created");
        }
        catch (Exception ex)
        {
            Log.e("DB Tappy", ex.getMessage());
        }
    }


    private void createTables()
    {
        try
        {
            String setPRAGMAForeignKeysOn = "PRAGMA foreign_keys = ON;";

            //Drop tables if exists
            String dropUsersTableCmd = "DROP TABLE IF EXISTS " + "users;";
            String dropScoresTableCmd = "DROP TABLE IF EXISTS " + "scores;";

            //User table
//            String createUsersTableCmd = "CREATE TABLE users "
//                    + "(username TEXT PRIMARY KEY, fullName TEXT, email TEXT);";

            //Score table
            String createScoresTableCmd = "CREATE TABLE scores "
                    + "(username TEXT, game TEXT, score REAL);";


            tappyDB.execSQL(setPRAGMAForeignKeysOn);
            tappyDB.execSQL(dropScoresTableCmd);
//            tappyDB.execSQL(dropUsersTableCmd); //cannot be dropped first because of user table reference

//            tappyDB.execSQL(createUsersTableCmd);
            tappyDB.execSQL(createScoresTableCmd);

            Log.d("DB Tappy", "Tables created");
        }
        catch (Exception ex)
        {
            Log.e("DB Tappy", "Error creating table" + ex.getMessage());
        }

    }

    public List<String[]> browseScoresRecs()
    {
        List<String[]> ScoreList = new ArrayList<>();

        String[] headRec = new String[3];
        headRec[0] = "username";
        headRec[1] = "game";
        headRec[2] = "score";

        ScoreList.add(headRec);

        String queryStr = "SELECT * FROM scores";

        try {
            Cursor cursor = tappyDB.rawQuery(queryStr, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    String[] eachRecArray = new String[3];
                    eachRecArray[0] = cursor.getString(0); //correspond to username
                    eachRecArray[1] = cursor.getString(1); // game
                    eachRecArray[2] = cursor.getString(2); // score

                    ScoreList.add(eachRecArray);
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception ex)
        {
            Log.d("DB Tappy", "Querying user  recs error " + ex.getMessage());
        }

        return ScoreList;
    } //ends browse Scores

    public void addUserScore (String username, String game, long score)
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


    //return home on back pressed
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}