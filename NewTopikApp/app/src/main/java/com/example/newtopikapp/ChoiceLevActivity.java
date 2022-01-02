package com.example.newtopikapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChoiceLevActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_lev);
    }

    public void select_lev1(View view) {
        Intent intent = new Intent(this,ChoiceMoActivity.class);
        startActivity(intent);
    }

    public void select_lev2(View view) {
        Intent intent = new Intent(this,ChoiceMoActivity.class);
        startActivity(intent);
    }
}