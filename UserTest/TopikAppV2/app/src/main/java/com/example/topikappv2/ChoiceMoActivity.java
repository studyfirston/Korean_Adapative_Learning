package com.example.topikappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceMoActivity extends AppCompatActivity{
    private Spinner spinner_lev;
    private Spinner spinner_mo;
    private Spinner spinner_prob;
    private String choice_level;
    private String choice_round;
    private String choice_prob;
    public static final String CHOICE_LEVEL = "choice_level";
    public static final String CHOICE_ROUND = "choice_round";
    public static final String CHOICE_PROB = "choice_prob";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_mo);

        spinner_lev = findViewById(R.id.spinner_lev);
        spinner_mo = findViewById(R.id.spinner_mo);
        spinner_prob = findViewById(R.id.spinner_prob);

        spinner_lev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_level = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_mo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_round = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    public void go_solve_mo(View view) {
        //String value를 비교할 때는 ==이 아니라 equals를 사용해야함...
        Intent intent = new Intent(this, SolveMoActivity.class);
        if(choice_round.equals("35회차")){
            choice_round = "35";
        } else if(choice_round.equals( "36회차")){
            choice_round = "36";
        } else if(choice_round.equals( "41회차")){
            choice_round = "41";
        } else if(choice_round.equals( "47회차")){
            choice_round = "47";
        } else if(choice_round.equals("52회차")){
            choice_round = "52";
        } else if(choice_round.equals("60회차")){
            choice_round = "60";
        } else if(choice_round.equals("64회차")){
            choice_round = "64";
        }
        if(choice_prob.equals("10문제")){
            choice_prob = "10";
        } else if(choice_prob.equals("15문제")) {
            choice_prob = "15";
        }else if(choice_prob.equals("5문제")){
                choice_prob = "5";
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

        if(choice_level.equals("Topik1")){
            choice_level = "1";
        } else if(choice_level.equals("Topik2")){
            choice_level = "2";
        }
        intent.putExtra(CHOICE_ROUND,choice_round);
        intent.putExtra(CHOICE_PROB,choice_prob);
        intent.putExtra(CHOICE_LEVEL,choice_level);
        startActivity(intent);
    }
}