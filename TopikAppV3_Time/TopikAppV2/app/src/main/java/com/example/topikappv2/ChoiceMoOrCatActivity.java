package com.example.topikappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceMoOrCatActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_mo_or_cate);
    }

    public void select_mo(View view) {
        Intent intent = new Intent(this, ChoiceMoActivity.class);
        startActivity(intent);
    }

    public void select_cat(View view) {
        Intent intent = new Intent(this, ChoiceCatActivity.class);
        startActivity(intent);
    }
}