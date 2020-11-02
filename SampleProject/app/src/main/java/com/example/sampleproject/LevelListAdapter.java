package com.example.sampleproject;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LevelListAdapter extends BaseAdapter
{
    List<String> levelName;
    List<Integer> levelImg;

    public LevelListAdapter(List<String> levelName, List<Integer> levelImg)
    {
        this.levelName = levelName;
        this.levelImg = levelImg;
    }

    @Override
    public int getCount()
    {
        return levelName.size();
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public Object getItem(int i)
    {
        return levelName.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            LayoutInflater anInflater = LayoutInflater.from(viewGroup.getContext());
            view = anInflater.inflate(R.layout.level_list_model, viewGroup, false);
        }

        /* bind text View */
        TextView txtViewLevel = view.findViewById(R.id.txtModel);
        txtViewLevel.setText(levelName.get(i));
        txtViewLevel.setGravity(Gravity.CENTER);

        /* bind img View */
        ImageView imgViewLevel = view.findViewById(R.id.imgModel);
        imgViewLevel.setImageResource(levelImg.get(i));

        return view;
    }
}
