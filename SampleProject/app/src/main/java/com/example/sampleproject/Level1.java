package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Level1 extends AppCompatActivity {

   // String colorID = "";
    int i = 0;
    Button tapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        tapBtn = findViewById(R.id.tappyTappy);
        int resColor = R.color.Press10;

        tapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                String tappy = (10 - i) + " more!";
                tapBtn.setText(tappy);

                // colorID = "Press" + (10 - i);
                // int resColor= getResources().getIdentifier(R.color.Press(10-i);
                //   int resID = getResources().getIdentifier("com.example.sampleproject:color/"+colorID, null, null);
                //    tapBtn.setBackgroundColor(resID);

                if (i == 1) {
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

                if (i >= 9) {
                    startActivity(new Intent(Level1.this, TicTacToe.class));
                }
            }
        });
    }

    //returns to Level List when back button is pressed
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,LevelList.class));

    }
}