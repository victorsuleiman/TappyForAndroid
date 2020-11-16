package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class ReactionTap extends AppCompatActivity {
    Button reactionStart,tapButton;
    TextView reactionTime;
    long startTime;
    long endTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_tap);

        reactionStart=findViewById(R.id.reactionStart);
        tapButton=findViewById(R.id.tapButton);
        reactionTime=findViewById(R.id.reactionTime);

        reactionStart.setEnabled(true);
        tapButton.setEnabled(false);
        final int[] time=new int[]{3000,4000,5000,6000,7000};
        final int rnd = new Random().nextInt(time.length);


        reactionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reactionTime.setText("");
                tapButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightRed));
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTime=System.currentTimeMillis();
                        tapButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.Press3));
                        tapButton.setText("TAP ME!");
                        reactionStart.setEnabled(false);
                        tapButton.setEnabled(true);

                    }
                },time[rnd]);
            }
        });

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: 3
                endTime=System.currentTimeMillis();
                reactionTime.setText((endTime-startTime)+"");
                tapButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightBlue));
            }
        });

    }
}