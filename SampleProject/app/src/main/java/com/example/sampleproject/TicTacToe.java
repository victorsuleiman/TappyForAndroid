package com.example.sampleproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleproject.SupportClasses.TimeRecorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.sampleproject.MainActivity.USERNAME_CURRENT;

public class TicTacToe extends AppCompatActivity {

    private TimeRecorder timeRecorder;

    private Button[][] buttons = new Button[3][3];

    private boolean gameStarted = false;
    private boolean playerTurn = true;

    private int roundCount;

    private int playerPoints;
    private int cpuPoints;

    private TextView textViewPlayer;
    private TextView textViewCpu;

    SQLiteDatabase tappyDB;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        openDB(); //open our DB

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = sharedPref.getString(USERNAME_CURRENT, "Anonymous");

        timeRecorder = new TimeRecorder(this);

        textViewPlayer = findViewById(R.id.textViewPlayerScore);
        textViewCpu = findViewById(R.id.textViewCpuScore);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //grabbing the ids dynamically
                String buttonID = "button_" + i + j;

                //resource ID
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                Button button = buttons[i][j];
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!gameStarted){
                            gameStarted = true;
                            timeRecorder.startRecording();
                        }

                        //checks if this button on the view that was clicked already
                        if (!((Button) v).getText().toString().equals("")) {
                            return;
                        }

                        if (playerTurn) {
                            ((Button) v).setText("X");
                        } else {
                            ((Button) v).setText("O");
                        }

                        //round over
                        roundCount++;
                        if (checkForWin()) {
                            if (playerTurn) player1Wins();
                            else cpuWins();
                        } else if (roundCount == 9) {
                            draw();
                        } else {
                            //now cpu plays.
                            playerTurn = !playerTurn;
                            cpuPlays();
                        }
                    }
                });
            }
        }

    }



    private void player1Wins() {
        playerPoints++;
        if (playerPoints == 3) {
            gameStarted = false;
            addUserScore(username,"Tic Tap Toe",(long)timeRecorder.getTime());
            timeRecorder.stopAndResetTimer(true);
            resetPoints();
            updatePointsText();
        } else {
            Toast.makeText(this,"You won the Round!", Toast.LENGTH_SHORT).show();
        }

        updatePointsText();
        resetBoard();
    }

    private void resetPoints() {
        playerPoints = 0;
        cpuPoints = 0;
    }

    private void cpuWins() {
        cpuPoints++;
        if (cpuPoints == 3) {
            gameStarted = false;
            timeRecorder.stopAndResetTimer(false);
            Toast.makeText(this,"CPU Wins the Game! Try again.", Toast.LENGTH_SHORT).show();
            resetPoints();
            updatePointsText();
        } else {
            Toast.makeText(this,"CPU Wins the Round!", Toast.LENGTH_SHORT).show();
        }
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this,"Draw!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer.setText("You (X): " + playerPoints);
        textViewCpu.setText("CPU (O): " + cpuPoints);
    }

    private void resetBoard() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setText("");
                    }
                }

                roundCount = 0;
                playerTurn = true;
            }
        }, 1500); // 1000 mili seconds is 1 second

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //check row win
        for (int i = 0; i < 3; i++) {
            //checks if the next to each other are not empty and have the same letters
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
                    &&!field[i][0].equals("")) {
                return true;
            }
        }

        //check column win
        for (int i = 0; i < 3; i++) {
            //checks if the next to each other are not empty and have the same letters
            if (field[0][i].equals(field[1][i]) && field[2][i].equals(field[0][i])
                    &&!field[0][i].equals("")) {
                return true;
            }
        }

        //check diagonal win
        String center = field[1][1];
        if (!center.equals("")) {
            boolean cond1 = field[0][0].equals(center) && field[2][2].equals(center);
            boolean cond2 = field[0][2].equals(center) && field[2][0].equals(center);

            return cond1 || cond2;
        }

        return false;
    }


    private void cpuPlays() {

        Button playPos = cpuAI();

        playPos.setText("O");
        if (checkForWin()) cpuWins();
        roundCount++;
        playerTurn = true;
    }

    private Button cpuAI() {

        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //checking win by row
        for (int i = 0; i < 3; i++) {
            //if there is two X's in the row AND an empty position put it on the empty position
            int emptyColumns = 0;
            int emptyColPosition = 0;
            int numberOfXs = 0;
            for (int j = 0; j < 3; j++) {
                if (field[i][j].equals("")) {
                    emptyColumns++;
                    emptyColPosition = j;
                } else if (field[i][j].equals("X")) {
                    numberOfXs++;
                }
                if (numberOfXs == 2 && emptyColumns == 1) {
                    Log.d("AI","Blocked Row");
                    return buttons[i][emptyColPosition];
                }
            }

        }

        //checking win by col
        for (int i = 0; i < 3; i++) {
            //check if there is only one empty column in the line and grab its position

            int emptyRows = 0;
            int emptyRowPosition = 0;
            int numberofXs = 0;
            for (int j = 0; j < 3; j++) {
                if (field[j][i].equals("")) {
                    emptyRows++;
                    emptyRowPosition = j;
                } else if (field[j][i].equals("X")) {
                    numberofXs++;
                }
            }

            //if there's only one empty position in the row
            if (emptyRows == 1 && numberofXs == 2) {
                Log.d("AI","Blocked col");
                return buttons[emptyRowPosition][i];
            }
        }

        //checking win by diagonal
        if (field[0][0].equals("X") && field[1][1].equals("X") && field[2][2].equals("")) return buttons[2][2];
        if (field[0][0].equals("X") && field[2][2].equals("X") && field[1][1].equals("")) return buttons[1][1];
        if (field[1][1].equals("X") && field[2][2].equals("X") && field[0][0].equals("")) return buttons[0][0];
        if (field[0][2].equals("X") && field[1][1].equals("X") && field[2][0].equals("")) return buttons[2][0];
        if (field[0][2].equals("X") && field[2][0].equals("X") && field[1][1].equals("")) return buttons[1][1];
        if (field[1][1].equals("X") && field[2][0].equals("X") && field[0][2].equals("")) return buttons[0][2];

        //if the function still didn't return anything, play it random. We want the CPU do get in the way of the player, not to win hehe

        //prioritize center
        if (field[1][1].equals("")) {
            Log.d("AI","Played center");
            return buttons[1][1];

        }

        //see which positions on the board cpu can play
        List<Button> availablePositions = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String position = buttons[i][j].getText().toString();
                if (position.equals("")) availablePositions.add(buttons[i][j]);
            }
        }



        //choose a random available position and play circle
        Random rand = new Random();
        int min = 0;
        int max = availablePositions.size() - 1;

        int randomPos = rand.nextInt((max - min) + 1) + min;

        Log.d("AI","Picking Random position");

        return availablePositions.get(randomPos);
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

    public void addUserScore (String username, String game, long score)
    {
        long result = 0;
        ContentValues val = new ContentValues();
        val.put("username", username);
        val.put("game", game);
        val.put("score", score);

        result = tappyDB.insert("scores", null, val);

        if (result != -1)
        {
            Log.d("DB Tappy", "Added score for user " + username );
        }
        else
        {
            Log.d("DB Tappy", "Error adding score for user " + username );
        }
    }

    //this method makes sure we doesn't lose the state of our variables if the device rotates
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points",playerPoints);
        outState.putInt("cpuPoints",cpuPoints);
        outState.putBoolean("player1Turn",playerTurn);
        outState.putDouble("currentTime",timeRecorder.getTime());
        outState.putBoolean("gameStarted",gameStarted);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerPoints = savedInstanceState.getInt("player1Points");
        cpuPoints = savedInstanceState.getInt("player2Points");
        playerTurn = savedInstanceState.getBoolean("player1Turn");
        gameStarted = savedInstanceState.getBoolean("gameStarted");
        timeRecorder.stopAndResetTimer(false);
        timeRecorder.setTime(savedInstanceState.getDouble("currentTime"));
        timeRecorder.startRecording();

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,LevelGrid.class));

    }
}