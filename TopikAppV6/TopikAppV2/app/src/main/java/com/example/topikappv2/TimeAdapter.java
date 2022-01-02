package com.example.topikappv2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class TimeAdapter extends BaseAdapter{
    private ArrayList<Time> mTimeSet = new ArrayList<Time>();
    private Context mContext = null;

    public TimeAdapter(Context context){
        this.mContext = context;
    }

    public TimeAdapter(){}

    public void addItem(Time timeSet){
        mTimeSet.add(timeSet);
    }

    public List<Time> returnList() {
        return mTimeSet;}

    public void setList(ArrayList<Time> mList) {
        mTimeSet = mList;
    }

    @Override
    public int getCount() {
        return mTimeSet.size();
    }

    @Override
    public Object getItem(int position) {
        return new Time(mTimeSet.get(position).getArranged_Num(), mTimeSet.get(position).getTime(), mTimeSet.get(position).getChange());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResultView2 resultView;

        if(convertView == null){
            resultView = new ResultView2(mContext,mTimeSet.get(position));
        }else {
            resultView = (ResultView2)convertView;
        }
        resultView.setArranged_num(mTimeSet.get(position).getArranged_Num().toString());
        resultView.setTime(mTimeSet.get(position).getTime().toString());
        resultView.setChange(mTimeSet.get(position).getChange().toString());



        if((position % 2) == 1) {
            //convertView.setBackgroundColor(0x800000ff);
            resultView.setBackgroundColor(0x800000ff);
        }else {
            //convertView.setBackgroundColor(0x200000ff);
            resultView.setBackgroundColor(0x200000ff);
        }

        return resultView;
    }
}
