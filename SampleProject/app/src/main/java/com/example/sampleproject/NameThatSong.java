package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.Random;

/*
A file with data about all songs is read, this data will be parsed and put inside objects
These objects are stored inside a List<Song>
When the activity start, a random Song is selected
A timer starts running the moment the user hear the song (a boolean keeps track of whether the user have heard the song or not)
The user enter their guess into the editText. This will then be compared with the song name stored inside the object
User guesses right => Get the time they needed to guess, reset timer, get a new song
User guesses wrong => Display hints (this can happen a few times). Hints includes album art, artist name and trivia

The game can be repeated, the timer and song generator will work properly
Resources are accessed dynamically
Play/Pause button works as intended, the song always resume from where paused, unless a new song is gotten
 */
public class NameThatSong extends AppCompatActivity {

    Button submitBtn;
    EditText answerText;
    MediaPlayer aPlayer = null;

    List<Song> songList = new ArrayList<>(); //holds all song;
    //TODO:switch to DB implementation, uses songParser class
    ImageButton playPauseBtn;
    long startTime; //used to calculate time taken for guess
    long endTime;
    long timeTaken;

    boolean songPlayed = false; //check if song has been played or not
    int currentSong; //holds the song currently being played
    int currentPos; //used for Play/Pause function
    Random rand = new Random(); //used to generate random number for a random song


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_that_song);

        submitBtn = findViewById(R.id.ntsSubmitBtn);
        answerText = findViewById(R.id.ntsAnswerField);
        playPauseBtn = findViewById(R.id.playPauseBtn);

        songList = SongParser(); //getting song list

        currentSong = rand.nextInt(songList.size()); //get random current song

        Toast.makeText(this, songList.get(currentSong).toString(), Toast.LENGTH_SHORT).show();

        /*Clicking Play/pause button*/
        playPauseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                //stop player if already playing
                if (aPlayer != null && aPlayer.isPlaying())
                {
                    currentPos = aPlayer.getCurrentPosition();
                    aPlayer.pause();
                    playPauseBtn.setImageResource(R.drawable.play_button);
                }
                else
                {
                    if (songPlayed == false) //song never played => starts timer and marks that the user has listened, play songs from the beginning
                    {
                        startTime = System.currentTimeMillis(); //starts timer once the user have heard the song
                        songPlayed = true;
                        aPlayer = MediaPlayer.create(NameThatSong.this, songList.get(currentSong).getTune());
                    }
                    else /*resume song*/
                    {
                        aPlayer.seekTo(currentPos);
                    }
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
                if (userAnswer.equals("")) //no answer given
                {
                    Toast.makeText(NameThatSong.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }
                else //Answer was given
                {
                    String actualAnswer = songList.get(currentSong).getSongName();
                    if (userAnswer.equalsIgnoreCase(actualAnswer)) //user guesses right
                    {
                        submitBtn.setText("You Got It!!! \n\nTry again? A new song is waiting!");
                        aPlayer.stop();

                        endTime = System.currentTimeMillis();
                        timeTaken = endTime - startTime;
                        Toast.makeText(NameThatSong.this, "Perfect! It took you " + timeTaken, Toast.LENGTH_SHORT).show();
                        //TODO: convert to readable format. Check previous code for ready-made converter

                        currentSong = rand.nextInt(songList.size()); //get random current song
                        songPlayed = false; //reset song played
                        answerText.setText(""); //clear user answer field
                    }
                    else //wrong guess
                    {
                        Toast.makeText(NameThatSong.this, "BOOOOOOO", Toast.LENGTH_SHORT).show();

                        /*give Hints*/
                        submitBtn.setText("That's not right. It's a song by " + songList.get(currentSong).getArtist());
                    }
                }

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

        List<Song> songList = new ArrayList<>(); //holds song objects
        /*loop through data and creates objects*/
        for (int i = 0; i < dataFromFile.size(); i++)
        {
            Log.e("Something", Arrays.toString(dataFromFile.get(i)));

            int tuneID = getResources().getIdentifier(dataFromFile.get(i)[2], "raw", getPackageName()); //get tune ID
            int artID = getResources().getIdentifier(dataFromFile.get(i)[3], "drawable", getPackageName()); //get art ID
            Song aSong = new Song
                (
                    dataFromFile.get(i)[0], //song name
                    dataFromFile.get(i)[1], //artist name
                    tuneID, //tune
                    artID, //cover art
                    dataFromFile.get(i)[4] //hint
                );
            songList.add(aSong);
        }

        return songList;
    }

    /*get Data from CSV file*/
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

    //returns to Level List when back button is pressed
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,LevelList.class));

    }
}

//Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>