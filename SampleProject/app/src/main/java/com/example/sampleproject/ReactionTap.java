package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class ReactionTap extends AppCompatActivity {
    enum MyState
    {
        waitState ,
        clickState,
        reactionState,

    }
    MyState myState= MyState.waitState;
    Button buttonReaction;
    final int[] time=new int[]{3000,4000,5000,6000,7000};
    long startTime;
    long endTime;
    int rnd;
    final int LIMIT=5;
    int counter=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_tap);

        //GraphView.series=new LineGraphSeries<>();

//        GraphView.series.appendData(new DataPoint(0,0),true,20);

        buttonReaction=findViewById(R.id.buttonReaction);
        rnd = new Random().nextInt(time.length);


        buttonReaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch(myState){
                    case waitState:
                        waitMethod();
                        break;
                    case clickState:
                        clickMethod();
                        break;
                    case reactionState:
                        reactionMethod();
                        break;

                }

            }
        });


    }
    public void waitMethod(){
        buttonReaction.setBackgroundColor(getResources().getColor(R.color.Red));
        buttonReaction.setEnabled(false);
        rnd = new Random().nextInt(time.length);
        buttonReaction.setText(R.string.wait);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                myState= MyState.clickState;
                clickMethod();



            }
        },time[rnd]);

    }
    public void clickMethod(){
        buttonReaction.setBackgroundColor(getResources().getColor(R.color.Green));
        buttonReaction.setEnabled(true);
        buttonReaction.setText(R.string.click);
        myState=MyState.reactionState;
        startTime=System.currentTimeMillis();



    }
    public void reactionMethod(){
        buttonReaction.setBackgroundColor(getResources().getColor(R.color.Blue));
        endTime=System.currentTimeMillis();
        String str= getResources().getString(R.string.clickagain);
        double showTime=(endTime-startTime);
        buttonReaction.setText(str+"\n"+showTime+" ms");
        myState= MyState.waitState;
//        GraphView.series.appendData(new DataPoint(counter,showTime),true,20);
        counter++;

        if(counter==LIMIT){
           startActivity(new Intent(ReactionTap.this,ReactionGraph.class));

        }


    }



}
}