package com.example.sampleproject.Adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sampleproject.Models.Score;
import com.example.sampleproject.R;
import com.example.sampleproject.SupportClasses.JamesUtilities;

import java.util.List;

public class ScoreAdapter extends BaseAdapter {
//    List<String> usernameList;
//    List<String> gameList;
//    List<Long> scoreList;
//    List<String> infoList;

    List<Score> allScores;

//    public ScoreAdapter(List<String> usernameList, List<String> gameList, List<Long> scoreList, List<String> infoList) {
//        this.usernameList = usernameList;
//        this.gameList = gameList;
//        this.scoreList = scoreList;
//        this.infoList = infoList;
//    }

    public ScoreAdapter(List<Score> allScores) {
        this.allScores = allScores;
    }

    @Override
    public int getCount()
    {
        return allScores.size();
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public Object getItem(int i)
    {
        return allScores.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            LayoutInflater anInflater = LayoutInflater.from(viewGroup.getContext());
            view = anInflater.inflate(R.layout.score_list_model, viewGroup, false);
        }

        /* bind text View */
        TextView txtViewUsername = view.findViewById(R.id.usernameSL);
        txtViewUsername.setText(allScores.get(i).getUsername());
        txtViewUsername.setGravity(Gravity.CENTER);

        TextView txtViewGame = view.findViewById(R.id.gameNameSL);
        txtViewGame.setText(allScores.get(i).getGame());
        txtViewGame.setGravity(Gravity.CENTER);

        TextView txtViewScore = view.findViewById(R.id.scoreSL);
        txtViewScore.setText(JamesUtilities.formatMilliseconds(allScores.get(i).getScore()));
        txtViewScore.setGravity(Gravity.CENTER);

        TextView txtViewInfo = view.findViewById(R.id.infoSL);
        txtViewInfo.setText(allScores.get(i).getAdditionalInfo());
        txtViewInfo.setGravity(Gravity.CENTER);

        return view;
    }
}
