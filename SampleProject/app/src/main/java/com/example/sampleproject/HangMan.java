package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleproject.Models.TapManModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HangMan extends AppCompatActivity {
    Spinner category;
    final String[] wordsAnimal = new String[]{"MOKNEY", "RHINO", "CAT", "DOG", "BIRD", "HIPPO", "COW", "HORSE", "WHALE",
            "TURTLE", "FISH", "GOAT", "RABBIT", "SNAKE"};
    final String[] wordsActivities = new String[]{"SKI", "RUN", "JUMP", "SING", "DANCE", "WRITE", "BIKE", "DRIVE", "BIKE",
            "DRIVE", "PARTY", "EAT", "DRINK", "CLEAN", "STUDY", "DIE"};
    final String[] words2020 = new String[]{"QUARANTINE", "COVID", "CORONA", "WWIII", "BLACK LIVES MATTER"};

    String[] button = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U"
            , "V", "W", "X", "Y", "Z"};

    List<Integer> hangManPics = new ArrayList<>(Arrays.asList(R.drawable.hangman_lvl0,
            R.drawable.hangman_lvl1, R.drawable.hangman_lvl2, R.drawable.hangman_lvl3,
            R.drawable.hangman_lvl4, R.drawable.hangman_lvl5, R.drawable.hangman_lvl6));


    String wordChosen;
    ImageView hangManImg;
    TextView dashBox;
    TextView msgBox;
    String dash = "";
    int tryCounter = 0; //amount of times the user guessed
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_man);

        createDB();
        createTables();

        List<String[]> animals = ReadCSV("animals");

        for(int i=1;i<animals.size();i++){ //we start with one cause we don't need the column titles
            int id=Integer.parseInt(animals.get(i)[0]);
            String word=animals.get(i)[1];
            String hint=animals.get(i)[2];

            addWordsToAnimals(id,word,hint);

        }
        List<String[]> activities = ReadCSV("activity");

        for(int i=1;i<activities.size();i++){ //we start with one cause we don't need the column titles
            int id=Integer.parseInt(activities.get(i)[0]);
            String word=activities.get(i)[1];
            String hint=activities.get(i)[2];

            addWordsToActivities(id,word,hint);

        }
        List<String[]> twenty_twenty = ReadCSV("activity");

        for(int i=1;i<twenty_twenty.size();i++){ //we start with one cause we don't need the column titles
            int id=Integer.parseInt(twenty_twenty.get(i)[0]);
            String word=twenty_twenty.get(i)[1];
            String hint=twenty_twenty.get(i)[2];

            addWordsToActivities(id,word,hint);

        }



        hangManImg = findViewById(R.id.hangManImg);
        category = findViewById(R.id.categorySpin);
        dashBox = findViewById(R.id.dashBox);
        msgBox = findViewById(R.id.msgBox);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //  Toast.makeText(HangMan.this, "Kar kon", Toast.LENGTH_SHORT).show();
                //reset game when changed
                dash = ""; //reset dash board
                //reset picture
                hangManImg.setImageResource(R.drawable.hangman_lvl0);
                //reset tries
                tryCounter = 0;
                //reset message box
                msgBox.setText("");
                letterColorBack(button);
                switch (position) {
                    case 0:
                        wordChosen = "";
                        Toast.makeText(HangMan.this, "Please choose a category", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        wordChosen = getRandom(wordsAnimal);
                        break;
                    case 2:
                        wordChosen = getRandom(wordsActivities);
                        break;
                    case 3:
                        wordChosen = getRandom(words2020);
                        break;
                }

                for (int i = 0; i < wordChosen.length(); i++) {
                    dash = dash + "_";


                }
                String displayText = interlace(dash);
                dashBox.setText(displayText);
                // msgBox.setText(wordChosen);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static String interlace(String aString) {
        String interlaced = " ";
        for (int i = 0; i < aString.length(); i++) {
            interlaced += aString.charAt(i) + " ";
        }
        return interlaced;
    }

    public void touchLetter(View view) {
        Button btn = (Button) view;
        String dash2 = "";
        // Toast.makeText(this, "Letter entered "+btn.getText(), Toast.LENGTH_LONG).show();
        char letterChosen = btn.getText().charAt(0);
        boolean correct = false; //initially false

        //only run code if there are still tries left
        if (tryCounter > 5) //if out of tries (check for lose)
        {
            msgBox.setText("Game over Bruh!");

        } else //still have tries
        {
            for (int i = 0; i < wordChosen.length(); i++) {
                if (letterChosen == wordChosen.charAt(i)) {
                    dash2 = dash2 + letterChosen;
                    correct = true; //user did get the correct letter


                } else {
                    dash2 = dash2 + dash.charAt(i);
                    btn.setBackgroundColor(getResources().getColor(R.color.lightRed));
                }
            }

            //if user guessed wrong
            if (correct == false) {
                tryCounter++;
                int imgIs = hangManPics.get(tryCounter);
                hangManImg.setImageResource(imgIs);
                Toast.makeText(this, "Wrong letter", Toast.LENGTH_SHORT).show();
            } else //user guessed right
            {
                if (dash2.indexOf("_") == -1) //no _ means we've won!
                {
                    //Win condition
                    hangManImg.setImageResource(R.drawable.hangman_winpose);
                    msgBox.setText("You Won!");
                }
            }

            dash = dash2;
            String displayText = interlace(dash);
            dashBox.setText(displayText);
        } //end else (still have tries)
    }


    public void letterColorBack(String[] letters) {
        Button[] letterButton = new Button[26];

        for (int i = 0; i < letters.length; i++) {
            String buttonNumber = "button" + letters[i];
            int buttonId = getResources().getIdentifier(buttonNumber, "id", getPackageName());
            Log.e("TAG", "button " + buttonId);
            letterButton[i] = findViewById(buttonId);
            letterButton[i].setBackgroundColor(getResources().getColor(R.color.buttonHangMan));

        }


    }


    public void createDB() {
        try {
            db = db.openOrCreateDatabase("HangMan.db", null);

        } catch (Exception ex) {
            Log.e("Tappy DB", ex.getMessage());
        }

    }

    private void createTables(){
        try {
            String setPRAGMAForeignKeyOn = "PRAGMA foreign_keys = ON;";
            String dropAnimalsTable = "DROP TABLE IF EXISTS " + "animals;";
            String dropActivitiesTable = "DROP TABLE IF EXISTS " + "activities;";
            String drop2020Table = "DROP TABLE IF EXISTS " + "twenty_twenty;";

            String createAnimalsTable = "CREATE TABLE animals " + "(ID INT  PRIMARY KEY, " +
                    "Word TEXT, Hint TEXT);";
            String createActivitiesTable = "CREATE TABLE activities " + "(ID INT  PRIMARY KEY, " +
                    "Word TEXT, Hint TEXT);";

            String create2020Table = "CREATE TABLE twenty_twenty " + "(ID INT  PRIMARY KEY, " +
                    "Word TEXT, Hint TEXT);";

            db.execSQL(setPRAGMAForeignKeyOn);
            db.execSQL(dropAnimalsTable);
            db.execSQL(dropActivitiesTable);
            db.execSQL(drop2020Table);

            db.execSQL(createAnimalsTable);
            db.execSQL(createActivitiesTable);
            db.execSQL(create2020Table);
        }catch (Exception ex){

        }
    }

            //returns to Level List when back button is pressed
