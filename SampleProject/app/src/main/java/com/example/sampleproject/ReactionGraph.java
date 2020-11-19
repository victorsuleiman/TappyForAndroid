package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ReactionGraph extends AppCompatActivity {
    public static  LineGraphSeries<DataPoint> series;

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
        series.setColor(R.color.Blue);
        series.setThickness(10);
        series.setDrawBackground(true);
        series.setBackgroundColor(R.color.Green);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);

    }
}