package com.example.topikappv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultView2 extends LinearLayout {
    private TextView arranged_num;
    private TextView time;
    private TextView change;

    public ResultView2(Context context, Time mTimeSet){
        super(context);

        //인플레이션
        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_time_list,this,true);

        //set icon
        arranged_num = findViewById(R.id.arranged_num);
        //mIcon.setImageResource(data.getIcon());
        time = findViewById(R.id.time);
        change = findViewById(R.id.change);
    }


    public void setArranged_num(String data) {arranged_num.setText(data);}

    public void setTime(String data) {time.setText(data);}

    public void setChange(String data) {change.setText(data);}
}
