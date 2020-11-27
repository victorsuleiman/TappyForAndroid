package com.example.hangmanpractice;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    LinearLayout lyContent;
    ImageButton [][] arrImg ;
    int [][] arr;
    String [] arrPath;
    int rowCount ;
    int colCount ;
    Button btnRestart  , btnPlay;
    int score;
    int counterINVisible ;
    TextView txtScore , txtPM;

    String [] goods = {"Toshiba" , "Western", "ADATA" , "Apple" , "Samsung","Dell"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        txtScore = (TextView)findViewById(R.id.txtScore);
        txtPM = (TextView) findViewById(R.id.txtPM);

       // Toast.makeText(getApplicationContext(),"Created!",Toast.LENGTH_SHORT).show();
        score = 0;
        txtScore.setText("Your Score " + score);
        counterINVisible =0;
        rowCount =4;
        colCount =3;

        arrImg = new ImageButton[rowCount][colCount];
        arr = new int [rowCount][colCount];
        arrPath = new String[colCount * rowCount /2 ];
         isSecondClick =true;
        fillArray();
        lyContent = (LinearLayout)findViewById(R.id.content);
        lyContent.addView(CreateBoard());

        ShowImageForFewMoments();

        btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillArray();
                lyContent.setEnabled(false);
                score=0;
                txtScore.setText("Your Score " + score);

                counterINVisible =0;
                isSecondClick =true;
                ShowImageForFewMoments();
            }
        });
    }


    void ShowImageForFewMoments()
    {

//
//        lyContent.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                lyContent.setEnabled(true);
//            }
//        },3000);
        for (int x =0; x<rowCount ; x++)
            for (int y =0; y<colCount ; y++) {
                String str = (String) arrImg[x][y].getTag();
                final int xx = x;
                final int yy = y;
                arrImg[x][y].setImageURI(Uri.parse(str));
                arrImg[x][y].postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrImg[xx][yy].setImageURI(null);
                        arrImg[xx][yy].setEnabled(true);

                    }
                }, 3000);
            }

    }
    Boolean isSecondClick;
    String previousePath;
    View previouseWidget;
    ViewParent previouseParent;
    class ClsImageButtonClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            ((ImageButton)v).setImageURI(Uri.parse((String)v.getTag()));
            final View currentWidget = v;
            final String vTag = (String) v.getTag();
            currentWidget.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkEquals(currentWidget);
                }
            }, 500);
         }




        public void checkEquals(View v) {
//            String pm = (String) v.getTag();
//            txtPM.setText(pm);
            isSecondClick = !isSecondClick;

            if (isSecondClick==true) {
                //   Toast.makeText(getApplication(),"isSecondClick = true ",Toast.LENGTH_SHORT).show();
                final String vTag = (String) v.getTag();
                final View currentWidget = v;
                if ( previousePath.equals(vTag))
                {

                    // ----visible
                    v.setVisibility(View.INVISIBLE);
                    previouseWidget.setVisibility(View.INVISIBLE);
                    score ++;
                    counterINVisible++;
                    txtScore.setText("Your Score " + score);


                    if (counterINVisible==  colCount * rowCount /2)
                    {
                        Toast.makeText(getApplication(),"You Are Winner ",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if (previouseWidget != null)
                        ((ImageButton)previouseWidget).setImageURI(null);
                          ((ImageButton)v).setImageURI(null);

                    score --;
                    txtScore.setText("Your Score " + score);
                }
                previousePath ="aa";
                previouseWidget = null;
            }
            else if (isSecondClick == false)
            {
               // Toast.makeText(getApplication(),"isSecondClick = false ",Toast.LENGTH_SHORT).show();
                previousePath = (String) v.getTag();
                previouseWidget = v;
                ((ImageButton)previouseWidget).setImageURI(Uri.parse(previousePath));
            }
        }
    }

    boolean [] arrchoosePhoto = new boolean [21];
    String choosePhoto ()
    {
        String str="android.resource://com.example.elyas.mathapplication//drawable//a";
        Random rnd = new Random();
        int n = rnd.nextInt(20);
        while (arrchoosePhoto[n])
             n = rnd.nextInt(20);
        arrchoosePhoto[n]=true;
        n = n+ 201;
        str= str + n;
        return str;
    }

    void fillArray()
    {
        for (int i = 0; i < arrchoosePhoto.length; i++) {
            arrchoosePhoto[i] = false;
        }
        for (int x =0; x<rowCount ; x++)
            for (int y =0; y<colCount ; y++) {
                arr[x][y] = -1;
                if (arrImg[x][y] == null)
                    arrImg[x][y] = new ImageButton(this);
                else
                    arrImg[x][y].setVisibility(View.VISIBLE);


                arrImg[x][y].setEnabled(false);
            }


        for (int k =0 ; k < colCount * rowCount /2 ; k++)
        {
            int m = (int) (Math.random() * rowCount) ;
            int n = (int) (Math.random() * colCount );

            while (arr[m][n] != -1)
            {
                m = (int) (Math.random() * rowCount) ;
                n = (int) (Math.random() * colCount );
            }

           arr[m][n] = k;
           int  i = (int) (Math.random() * rowCount );
           int  j = (int) (Math.random() * colCount);

            while (arr[i][j] != -1)
            {
                i = (int) (Math.random() * rowCount );
                j = (int) (Math.random() * colCount);
            }

            arr[i][j]= k;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 300, 1);
            String pathPhoto = choosePhoto();
            Uri uri = Uri.parse(pathPhoto);


            arrImg[i][j].setImageURI(uri);
            arrImg[i][j].setScaleType(ImageView.ScaleType.FIT_XY);
            params.setMargins(5, 5, 5, 5);
            arrImg[i][j].setTag(pathPhoto);
            arrImg[i][j].setLayoutParams(params);
            arrImg[i][j].setOnClickListener(new ClsImageButtonClick());


            arrImg[m][n].setImageURI(uri);
            arrImg[m][n].setScaleType(ImageView.ScaleType.FIT_XY);
            params.setMargins(5, 5, 5, 5);
            arrImg[m][n].setTag(pathPhoto);
            arrImg[m][n].setLayoutParams(params);
            arrImg[m][n].setOnClickListener(new ClsImageButtonClick());
        }
    }

    LinearLayout  CreateBoard()
    {
        LinearLayout ly = new LinearLayout(this);
        ly.setWeightSum(rowCount);
        ly.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < rowCount; i++) {
            LinearLayout lyRow = new LinearLayout(this);
            lyRow.setWeightSum(colCount);

             for (int j = 0; j < colCount; j++) {
                try {
                    lyRow.addView(arrImg[i][j]);
                }
                catch ( Exception e ){

                }
             }
           ly.addView(lyRow);
        }
        return ly;
    }

}
