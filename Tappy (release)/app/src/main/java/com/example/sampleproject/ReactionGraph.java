package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sampleproject.SupportClasses.DBHelper;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ReactionGraph extends AppCompatActivity {

    public static LineGraphSeries<DataPoint> series;
    SQLiteDatabase tappyDB;
    DBHelper aDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_graph);

        com.jjoe64.graphview.GraphView graphView=(com.jjoe64.graphview.GraphView)findViewById(R.id.graphTime);
        graphView.addSeries(series);

        Long minValue = getIntent().getExtras().getLong("minTime");
        Long avgValue = getIntent().getExtras().getLong("avgTime");

        String minTime = minValue.toString();
        String avgTime = avgValue.toString();

        TextView textView=findViewById(R.id.scoreTxt);
        textView.setText("Good Job!\n"+"Your best time is: "+ minTime + "ms"
        + "\nYour average time is: "+ avgTime + "ms");




        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(5);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(10);
        series.setColor(Color.CYAN);
        series.setThickness(15);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(60,242, 199, 70));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(20);
    }
}