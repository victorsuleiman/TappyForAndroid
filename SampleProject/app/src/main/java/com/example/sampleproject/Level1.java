package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Level1 extends AppCompatActivity {

    int i = 0;
    Button tapBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        tapBtn = findViewById(R.id.tappyTappy);

        tapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                String tappy = (10 - i) + " more!";
                tapBtn.setText(tappy);
                if (i>= 9)
                {
                    startActivity(new Intent(Level1.this, TicTacToe.class));
                }
            }
        });
    }
}