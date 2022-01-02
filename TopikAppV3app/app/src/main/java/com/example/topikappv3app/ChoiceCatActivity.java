package com.example.topikappv3app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceCatActivity extends AppCompatActivity{
    private Spinner spinner_cat;
    private Spinner spinner_cat2;
    private Spinner spinner_cat3;
    private Spinner spinner_cat_num;
    private Spinner spinner_prob_cat;
    private Spinner spinner_prob_cat2;
    private Spinner Two_total_spinner_prob_cat;
    private Spinner Three_total_spinner_prob_cat;

    private String choice_cat;
    private String choice_cat2;
    private String choice_cat3;
    private String choice_cat_num;
    private String choice_prob_cat;
    public static final String CHOICE_CAT = "choice_cat";
    public static final String CHOICE_CAT2 = "choice_cat2";
    public static final String CHOICE_CAT3 = "choice_cat3";
    public static final String CHOICE_CAT_NUM = "choice_cat_num";
    public static final String CHOICE_PROB_CAT = "choice_prob_cat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_cat);

        spinner_cat_num = findViewById(R.id.spinner_cat_num);
        spinner_cat = findViewById(R.id.spinner_cat);
        spinner_cat2 = findViewById(R.id.spinner_cat2);
        spinner_cat3 = findViewById(R.id.spinner_cat3);
        spinner_prob_cat = findViewById(R.id.spinner_prob_cat);
        spinner_prob_cat.setVisibility(View.VISIBLE);
        spinner_prob_cat2 = findViewById(R.id.spinner_prob_cat2);
        Two_total_spinner_prob_cat = findViewById(R.id.Two_total_spinner_prob_cat);
        Three_total_spinner_prob_cat = findViewById(R.id.Three_total_spinner_prob_cat);


        spinner_cat_num.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat_num = parent.getItemAtPosition(position).toString();
                if(choice_cat_num.equals("1개")){
                    spinner_cat2.setVisibility(View.GONE);
                    spinner_cat3.setVisibility(View.GONE);
                    spinner_prob_cat.setVisibility(View.VISIBLE);
                    spinner_prob_cat2.setVisibility(View.GONE);
                    Two_total_spinner_prob_cat.setVisibility(View.GONE);
                    Three_total_spinner_prob_cat.setVisibility(View.GONE);
                }else if(choice_cat_num.equals("2개")){
                    spinner_cat2.setVisibility(View.VISIBLE);
                    spinner_cat3.setVisibility(View.GONE);
                    spinner_prob_cat.setVisibility(View.GONE);
                    spinner_prob_cat2.setVisibility(View.GONE);
                    Two_total_spinner_prob_cat.setVisibility(View.VISIBLE);
                    Three_total_spinner_prob_cat.setVisibility(View.GONE);

                } else{
                    spinner_cat2.setVisibility(View.VISIBLE);
                    spinner_cat3.setVisibility(View.VISIBLE);
                    spinner_prob_cat.setVisibility(View.GONE);
                    spinner_prob_cat2.setVisibility(View.GONE);
                    Two_total_spinner_prob_cat.setVisibility(View.GONE);
                    Three_total_spinner_prob_cat.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //하나씩 반복적으로 가져오되, 중복없이 해야함! 회차와 num을 저장하면서 불러와야 한다.

        spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat = parent.getItemAtPosition(position).toString();
                if(choice_cat_num.equals("1개")){
                    if(choice_cat.equals("상황 추론")){
                        spinner_prob_cat.setVisibility(View.GONE);
                        spinner_prob_cat2.setVisibility(View.VISIBLE);
                    } else{
                        spinner_prob_cat2.setVisibility(View.GONE);
                        spinner_prob_cat.setVisibility(View.VISIBLE);
                    }
                } else {
                    spinner_prob_cat.setVisibility(View.GONE);
                    spinner_prob_cat2.setVisibility(View.GONE);
                }
                Log.d("choice_cat",choice_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cat2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat2 = parent.getItemAtPosition(position).toString();
                Log.d("choice_cat2",choice_cat2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cat3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat3 = parent.getItemAtPosition(position).toString();
                Log.d("choice_cat3",choice_cat3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_prob_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                //Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_prob_cat2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                //Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Two_total_spinner_prob_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Three_total_spinner_prob_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void go_solve_cat(View view) {
        //String value를 비교할 때는 ==이 아니라 equals를 사용해야함...
        //Intent intent = new Intent(this, SolveCatActivity.class);
//        if(choice_prob_cat.equals("5문제")){
//            choice_prob_cat = "5";
//        } else if(choice_prob_cat.equals("10문제")){
//            choice_prob_cat = "10";
//        } else if(choice_prob_cat.equals("3문제")){
//            choice_prob_cat = "3";
//        } else if(choice_prob_cat.equals("8문제")){
//            choice_prob_cat = "8";
//        }
        if(choice_prob_cat.equals("5문제")){
            choice_prob_cat = "5";
        } else if(choice_prob_cat.equals("10문제")){
            choice_prob_cat = "10";
        } else if(choice_prob_cat.equals("3문제")){
            choice_prob_cat = "3";
        } else if(choice_prob_cat.equals("8문제")){
            choice_prob_cat = "8";
        } else if(choice_prob_cat.equals("15문제")){
            choice_prob_cat = "15";
        } else if(choice_prob_cat.equals("20문제")){
            choice_prob_cat = "20";
        }

        Log.d("choice_prob_final",choice_prob_cat);


        if(choice_cat_num.equals("2개")){
            if(choice_cat.equals(choice_cat2)){
                Toast.makeText(ChoiceCatActivity.this, "서로 다른 유형을 선택해 주세요 :)", Toast.LENGTH_LONG).show();
            }else{
                // 2개가 서로 다를 때
                Send_Intent();
            }
        }else if(choice_cat_num.equals("3개")){
            if(choice_cat.equals(choice_cat2) || choice_cat.equals(choice_cat3) || choice_cat2.equals(choice_cat3)){
                Toast.makeText(ChoiceCatActivity.this, "서로 다른 유형을 선택해 주세요 :)", Toast.LENGTH_LONG).show();
            }
            else{
                // 3개가 서로 다를 때
                Send_Intent();
            }
        } else{
            //유형이 하나 일 때
            Send_Intent();
        }
    }
    public void Send_Intent(){
        Intent intent = new Intent(this, SolveCatActivity.class);
        intent.putExtra(CHOICE_CAT,choice_cat);
        if (choice_cat_num.equals("2개")){
            intent.putExtra(CHOICE_CAT2,choice_cat2);
        } else if(choice_cat_num.equals("3개")){
            intent.putExtra(CHOICE_CAT2,choice_cat2);
            intent.putExtra(CHOICE_CAT3,choice_cat3);
        }
        Log.d("개수 확인",choice_cat_num);
        intent.putExtra(CHOICE_CAT_NUM,choice_cat_num);
        intent.putExtra(CHOICE_PROB_CAT,choice_prob_cat);
        startActivity(intent);
    }
}