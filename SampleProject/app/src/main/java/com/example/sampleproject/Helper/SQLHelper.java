package com.example.sampleproject.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.sampleproject.Models.TapManModel;
import com.example.sampleproject.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SQLHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "TappyDB";
    private static String tablename = "animal";


    private static final String KEY_ID = "ID";
    private static final String KEY_WORD = "Word";
    private static final String KEY_HINT = "Hint";
    Context context;

    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db= db.openOrCreateDatabase("Tappy.db",null);

        }catch (Exception ex){
            Log.e("Db Demo",ex.getMessage());
        }


        tablename="animals";
        String CREATE_TABLE = "CREATE TABLE " + tablename +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                KEY_WORD + " TEXT , " + KEY_HINT + " TEXT " + " )";
        db.execSQL(CREATE_TABLE);

        tablename="activitytables";
         CREATE_TABLE = "CREATE TABLE " + tablename +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                KEY_WORD + " TEXT , " + KEY_HINT + " TEXT " + " )";
        db.execSQL(CREATE_TABLE);

        tablename="twenty_twenty";
         CREATE_TABLE = "CREATE TABLE " + tablename +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                KEY_WORD + " TEXT , " + KEY_HINT + " TEXT " + " )";
        db.execSQL(CREATE_TABLE);

       List<TapManModel> lstAnimal= ReadCSV("animals");
        List<TapManModel> lsrtActivity=ReadCSV("activity");
        List<TapManModel> lst2020= ReadCSV("2020");

        for(int i=0;i<lstAnimal.size();i++){
            addContent(lstAnimal.get(i));
        }
        for(int i=0;i<lsrtActivity.size();i++){
            addContent(lsrtActivity.get(i));
        }
        for(int i=0;i<lst2020.size();i++){
            addContent(lst2020.get(i));
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        onCreate(db);
    }



    public void addContent(TapManModel obj) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WORD, obj.getWord());
        values.put(KEY_HINT, obj.getHint());
        db.insert(tablename, null, values);
        db.close();
    }

    private List<TapManModel> ReadCSV(String fileName) {
        List<TapManModel> arr = new ArrayList<>();

        //populate the list
        InputStream inputStream=null;
        switch(fileName){
            case "animals":
                inputStream=context.getResources().openRawResource(R.raw.animals);
                break;
            case "activity":
                inputStream=context.getResources().openRawResource(R.raw.activitytables);
                break;
            case "2020":
                inputStream=context.getResources().openRawResource(R.raw.twenty_twenty);
                break;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            TapManModel tpm=null;
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {

                String[] eachWord = csvLine.split(",");
                if (!eachWord[0].equals("ID") ) {
                   tpm= new TapManModel(Integer.parseInt(eachWord[0]), eachWord[1], eachWord[2]);
                }
                arr.add(tpm);

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
