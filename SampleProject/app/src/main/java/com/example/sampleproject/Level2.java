package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sampleproject.SupportClasses.TimeRecorder;

import java.util.Timer;
import java.util.TimerTask;

public class Level2 extends AppCompatActivity {

    TimeRecorder timeRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);

        timeRecorder = new TimeRecorder(this);

        Button startTimerButton = findViewById(R.id.level2Btn);
        Button stopTimerButton = findViewById(R.id.level2BtnStopTimer);


        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!timerStarted) {
//                    timerStarted = true;
//                    startTimer();
//                }
                timeRecorder.startRecording();
            }
        });

        stopTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                stopTimer();
                timeRecorder.stopAndResetTimer(true);
            }
        });
    }

}