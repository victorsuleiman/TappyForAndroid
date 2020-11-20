package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
A file with data about all songs is read, this data will be parsed and put inside objects
These objects are stored inside a List<Song>
When the activity start, a random Song is selected
A timer starts running the moment the user hear the song (a boolean keeps track of whether the user have heard the song or not)
The user enter their guess into the editText. This will then be compared with the song name stored inside the object
User guesses right => Do a little animation, get the time they needed to guess, reset timer, get a new song
User guesses wrong => Display hints (this can happen a few times). Hints includes album art, artist name and trivia

The game can be repeated, the timer and song generator will work properly
Resources are accessed with dynamic names, data are read from a CSV file and put into objects
Play/Pause button works as intended, the song always resume from where paused, unless a new song is gotten
Uses Relative layout and ImageView to set a background that's tinted and centered
A method is used to make sure all resources are bound and working correctly
Uses quite a few of animations
 */
public class NameThatSong extends AppCompatActivity {

    ImageView bgImg;
    ImageView decImgLeft;
    ImageView decImgRight;
    Button submitBtn;
    EditText answerText;
    MediaPlayer aPlayer = null;
    ImageButton playPauseBtn;
    TextView ntsTitle;

    List<Song> songList = new ArrayList<>(); //holds all song;
    //TODO:switch to DB implementation, uses songParser class (currently using a CSV file to hold all song info)
    long startTime; //used to calculate time taken for guess
    long endTime;
    long timeTaken;

    boolean songPlayed = false; //check if song has been played or not
    int tries = 0; // times guessed, used to display hints too
    int currentSong; //holds the song currently being played
    int currentPos; //used for Play/Pause function
    Random rand = new Random(); //used to generate random number for a random song

    RotateAnimation animL = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); //used to rotate images when correct guess
    RotateAnimation animR = new RotateAnimation(360f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); //used to rotate images when correct guess
    Animation shakeAnim; //for submit button
    Animation rotateAnim; //for play/pause button
    Animation shrinkRotateInAnim; //for play/pause button when winning first try
    Animation fadeInAnim; //fade title into view
    Animation pushDownInAnim; //animate cover art into view
    Animation bounceAnim;
    Animation pushUpOut;
    Animation pushUpIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_that_song);

        submitBtn = findViewById(R.id.ntsSubmitBtn);
        answerText = findViewById(R.id.ntsAnswerField);
        playPauseBtn = findViewById(R.id.playPauseBtn);
        ntsTitle = findViewById(R.id.ntsTitleTxt);

        bgImg = findViewById(R.id.imageSwitcherNTS);
        decImgLeft = findViewById(R.id.decImg2);
        decImgRight = findViewById(R.id.decImg3);

        animL.setInterpolator(new LinearInterpolator());
        animL.setRepeatCount(1);
        animL.setDuration(500);

        animR.setInterpolator(new LinearInterpolator());
        animR.setRepeatCount(1);
        animR.setDuration(500);

        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        shrinkRotateInAnim = AnimationUtils.loadAnimation(this, R.anim.shrinkrotatein);
        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        pushDownInAnim = AnimationUtils.loadAnimation(this, R.anim.pushdownin);
        bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        pushUpOut = AnimationUtils.loadAnimation(this, R.anim.pushupout);
        pushUpIn = AnimationUtils.loadAnimation(this, R.anim.pushupin);
        songList = SongParser(); //getting song list
        testResources(songList); //check if any resources is missing
        currentSong = rand.nextInt(songList.size()); //get random current song

        ntsTitle.setAnimation(bounceAnim);
        decImgRight.setAnimation(fadeInAnim);
        decImgLeft.setAnimation(fadeInAnim);
