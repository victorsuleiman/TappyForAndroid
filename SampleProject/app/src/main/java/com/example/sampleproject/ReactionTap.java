package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReactionTap extends AppCompatActivity {
    Button reactionStart,tapButton;
    TextView reactionTime;
    long startTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_tap);

        reactionStart=findViewById(R.id.reactionStart);
        tapButton=findViewById(R.id.tapButton);
        reactionTime=findViewById(R.id.reactionTime);

        reactionStart.setEnabled(true);
        tapButton.setEnabled(false);

        reactionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                },2000);
            }
        });

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}