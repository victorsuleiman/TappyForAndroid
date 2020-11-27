package com.example.sampleproject.Adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sampleproject.R;
import com.example.sampleproject.SupportClasses.JamesUtilities;

import java.util.List;

public class ScoreAdapter extends BaseAdapter {
    List<String> usernameList;
    List<String> gameList;
    List<Long> scoreList;
    List<String> infoList;

    public ScoreAdapter(List<String> usernameList, List<String> gameList, List<Long> scoreList, List<String> infoList) {
        this.usernameList = usernameList;
        this.gameList = gameList;
        this.scoreList = scoreList;
        this.infoList = infoList;
    }

    @Override
    public int getCount()
    {
        return scoreList.size();
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public Object getItem(int i)
    {
        return scoreList.get(i);
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
        txtViewUsername.setText(usernameList.get(i));
        txtViewUsername.setGravity(Gravity.CENTER);

        TextView txtViewGame = view.findViewById(R.id.gameNameSL);
        txtViewGame.setText(gameList.get(i));
        txtViewGame.setGravity(Gravity.CENTER);

        TextView txtViewScore = view.findViewById(R.id.scoreSL);
        txtViewScore.setText(JamesUtilities.formatMilliseconds(scoreList.get(i)));
        txtViewScore.setGravity(Gravity.CENTER);

        TextView txtViewInfo = view.findViewById(R.id.infoSL);
        txtViewInfo.setText(infoList.get(i));
        txtViewInfo.setGravity(Gravity.CENTER);

        return view;
    }
}
