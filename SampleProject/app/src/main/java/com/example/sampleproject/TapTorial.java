package com.example.sampleproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleproject.SupportClasses.JamesUtilities;
import com.example.sampleproject.SupportClasses.TimeRecorder;


public class TapTorial extends AppCompatActivity {

   // String colorID = "";
    int i = 0;
    Button tapBtn;

    DBHelper aDB;
    String username;
    final String GAME_NAME = "Taptorial";
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        final TimeRecorder timeRecorder = new TimeRecorder(this);

        aDB = new DBHelper(TapTorial.this);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = sharedPref.getString(Constants.USERNAME_CURRENT, "Anonymous");

        tapBtn = findViewById(R.id.tappyTappy);
        int resColor = R.color.Press10;

        tapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                String tappy = (10 - i) + " more!";
                if (!(i >= 10))
                    tapBtn.setText(tappy);

                // colorID = "Press" + (10 - i);
                // int resColor= getResources().getIdentifier(R.color.Press(10-i);
                //   int resID = getResources().getIdentifier("com.example.sampleproject:color/"+colorID, null, null);
                //    tapBtn.setBackgroundColor(resID);

                if (i == 1) {
                    timeRecorder.startRecording();
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.Press9));
                } else if (i == 2) {
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.Press8));
                } else if (i == 3) {
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.Press7));
                } else if (i == 4) {
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.Press6));
                } else if (i == 5) {
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.Press5));
                } else if (i == 6) {
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.Press4));
                } else if (i == 7) {
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.Press3));
                } else if (i == 8) {
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.Press2));
                } else if (i == 9) {
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.Press1));
                }

                else if (i == 10) {
                    tapBtn.setText("✓");
                    tapBtn.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                    addUserScore(username,"TapTorial",(long) timeRecorder.getTime());
                    String score = JamesUtilities.formatMilliseconds((long) timeRecorder.getTime());
                    Toast.makeText(TapTorial.this, "Nice! Your time was " + score + ". Now go play the other games!",
                            Toast.LENGTH_LONG).show();
                    timeRecorder.stopAndResetTimer(false);
                }
                else
                {
                    Toast.makeText(TapTorial.this, "Press the back button to go back to the level list.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    //returns to Level List when back button is pressed
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,LevelGrid.class));

    }

    public void addUserScore (String username, String game, long score)
    {
        ContentValues val = new ContentValues();
        val.put("username", username);
        val.put("game", game);
        val.put("score", score);

        if(score<1000){
            msg="You are ready to become the Tappy master";
        }
        else if(score<4000 && score>1000){
            msg="Dang! You are very close to being a Tappy master";
        }else{
            msg="Don't lose hope! Not all Tappy masters wear capes...yet";
        }

        DBHelper.addUserScore(username, GAME_NAME, score,msg); //add score to DB (avg time recorded)
    }
}