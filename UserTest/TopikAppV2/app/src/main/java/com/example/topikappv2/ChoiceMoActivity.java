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
        //String value??? ????????? ?????? ==??? ????????? equals??? ???????????????...
        Intent intent = new Intent(this, SolveMoActivity.class);
        if(choice_round.equals("35??????")){
            choice_round = "35";
        } else if(choice_round.equals( "36??????")){
            choice_round = "36";
        } else if(choice_round.equals( "41??????")){
            choice_round = "41";
        } else if(choice_round.equals( "47??????")){
            choice_round = "47";
        } else if(choice_round.equals("52??????")){
            choice_round = "52";
        } else if(choice_round.equals("60??????")){
            choice_round = "60";
        } else if(choice_round.equals("64??????")){
            choice_round = "64";
        }
        if(choice_prob.equals("10??????")){
            choice_prob = "10";
        } else if(choice_prob.equals("15??????")) {
            choice_prob = "15";
        }else if(choice_prob.equals("5??????")){
                choice_prob = "5";
        } else if(choice_prob.equals("20??????")){
            choice_prob = "20";
        } else if(choice_prob.equals("25??????")){
            choice_prob = "25";
        } else if(choice_prob.equals("30??????")){
            choice_prob = "30";
        } else if(choice_prob.equals("35??????")){
            choice_prob = "35";
        } else if(choice_prob.equals("40??????")){
            choice_prob = "40";
        } else if(choice_prob.equals("45??????")){
            choice_prob = "45";
        } else if(choice_prob.equals("50??????")){
            choice_prob = "50";
        } else if(choice_prob.equals("????????????")){
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