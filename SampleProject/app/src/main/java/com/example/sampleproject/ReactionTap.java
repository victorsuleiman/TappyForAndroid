package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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

        reactionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },2000);
            }
        });

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime=System.currentTimeMillis();
            }
        });

    }
}