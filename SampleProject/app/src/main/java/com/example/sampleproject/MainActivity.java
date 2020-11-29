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
import com.example.sampleproject.Constants;

public class MainActivity extends AppCompatActivity
{
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

//        final Intent intent = new Intent(MainActivity.this,LevelList.class);

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
//                if (username.equals(""))
//                    Toast.makeText(MainActivity.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
//                else if (username.contains(" "))
//                    Toast.makeText(MainActivity.this, "Please enter a username without any spaces.", Toast.LENGTH_LONG).show();
//                else {
                    saveUser(username);
                    img.startAnimation(rotateAnim);
//                }

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

    }

    /*save username into shared pref*/
    public void saveUser(String username)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPref.edit(); //editor needed to put content in

        editor.putString(Constants.USERNAME_CURRENT, username);
        editor.commit();

        Toast.makeText(this, username + " written to sharedpref", Toast.LENGTH_SHORT).show();

    }

    public void loadData()
    {
        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        currentUser = sharedPref.getString(Constants.USERNAME_CURRENT, "Anonymous"); //if user not found, make username "Anonymous"
    }

    /*create main DB*/
    private void createDB()
    {
        try {
            DBHelper.tappyDB = openOrCreateDatabase("tappy.db", MODE_PRIVATE, null);

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
//            String dropUsersTableCmd = "DROP TABLE IF EXISTS " + "users;";
            String dropScoresTableCmd = "DROP TABLE IF EXISTS " + "scores;";

            //User table
//            String createUsersTableCmd = "CREATE TABLE users "
//                    + "(username TEXT PRIMARY KEY, fullName TEXT, email TEXT);";

            //Score table
            String createScoresTableCmd = "CREATE TABLE scores "
                    + "(username TEXT, game TEXT, score REAL, info TEXT);";


            DBHelper.tappyDB.execSQL(setPRAGMAForeignKeysOn);
            DBHelper.tappyDB.execSQL(dropScoresTableCmd);
//            tappyDB.execSQL(dropUsersTableCmd); //cannot be dropped first because of user table reference

//            tappyDB.execSQL(createUsersTableCmd);
            DBHelper.tappyDB.execSQL(createScoresTableCmd);

            Log.d("DB Tappy", "Tables created");
        }
        catch (Exception ex)
        {
            Log.e("DB Tappy", "Error creating table" + ex.getMessage());
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