//    @Override
//    public void onBackPressed(){
//
//        startActivity(new Intent(this,LevelGrid.class));
//    }
//
//
//
//        }catch(Exception ex){
//            Log.e("DB demo","Error msg"+ex.getMessage());
//        }
//
//    }

            public void addWordsToAnimals (Integer id, String Word, String Hint){
                long result;
                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", id);
                contentValues.put("Word", Word);
                contentValues.put("Hint", Hint);

                result = db.insert("animals", null, contentValues);// by adding the null we are specifying that no column can be null
                if (result != -1) {
                    Log.e("DB demo", "It is done");
                } else {
                    Log.e("DB demo", " Error inserting rec");
                }
            }

            public void addWordsToActivities (Integer id, String Word, String Hint){
                long result;
                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", id);
                contentValues.put("Word", Word);
                contentValues.put("Hint", Hint);

                result = db.insert("activitytables", null, contentValues);// by adding the null we are specifying that no column can be null
                if (result != -1) {
                    Log.e("DB demo", "It is done");
                } else {
                    Log.e("DB demo", " Error inserting rec");
                }
            }

            public void addWordsTo2020 (Integer id, String Word, String Hint){
                long result;
                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", id);
                contentValues.put("Word", Word);
                contentValues.put("Hint", Hint);

                result = db.insert("twenty_twenty", null, contentValues);// by adding the null we are specifying that no column can be null
                if (result != -1) {
                    Log.e("DB demo", "It is done");
                } else {
                    Log.e("DB demo", " Error inserting rec");
                }
            }


            private List<String[]> ReadCSV (String fileName){
                List<String[]> arr = new ArrayList<>();

                //populate the list
                InputStream inputStream = null;
                switch (fileName) {
                    case "animals":
                        inputStream = getResources().openRawResource(R.raw.animals);
                        break;
                    case "activity":
                        inputStream = getResources().openRawResource(R.raw.activitytables);
                        break;
                    case "2020":
                        inputStream = getResources().openRawResource(R.raw.twenty_twenty);
                        break;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                try {
                    String csvLine;
                    while ((csvLine = reader.readLine()) != null) {

                        String[] eachWord = csvLine.split(",");
                        if (!eachWord[0].equals("ID")) {
                            arr.add(eachWord);
                        }


                    }

                } catch (IOException ex) {
                    throw new RuntimeException("Error " + ex);


                } finally {
                    try {
                        inputStream.close();

                    } catch (IOException ex) {
                        throw new RuntimeException("Error" + ex);

                    }
                }

                return arr;
            }
        }
    



