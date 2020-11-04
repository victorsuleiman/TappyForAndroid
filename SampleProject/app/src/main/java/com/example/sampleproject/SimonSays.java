package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SimonSays extends AppCompatActivity {

    //Pseudo-code for what I will try to do:
    /*
    An array of 7 buttons. Each position will represent one button that will be highlighted in order, from 1 to 7.
    With every onClickListener, I need to check if the user is pressing the button in the right order.

    If timer's still ticking or game hasn't started, do nothing,
    else
        If it's wrong on the order, Toast the user and reset.
        IF it is right but more buttons need to be pressed, increment stuff and do nothing
        if it's right and the last button, do the showing sequence with 1 more button or if it's the last in the array, player wins.

     How to show the player the highlighted buttons in order:
     Set up a timer, with number of seconds left ranging from 1000 to 7000 ms
     On each tick (probably 500 - 1000 ms), the next button will highlight, until it finishes
        for every highlighting I need to turn the button off, so the postDelayed stuff needs to be less than the tick.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_says);

        Button testButton = findViewById(R.id.buttonTest);

        final Button greenButton = findViewById(R.id.buttonGreen);
        final Button redButton = findViewById(R.id.buttonRed);

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                greenButton.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                unClick(greenButton,R.color.darkGreen);

                redButton.setBackgroundColor(getResources().getColor(R.color.lightRed));
                unClick(redButton,R.color.darkRed);


            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greenButton.performClick();
            }
        });

    }

    void unClick(final Button button, final int color) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                button.setBackgroundColor(getResources().getColor(color));

            }
        }, 2000);
    }


}