//        Toast.makeText(this, songList.get(currentSong).toString(), Toast.LENGTH_SHORT).show(); //get info about currently selected song



        /*Clicking Play/pause button*/
        playPauseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                playPauseBtn.startAnimation(rotateAnim);
                //stop player if already playing
                if (aPlayer != null && aPlayer.isPlaying())
                {
                    currentPos = aPlayer.getCurrentPosition();
                    aPlayer.pause();
                    playPauseBtn.setImageResource(R.drawable.play_button);
                }
                else
                {
                    if (songPlayed == false) //song never played => starts timer and marks that the user has listened, play songs from the beginning, stop animation if exist
                    {
                        startTime = System.currentTimeMillis(); //starts timer once the user have heard the song

                        submitBtn.setText("Try to guess the song and click here");
                        songPlayed = true;
                        try
                        {
                            aPlayer = MediaPlayer.create(NameThatSong.this, songList.get(currentSong).getTune());
                        }
                        catch (Exception ex)
                        {
                            Log.e("Media Player", "problem playing song " +currentSong + "  " + ex.getMessage());
                        }

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
                String userAnswer = answerText.getText().toString().trim(); //answer trimmed
                if (songPlayed == false)
                {
                    Toast.makeText(NameThatSong.this, "Listen to the song first :)", Toast.LENGTH_SHORT).show();
                }
                else if (userAnswer.equals("")) //no answer given
                {
                    Toast.makeText(NameThatSong.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }
                else //Answer was given, song was played
                {
                    tries ++;
                    String actualAnswer = songList.get(currentSong).getSongName();
                    if (userAnswer.equalsIgnoreCase(actualAnswer)) //user guesses right
                    {
                        endTime = System.currentTimeMillis();
                        timeTaken = endTime - startTime;

                        if (tries == 1) {
                            submitBtn.setText("Correct! You answered in " + formatMilliseconds(timeTaken) + "\nYou got it first try!"
                                    + "\nTry again? A new song is waiting!");
                            submitBtn.startAnimation(pushUpIn);
                        }
                        else
                        {
                            submitBtn.setText("Correct! You answered in " + formatMilliseconds(timeTaken) + "\nIt took you " + tries + " guesses"
                                    + "\nTry again? A new song is waiting!");
                        }

                        decImgLeft.startAnimation(animL); //spin left image
                        decImgRight.startAnimation(animR); //spin right image

                        aPlayer.stop(); //stop current song
                        aPlayer.reset(); //prevent memory leaks
                        playPauseBtn.setImageResource(R.drawable.play_button); //reset play button
                        currentSong = rand.nextInt(songList.size()); //get random current song
                        songPlayed = false; //reset songPlayed boolean
                        answerText.setText(""); //clear user answer field
                        tries = 0; //reset tries count
//                        bgImg.setAnimation(pushUpOut);
                        bgImg.setBackgroundResource(R.drawable.transparentbg); //reset background image
                    }
                    else //wrong guess
                    {
//                        Toast.makeText(NameThatSong.this, "BOOOOOOO", Toast.LENGTH_SHORT).show();

                        playPauseBtn.startAnimation(shakeAnim); //start animation if it's a correct guess
                        /*give Hints*/
                        if (tries == 1) {
                            submitBtn.setText("That's not right.\n It's a song by " + songList.get(currentSong).getArtist());
                        }
                        else if (tries ==2)
                        {
                            submitBtn.setText("That's not the song.\n"
                            + songList.get(currentSong).getHint());
                        }
                        else
                        {
                            int hintImg = songList.get(currentSong).getCoverArt();
                            bgImg.setBackgroundResource(hintImg);
                            bgImg.setAnimation(pushDownInAnim);
                            submitBtn.setText("Still not right yet.\nHere's the cover art");
                        }
                    } //ends wrong guesses
                } //ends answer given
            }
        }); //ends clicking submit Button
    } //ends onCreate

    /*used to parse song from DB*/
    public List<Song> SongParser()
    {
        List<String[]> dataFromFile = ReadCSV(); //get data from CSV file

        List<Song> songList = new ArrayList<>(); //holds song objects

        /*loop through data and creates objects*/
        for (int i = 0; i < dataFromFile.size(); i++)
        {
//            Log.e("Something", Arrays.toString(dataFromFile.get(i)));

            int tuneID = getResources().getIdentifier(dataFromFile.get(i)[2], "raw", getPackageName()); //get tune ID
            int artID = getResources().getIdentifier(dataFromFile.get(i)[3], "drawable", getPackageName()); //get art ID

            /*creates song object*/
            Song aSong = new Song
                (
                    dataFromFile.get(i)[0], //song name
                    dataFromFile.get(i)[1], //artist name
                    tuneID, //tune
                    artID, //cover art
                    dataFromFile.get(i)[4] //hint
                );
            songList.add(aSong); //add object to list
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
            throw new RuntimeException("Error reading CSV File " + ex.getMessage());
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Error closing CSV File " + ex.getMessage());
            }
        }
        return dataList;
    }

    /*test song resources*/
    public void testResources(List<Song> songList)
    {
        boolean error = false;
        for (int i = 0; i < songList.size(); i++)
        {
            if (songList.get(i).getCoverArt() == 0 || songList.get(i).getTune() == 0)
            {
                Log.d("Song List", "Resources missing on song " + i);
                error = true;
            }
        }
        if (error)
        {
            Toast.makeText(this, "Resources missing. Check logcat for list", Toast.LENGTH_SHORT).show();
        }
    }


    /*method to convert milliseconds into different time format*/
