package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sampleproject.SupportClasses.ButtonAttribute;
import com.example.sampleproject.SupportClasses.TimeRecorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class SimonSays extends AppCompatActivity {

    //Pseudo-code for what I did:
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
        for every highlighting I need to turn the button off before the other gets lit (bro),
        so the postDelayed stuff needs to be less than the tick.
     */

    private final int NUMBER_OF_ROUNDS = 7;
    private CountDownTimer timer;
    private long timeLeft;
    private final int tickTime = 1000;
    private final List <ButtonAttribute> buttonAttributes = new ArrayList<>();
    private int roundNumber = 1;
    private int playerPresses = 0;
    private boolean rightOrder = true;
    private boolean gameStarted = false;
    private boolean patternShowing = false;
    private MediaPlayer mediaPlayer;
    private int numberOfTries = 0;

    private TimeRecorder timeRecorder;

    SQLiteDatabase tappyDB;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_says);

        openDB(); //open our DB

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = sharedPref.getString(Constants.USERNAME_CURRENT, "Anonymous");

        timeRecorder = new TimeRecorder(this);

        //Adding the buttons and their respective colors so I can freely use it
        buttonAttributes.add(new ButtonAttribute((Button)findViewById(R.id.buttonGreen),R.color.lightGreen,R.color.darkGreen,"green",
                R.raw.simon_sound_green));
        buttonAttributes.add(new ButtonAttribute((Button)findViewById(R.id.buttonRed),R.color.lightRed,R.color.darkRed,"red",
                R.raw.simon_sound_red));
        buttonAttributes.add(new ButtonAttribute((Button)findViewById(R.id.buttonYellow),R.color.lightYellow,R.color.darkOrange,"yellow",
                R.raw.simon_sound_yellow));
        buttonAttributes.add(new ButtonAttribute((Button)findViewById(R.id.buttonBlue),R.color.lightBlue,R.color.darkBlue,"blue",
                R.raw.simon_sound_blue));

        Button testButton = findViewById(R.id.buttonSimonStart);

        setOnTouchListeners();

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameStarted) {
                    Toast.makeText(SimonSays.this, "Game is still running!", Toast.LENGTH_SHORT).show();
                } else {
                    numberOfTries++;
                    roundNumber = 1;
                    playerPresses = 0;
                    //instantiating a ButtonColor list for the random ButtonColors
                    List<ButtonAttribute> colorOrder = assignOrder();
                    gameStarted = true;
                    //setOnClickListeners for all colored buttons
                    setButtonListeners(colorOrder);
                    timeRecorder.startRecording();
                    showPattern(colorOrder);
                }

            }
        });

    }

    private void showPattern(final List<ButtonAttribute> buttons) {

        //timeLeft will be array length times tick -> REFACTOR! NEEDS TO BE CURRENT ROUND NUMBER!
        timeLeft = tickTime*roundNumber;
        patternShowing = true;

        timer = new CountDownTimer(timeLeft,tickTime) {
            int i = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    highlightButton(buttons.get(i).getButton(),buttons.get(i).getHighlightColor());
                    playSound(buttons.get(i));
                    unHighlightButton(buttons.get(i).getButton(),buttons.get(i).getDarkColor());
                    i++;
                } catch (Exception ex) {
                    Toast.makeText(SimonSays.this,ex.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFinish() {
                patternShowing = false;
            }
        }.start();
    }

    private List<ButtonAttribute> assignOrder() {
        Random rand = new Random();
        List<ButtonAttribute> order = new ArrayList<>();
        //grab a random ButtonColor from the buttons
        for (int i = 0; i < NUMBER_OF_ROUNDS; i++) {
            int randomIndex = rand.nextInt(buttonAttributes.size());
            order.add(buttonAttributes.get(randomIndex));
        }
        return order;
    }

    private void highlightButton (Button button, int color) {
        button.setBackgroundColor(getResources().getColor(color));
    }

    void unHighlightButton(final Button button, final int color) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                button.setBackgroundColor(getResources().getColor(color));

            }
        }, 500);
    }

    void setButtonListeners(final List<ButtonAttribute> colorOrder) {

        //see the roundNumber, see the buttonPresses,
        //compare the button that has been pressed with the button in the order array
        //if it's the same button, either keep going or if it's the last button, user won.
        for (int i = 0; i < buttonAttributes.size(); i++)
        {
            final ButtonAttribute currentButton = buttonAttributes.get(i);
            buttonAttributes.get(i).getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //don't do anything if game has not started or pattern is still being shown
                    if (!gameStarted || patternShowing) return;
                    else  {
                        //right button

                        if (currentButton.getButtonName().equals(colorOrder.get(playerPresses).getButtonName())) {
                            playerPresses++;
                            if (playerPresses == roundNumber) {
                                roundNumber ++;
                                if (roundNumber > NUMBER_OF_ROUNDS) {
                                    //game is won!
                                    gameStarted = false;

                                    DBHelper.addUserScore(username,"Simon Tap",(long) timeRecorder.getTime(),
                                            formatAdditionalInfo());
                                    timeRecorder.stopAndResetTimer(true);
                                } else {
                                    //round won, need to show
                                    Toast.makeText(SimonSays.this, "Round Won! Next Round...", Toast.LENGTH_SHORT).show();
                                    playerPresses = 0;

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run()
                                        {
                                            showPattern(colorOrder);
                                        }
                                    }, 3000);

                                }
                            }
                        }
                        //wrong button
                        else {
                            roundNumber = 1;
                            playerPresses = 0;
                            Toast.makeText(SimonSays.this, "Wrong Button! Start again!", Toast.LENGTH_SHORT).show();
                            gameStarted = false;
                            timeRecorder.stopAndResetTimer(false);
                        }
                    }
                }
            });
        }
    }

    void setOnTouchListeners() {
        for (int i = 0; i < buttonAttributes.size(); i++) {
            final ButtonAttribute currentButtonAttribute = buttonAttributes.get(i);
            final Button currentButton = currentButtonAttribute.getButton();
            currentButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        currentButton.setBackgroundColor(getResources().getColor(currentButtonAttribute.getDarkColor()));
                        playSound(currentButtonAttribute);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        currentButton.setBackgroundColor(getResources().getColor(currentButtonAttribute.getHighlightColor()));
                    }
                    return false;
                }
            });
        }

    }

    void playSound(ButtonAttribute button) {
        if (mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        else
        {
            /*play song*/
            mediaPlayer = MediaPlayer.create(SimonSays.this, button.getSound());
            mediaPlayer.start();

            /*stop mediaPlayer once done*/
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer player)
                {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    // play next audio file


                }
            });
        }
    }

    private void openDB()
    {
        try
        {
            tappyDB = openOrCreateDatabase("tappy.db", MODE_PRIVATE, null);
        }
        catch (Exception e)
        {
            Log.d("Tappy DB", "Database opening error" + e.getMessage());
        }
    }

    private String formatAdditionalInfo() {
        String formattedInfo = "";

        if (numberOfTries <= 1) formattedInfo = "First try";
        else formattedInfo = String.valueOf(numberOfTries) + " tries";

        return formattedInfo;
    }

    //returns to Level List when back button is pressed
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,LevelGrid.class));

    }


}