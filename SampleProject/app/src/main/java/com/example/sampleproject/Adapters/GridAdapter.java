package com.example.sampleproject.Adapters;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sampleproject.R;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    List<String> lvlName;
    List<Integer> lvlImg;

    public GridAdapter(List<String> lvlName, List<Integer> lvlImg) {
        this.lvlName = lvlName;
        this.lvlImg = lvlImg;
    }

    @Override
    public int getCount() {
        return lvlName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
  //        LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (v == null) {
            LayoutInflater _inflater = LayoutInflater.from(parent.getContext());
            v = _inflater.inflate(R.layout.gridlayout, parent, false);
        }
        // Get the TextView and ImageView from CustomView for displaying item
        TextView txtview =  v.findViewById(R.id.listitemTextView1);
        ImageView imgview = v.findViewById(R.id.listitemImageView1);

        // Set the text and image for current item using data from map list
        txtview.setText(lvlName.get(position));
        imgview.setImageResource(lvlImg.get(position));
        return v;
    }
}
