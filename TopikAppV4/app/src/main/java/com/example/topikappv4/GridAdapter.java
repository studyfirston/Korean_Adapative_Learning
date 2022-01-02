package com.example.topikappv4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter{
    ArrayList<String> numList;

    public GridAdapter(ArrayList<String> numList){
        this.numList = numList;
    }
    @Override
    public int getCount() {
        return numList.size();
    }

    @Override
    public Object getItem(int position) {
        return numList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move_list,parent,false);
        TextView moveList = convertView.findViewById(R.id.move_list);
        moveList.setText(numList.get(position));
        return convertView;
    }
}
