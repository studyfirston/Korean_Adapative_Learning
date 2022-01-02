package com.example.topikappv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultView extends LinearLayout {
    private TextView resultNumber;
    private TextView userAnswer;
    private TextView realAnswer;
    private TextView result;
    private TextView score;
    private TextView time_stamp;
    private TextView change;

    public ResultView(Context context, UserSet mUserSet){
        super(context);

        //인플레이션
        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_user_result,this,true);

        //set icon
        resultNumber = findViewById(R.id.resultNumber);
        //mIcon.setImageResource(data.getIcon());
        userAnswer = findViewById(R.id.userAnswer);

        realAnswer = findViewById(R.id.realAnswer);

        result = findViewById(R.id.result);

        score = findViewById(R.id.score);

        time_stamp = findViewById(R.id.time);

        change = findViewById(R.id.change);

    }

    public void setResultNumber(String data) {resultNumber.setText(data);}

    public void setUserAnswer(String data) {userAnswer.setText(data);}

    public void setRealAnswer(String data) {realAnswer.setText(data);}

    public void setResult(String data) {result.setText(data);}

    public void setScore(String data) {score.setText(data);}

    public void setChange(int data){change.setText(String.valueOf(data));}

    public void setTime_stamp(int data) {
        time_stamp.setText(String.valueOf(data) + "초");}
}
