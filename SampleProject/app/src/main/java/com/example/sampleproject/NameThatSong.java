package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NameThatSong extends AppCompatActivity {

    Button submitBtn;
    EditText answerText;
    MediaPlayer aPlayer = null;
    Song song1 = new Song(); //TODO: change to song array based on DB, uses songParser class
    List<Song> songList = new ArrayList<>(); //holds all song;
    ImageButton playPauseBtn;
    long startTime; //used to calculate time taken for guess
    long endTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_that_song);

        submitBtn = findViewById(R.id.ntsSubmitBtn);
        answerText = findViewById(R.id.ntsAnswerField);
        playPauseBtn = findViewById(R.id.playPauseBtn);

        songList = SongParser(); //getting song list

        /*Clicking Play/pause button*/
        playPauseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                //stop player if already playing
                if (aPlayer != null && aPlayer.isPlaying())
                {
                    aPlayer.stop();
                    playPauseBtn.setImageResource(R.drawable.play_button); //TODO: change to Play img
                }
                else
                {
                    /*play song*/
                    aPlayer = MediaPlayer.create(NameThatSong.this, songList.get(0).getTune());
                    aPlayer.start();
                    playPauseBtn.setImageResource(R.drawable.pause_button); //TODO: change to Pause img
                }
            }
        });


        /*Clicking submit button*/
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //compare input vs song name
                String userAnswer = answerText.getText().toString();
                if (userAnswer.equals(null))
                {
                    Toast.makeText(NameThatSong.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (userAnswer.equalsIgnoreCase(song1.getSongName())) {
                        Toast.makeText(NameThatSong.this, "Perfect!", Toast.LENGTH_SHORT).show();
                        aPlayer.stop();
                    } else {
                        Toast.makeText(NameThatSong.this, "BOOOOOOO", Toast.LENGTH_SHORT).show();
                    }
                }

                //TODO:change textview based on correct or not
            }
        });

    }

    /*used to parse song from DB*/
    public static List<Song> SongParser()
    {
        Song song1 = new Song();
        List<Song> songList = new ArrayList<>();
        songList.add(song1);
        return songList;
    }

}

//Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>