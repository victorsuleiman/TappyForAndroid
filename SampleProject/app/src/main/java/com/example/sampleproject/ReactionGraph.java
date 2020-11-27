package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ReactionGraph extends AppCompatActivity {

    public static LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_graph);

        com.jjoe64.graphview.GraphView graphView=(com.jjoe64.graphview.GraphView)findViewById(R.id.graphTime);
        graphView.addSeries(series);



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