package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sampleproject.SupportClasses.JamesUtilities;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.example.sampleproject.ReactionGraph.series;
import static com.example.sampleproject.ReactionTap.MyState.*;

public class ReactionTap extends AppCompatActivity {

    /*for DB*/
    String username;
    DBHelper aDB;
    final String GAME_NAME = "Reaction Tap";

    enum MyState
    {
        waitState ,
        clickState,
        reactionState,
    }
    MyState myState= waitState;
    Button buttonReaction;
    final int[] time=new int[]{3000,4000,5000,6000,7000};
    long startTime;
    long endTime;
    int rnd;
    final int LIMIT=5;
    int counter=1;
    List<Long> allTimes = new ArrayList<Long>(); //List that contains all times


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_tap);

        aDB = new DBHelper(ReactionTap.this);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = sharedPref.getString(Constants.USERNAME_CURRENT, "Anonymous"); //if user not found, make username "Anonymous"

        //create LineGraphSeries object
        series = new LineGraphSeries<>();
        series.appendData(new DataPoint(0, 0), true, 20);

        buttonReaction = findViewById(R.id.buttonReaction);

        //onClick for reaction button
        buttonReaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (myState) {
                    case waitState:
                        waitMethod();
                        break;
                    case clickState:
                        clickMethod();
                        break;
                    case reactionState:
                        reactionMethod();
                        break;
                }
            }
        });

    }
    public void waitMethod () {
        buttonReaction.setBackgroundColor(getResources().getColor(R.color.Red));
        buttonReaction.setEnabled(false);
        rnd = new Random().nextInt(time.length);
        buttonReaction.setText(R.string.wait);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            myState = clickState;
            clickMethod();
            }
        }, time[rnd]);

    }
    public void clickMethod () {
        buttonReaction.setBackgroundColor(getResources().getColor(R.color.Green));
        buttonReaction.setEnabled(true);
        buttonReaction.setText(R.string.click);
        myState = reactionState;
        startTime = System.currentTimeMillis();


    }
    public void reactionMethod () {
        buttonReaction.setBackgroundColor(getResources().getColor(R.color.Blue));
        endTime = System.currentTimeMillis();
        String str = getResources().getString(R.string.clickagain);
        long showTime = (endTime - startTime);
        buttonReaction.setText(str + "\n" + showTime + " ms");
        myState = waitState;

        allTimes.add(showTime);
        series.appendData(new DataPoint(counter, showTime), true, 20); //add data


        counter++;

        if (counter == LIMIT) {
            long minTime = Collections.min(allTimes);
            long avgTime = JamesUtilities.getAvg(allTimes);
            Bundle bundle=new Bundle();
            bundle.putLong("minTime",minTime);
            bundle.putLong("avgTime",avgTime);

            Toast.makeText(this, minTime +" "+ avgTime, Toast.LENGTH_SHORT).show();
            String additionalInfo = "Best time: " + minTime + "ms, average time: " + avgTime + "ms";

            DBHelper.addUserScore(username, GAME_NAME, avgTime, additionalInfo); //add score to DB (avg time recorded)

            Intent intent=new Intent(ReactionTap.this, ReactionGraph.class);
            intent.putExtras(bundle);
            startActivity(intent);

        }


    }

    //returns to Level List when back button is pressed
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,LevelGrid.class));
    }
}

