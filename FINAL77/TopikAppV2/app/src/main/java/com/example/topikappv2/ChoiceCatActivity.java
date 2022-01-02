package com.example.topikappv2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ChoiceCatActivity extends AppCompatActivity{
    private Spinner spinner_lev;
    private Spinner spinner_cat;
    private Spinner spinner_cat2;
    private Spinner spinner_cat3;

    private Spinner spinner_cat_num;
    private Spinner spinner_prob_cat;
    private Spinner spinner_prob_cat2;
    private Spinner Two_total_spinner_prob_cat;
    private Spinner Three_total_spinner_prob_cat;

    private String choice_lev;
    private String choice_cat;
    private String choice_cat2;
    private String choice_cat3;

    private String choice_cat_num;
    private String choice_prob_cat;
    public static final String CHOICE_LEVEL = "choice_level";
    public static final String CHOICE_CAT = "choice_cat";
    public static final String CHOICE_CAT2 = "choice_cat2";
    public static final String CHOICE_CAT3 = "choice_cat3";
    public static final String CHOICE_CAT_NUM = "choice_cat_num";
    public static final String CHOICE_PROB_CAT = "choice_prob_cat";

    private ArrayList topik1List = new ArrayList();
    private ArrayList topik2List = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_cat);

        spinner_lev = findViewById(R.id.spinner_lev);
        spinner_cat_num = findViewById(R.id.spinner_cat_num);
        spinner_cat = findViewById(R.id.spinner_cat);
        spinner_cat2 = findViewById(R.id.spinner_cat2);
        spinner_cat3 = findViewById(R.id.spinner_cat3);
        spinner_prob_cat = findViewById(R.id.spinner_prob_cat);
        spinner_prob_cat.setVisibility(VISIBLE);
        spinner_prob_cat2 = findViewById(R.id.spinner_prob_cat2);
        Two_total_spinner_prob_cat = findViewById(R.id.Two_total_spinner_prob_cat);
        Three_total_spinner_prob_cat = findViewById(R.id.Three_total_spinner_prob_cat);

        topik1List = new ArrayList<>();
        topik1List.add("내용 일치");
        topik1List.add("내용 추론");
        topik1List.add("빈칸 채우기");
        topik1List.add("상황 추론");
        topik1List.add("상황과 그에 다른 반응");
        topik1List.add("순서 배열");
        topik1List.add("어휘");
        topik1List.add("어휘/문법");
        topik1List.add("중심 생각");

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,topik1List);

        topik2List = new ArrayList<>();
        topik2List.add("내용 일치");
        topik2List.add("내용 추론");
        topik2List.add("빈칸 채우기");
        topik2List.add("순서 배열");
        topik2List.add("어휘/문법");
        topik2List.add("중심 생각");

        ArrayAdapter arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,topik2List);

        spinner_lev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_lev = parent.getItemAtPosition(position).toString();
                if(choice_lev.equals("Topik1")){
                    spinner_cat.setAdapter(arrayAdapter);
                    spinner_cat2.setAdapter(arrayAdapter);
                    spinner_cat3.setAdapter(arrayAdapter);

                }else if(choice_lev.equals("Topik2")){
                    spinner_cat.setAdapter(arrayAdapter2);
                    spinner_cat2.setAdapter(arrayAdapter2);
                    spinner_cat3.setAdapter(arrayAdapter2);

                }

                spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        choice_cat = parent.getItemAtPosition(position).toString();
                        if(choice_cat_num.equals("1개")){
                            if(choice_cat.equals("상황 추론")){
                                spinner_prob_cat.setVisibility(GONE);
                                spinner_prob_cat2.setVisibility(VISIBLE);
                            } else{
                                spinner_prob_cat2.setVisibility(GONE);
                                spinner_prob_cat.setVisibility(VISIBLE);
                            }
                        } else {
                            spinner_prob_cat.setVisibility(GONE);
                            spinner_prob_cat2.setVisibility(GONE);
                        }
                        //Log.d("choice_cat",choice_cat);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinner_cat2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        choice_cat2 = parent.getItemAtPosition(position).toString();
                        //Log.d("choice_cat2",choice_cat2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinner_cat3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        choice_cat3 = parent.getItemAtPosition(position).toString();
                        //Log.d("choice_cat3",choice_cat3);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

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

        Two_total_spinner_prob_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                //Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Three_total_spinner_prob_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                //Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cat_num.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat_num = parent.getItemAtPosition(position).toString();
                TextView text_prob = findViewById(R.id.text_problems);
                text_prob.setVisibility(VISIBLE);
                if(choice_cat_num.equals("1개")){
                    spinner_cat2.setVisibility(GONE);
                    spinner_cat3.setVisibility(GONE);
                    spinner_prob_cat.setVisibility(VISIBLE);
                    spinner_prob_cat2.setVisibility(GONE);
                    Two_total_spinner_prob_cat.setVisibility(GONE);
                    Three_total_spinner_prob_cat.setVisibility(GONE);
                }else if(choice_cat_num.equals("2개")){
                    spinner_cat2.setVisibility(VISIBLE);
                    spinner_cat3.setVisibility(GONE);
                    spinner_prob_cat.setVisibility(GONE);
                    spinner_prob_cat2.setVisibility(GONE);
                    Two_total_spinner_prob_cat.setVisibility(VISIBLE);
                    Three_total_spinner_prob_cat.setVisibility(GONE);

                } else{
                    spinner_cat2.setVisibility(VISIBLE);
                    spinner_cat3.setVisibility(VISIBLE);
                    spinner_prob_cat.setVisibility(GONE);
                    spinner_prob_cat2.setVisibility(GONE);
                    Two_total_spinner_prob_cat.setVisibility(GONE);
                    Three_total_spinner_prob_cat.setVisibility(VISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //하나씩 반복적으로 가져오되, 중복없이 해야함! 회차와 num을 저장하면서 불러와야 한다.

//        choice_lev = spinner_lev.getSelectedItem().toString();

    }

    public void go_solve_cat(View view) {
        //String value를 비교할 때는 ==이 아니라 equals를 사용해야함...
        //Intent intent = new Intent(this, SolveCatActivity.class);
        choice_lev = spinner_lev.getSelectedItem().toString();
        choice_cat_num = spinner_cat_num.getSelectedItem().toString();
        if(choice_cat_num.equals("1개")){
            choice_prob_cat = spinner_prob_cat.getSelectedItem().toString();
        } else if(choice_cat_num.equals("2개")){
            choice_prob_cat = Two_total_spinner_prob_cat.getSelectedItem().toString();
        } else if(choice_cat_num.equals("3개")){
            choice_prob_cat = Three_total_spinner_prob_cat.getSelectedItem().toString();
        }
        choice_cat = spinner_cat.getSelectedItem().toString();
        choice_cat2 = spinner_cat2.getSelectedItem().toString();
        choice_cat3 = spinner_cat3.getSelectedItem().toString();

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

        if(choice_cat_num.equals("2개")){
            if(choice_cat.equals(choice_cat2)){
                Toast.makeText(ChoiceCatActivity.this, "Please choose different catgories. :)", Toast.LENGTH_LONG).show();
            }
            else{
                Send_Intent();
            }
        }else if(choice_cat_num.equals("3개")){
            if(choice_cat.equals(choice_cat2) || choice_cat.equals(choice_cat3) || choice_cat2.equals(choice_cat3)){
                Toast.makeText(ChoiceCatActivity.this, "Please choose different catgories :)", Toast.LENGTH_LONG).show();
            }
            else{
                // 3개가 서로 다를 때
                Send_Intent();
            }
        }
        else{
            //유형이 하나 일 때
            Send_Intent();
        }
    }
    public void Send_Intent(){
        Intent intent = new Intent(this, SolveCatActivity.class);
        intent.putExtra(CHOICE_CAT,choice_cat);
        if(choice_lev.equals("Topik1")){
            choice_lev = "1";
        } else if(choice_lev.equals("Topik2")){
            choice_lev = "2";
        }
        if (choice_cat_num.equals("2개")){
            intent.putExtra(CHOICE_CAT2,choice_cat2);
        } else if(choice_cat_num.equals("3개")){
            intent.putExtra(CHOICE_CAT2,choice_cat2);
            intent.putExtra(CHOICE_CAT3,choice_cat3);
        }

        //Log.d("level 확인",choice_lev);
        intent.putExtra(CHOICE_LEVEL,choice_lev);
        intent.putExtra(CHOICE_CAT_NUM,choice_cat_num);
        intent.putExtra(CHOICE_PROB_CAT,choice_prob_cat);
        startActivity(intent);
    }
}