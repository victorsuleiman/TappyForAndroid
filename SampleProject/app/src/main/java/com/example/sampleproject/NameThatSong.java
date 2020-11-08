package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NameThatSong extends AppCompatActivity {

    Button submitBtn;
    EditText answerText;
    MediaPlayer aPlayer = null;
    Song song1 = new Song();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_that_song);

        submitBtn = findViewById(R.id.ntsSubmitBtn);
        answerText = findViewById(R.id.ntsAnswerField);

        //TODO: move to Play/Pause button
        /*play song*/
        aPlayer = MediaPlayer.create(NameThatSong.this, song1.tune);
        aPlayer.start();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //compare input vs song name
                String userAnswer = answerText.getText().toString();

                if (userAnswer.equalsIgnoreCase(song1.getSongName()))
                {
                    Toast.makeText(NameThatSong.this, "Perfect!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(NameThatSong.this, "BOOOOOOO", Toast.LENGTH_SHORT).show();
                }

                //TODO:change textview based on correct or not
            }
        });

    }



    public static class Song
    {
        private String songName;
        private String artist;
        private String hintNo1;
        private String hintNo2;
        private int tune;

        public Song() {
            songName = "In The End";
            artist = "Linkin Park";
            tune = R.raw.in_the_end_lp;
        }
        public Song(String songName, String artist, String hintNo1, String hintNo2, int tune)
        {
            this.songName = songName;
            this.artist = artist;
            this.hintNo1 = hintNo1;
            this.hintNo2 = hintNo2;
            this.tune = tune;
        }

        public String getSongName() {
            return songName;
        }

        public int getTune() {
            return tune;
        }
    }
}