//    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatMilliseconds(long milliseconds)
    {
        final long MILLIS_IN_SECOND = 1000;

        //if less than one minute => return simple format
        if (milliseconds < MILLIS_IN_SECOND * 60)
        {
            double msDouble = milliseconds;
            double finalSeconds = msDouble/1000;
            String result = finalSeconds + " second";
            //if more than 2 seconds => add ending "s"
            if (finalSeconds >= 2)
            {
                result += "s";
            }
            return result;
        }

        //return full format for bigger numbers
        else
        {
            final long SECONDS_IN_MINUTE = 60;
            final long SECONDS_IN_HOUR = 3600;
            final long SECONDS_IN_DAY = SECONDS_IN_HOUR * 24;
            final long SECONDS_IN_YEAR = SECONDS_IN_DAY * 365; //assume a year has 365 days
            long years, days, hours, minutes, seconds = 0;
            String toAdd = "";
            ArrayList<String> resultString = new ArrayList<String>();
            //String[] units = {"year", "day", "hour", "minute"}; //can be utilized to change languages

            seconds = milliseconds / 1000;
            milliseconds = milliseconds % MILLIS_IN_SECOND;

            years = seconds / SECONDS_IN_YEAR;
            seconds = seconds % SECONDS_IN_YEAR;
            if (years != 0)
            {
                toAdd = years + " year";
                if (years > 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            days = seconds / SECONDS_IN_DAY;
            seconds = seconds % SECONDS_IN_DAY;
            if (days != 0)
            {
                if (years != 0)
                {
                    resultString.add(", ");
                }
                toAdd = days + " day";
                if (days> 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            hours = seconds / SECONDS_IN_HOUR;
            seconds = seconds % SECONDS_IN_HOUR;
            if (hours != 0)
            {
                if (years != 0 || days != 0)
                {
                    resultString.add(", ");
                }
                toAdd = hours + " hour";
                if (hours> 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            minutes = seconds / SECONDS_IN_MINUTE;
            seconds = seconds % SECONDS_IN_MINUTE;
            if (minutes != 0)
            {
                if (years != 0 || days != 0 || hours != 0)
                {
                    resultString.add(", ");
                }
                toAdd = minutes + " minute";
                if (minutes> 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            if (seconds != 0)
            {
                if (years != 0 || days != 0 || hours != 0 || minutes != 0)
                {
                    resultString.add(", ");
                }
                toAdd = seconds + " second";
                if (seconds > 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            if (milliseconds != 0)
            {
                if (years != 0 || days != 0 || hours != 0 || minutes != 0 || seconds != 0)
                {
                    resultString.add(", ");
                }
                toAdd = milliseconds + " millisecond";
                if (milliseconds > 1)
                {
                    toAdd += "s";
                }
                resultString.add(toAdd);
            }

            //nothing printed => just say "no time"
            if (resultString.size() == 0)
            {
                resultString.add("no time");
            }
            //replace last comma with " and "
            else if (resultString.size() > 1 && resultString.get(resultString.size() - 2).equals(", "))
            {
                resultString.set(resultString.size() - 2, " and ");
            }

            //converts arrayList to normal string without using String.join
            String fullResult = "";
            for (String s : resultString)
            {
                fullResult += s;
            }
            return fullResult;
        }
    } //ends formatMilliseconds

    //returns to Level List when back button is pressed
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,LevelList.class));
        aPlayer.stop(); //stop song
        aPlayer.reset(); //prevent memory leaks
    }

}

//Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>