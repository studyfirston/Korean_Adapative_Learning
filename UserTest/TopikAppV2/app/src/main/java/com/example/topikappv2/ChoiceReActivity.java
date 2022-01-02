package com.example.topikappv2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceReActivity extends AppCompatActivity {
    private Spinner spinner_prob;
    private String choice_prob;
    public static final String CHOICE_PROB = "choice_prob";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_re);
        spinner_prob = findViewById(R.id.spinner_prob);

        Intent intent = getIntent();

        spinner_prob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void go_solve_re(View view) {
        //String value를 비교할 때는 ==이 아니라 equals를 사용해야함...
        Intent intent = new Intent(this, SolveReActivity.class);

        if(choice_prob.equals("5문제")){
            choice_prob = "5";
        } else if(choice_prob.equals("10문제")) {
            choice_prob = "10";
        }else if(choice_prob.equals("15문제")){
            choice_prob = "15";
        } else if(choice_prob.equals("20문제")){
            choice_prob = "20";
        } else if(choice_prob.equals("25문제")){
            choice_prob = "25";
        } else if(choice_prob.equals("30문제")){
            choice_prob = "30";
        } else if(choice_prob.equals("35문제")){
            choice_prob = "35";
        } else if(choice_prob.equals("40문제")){
            choice_prob = "40";
        } else if(choice_prob.equals("45문제")){
            choice_prob = "45";
        } else if(choice_prob.equals("50문제")){
            choice_prob = "50";
        } else if(choice_prob.equals("전체풀기")){
            choice_prob = "70";
        }

        intent.putExtra(CHOICE_PROB,choice_prob);
        startActivity(intent);
    }
}
