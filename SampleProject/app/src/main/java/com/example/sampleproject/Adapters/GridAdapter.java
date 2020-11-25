package com.example.sampleproject.Adapters;


import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

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
        if(convertView==null){
            ImageView imageview = new ImageView(parent.getContext());
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);//maintains the propotion but scales the image to fit into the image view.
            imageview.setLayoutParams(new ViewGroup.LayoutParams(GridView.AUTO_FIT,120));
            imageview.setImageResource(lvlImg.get(position));
            imageview.setBackgroundColor(Color.LTGRAY);
            convertView= imageview;

        }

        return convertView;
    }
}
