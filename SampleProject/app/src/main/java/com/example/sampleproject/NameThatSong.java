package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameThatSong extends AppCompatActivity {

    Button submitBtn;
    EditText answerText;
    MediaPlayer aPlayer = null;

    List<Song> songList = new ArrayList<>(); //holds all song;
    //TODO:switch to DB implementation, uses songParser class
    ImageButton playPauseBtn;
    long startTime; //used to calculate time taken for guess
    long endTime;

    //TODO: get random current song
    int currentSong = 0; //current song that's being played
    boolean songPlayed = false; //check if song has been played or not



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
                    playPauseBtn.setImageResource(R.drawable.play_button);
                }
                else
                {
                    if (songPlayed == false) //song never played => starts timer and marks that the user has listened
                    {
                        startTime=System.currentTimeMillis(); //starts timer
                        songPlayed = true;
                    }
                    /*play song*/
                    aPlayer = MediaPlayer.create(NameThatSong.this, songList.get(currentSong).getTune());
                    aPlayer.start();
                    playPauseBtn.setImageResource(R.drawable.pause_button);
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
                    String actualAnswer = songList.get(currentSong).getSongName();
                    if (userAnswer.equalsIgnoreCase(actualAnswer)) {
                        Toast.makeText(NameThatSong.this, "Perfect!", Toast.LENGTH_SHORT).show();
                        submitBtn.setText("You Got It!!! \n\nTry again? A new song is waiting!");
                        aPlayer.stop();

                        //TODO:get new song (update currentSong)
                        currentSong = 0;
                    } else {
                        Toast.makeText(NameThatSong.this, "BOOOOOOO", Toast.LENGTH_SHORT).show();
                        submitBtn.setText("That's not right. It's a song by ....");
                    }
                }

                //TODO:change textview/button based on correct or not
            }
        });

    }

    /*used to parse song from DB*/
    public List<Song> SongParser()
    {
        Song song1 = new Song();
        int id = getResources().getIdentifier("in_the_end_lp", "raw", getPackageName());
        song1.setTune(id); //set tune for song
//        song1.setTune(R.raw.in_the_end_lp);
//        Song song1 = new Song("In The End, Linkin Park,");
        List<String[]> dataFromFile = ReadCSV();

        for (int i = 0; i < dataFromFile.size(); i++)
        {
            Log.e("Something", Arrays.toString(dataFromFile.get(i)));
            Song aSong = new Song();
        }

        List<Song> songList = new ArrayList<>();
        songList.add(song1);
        return songList;
    }
    private List<String[]> ReadCSV()
    {
        List<String[]> dataList = new ArrayList<>();

        //populate the list
        InputStream inputStream = getResources().openRawResource(R.raw.songs);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try
        {
            String csvLine;
            while ((csvLine = reader.readLine()) != null)
            {
                String[] dataInfo = csvLine.split(","); //breakdown line into array
                dataList.add(dataInfo); //add data to list
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Error reading CSV File " + ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Error closing CSV File");
            }
        }
        return dataList;
    }
}

//Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>