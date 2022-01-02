package com.example.topikappv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class TimeActivity extends AppCompatActivity{
    private TimeAdapter tAdapter;
    private ListView listView;
    private ArrayList<Time> time_list;
    private int arranged_num;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        listView = findViewById(R.id.time_list_view);
        tAdapter = new TimeAdapter(this);
        Intent intent = getIntent();
        time_list = (ArrayList<Time>) intent.getSerializableExtra("time_list");
        tAdapter.setList(time_list);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_time_list, listView, false);

        listView.setVisibility(VISIBLE);
        listView.setAdapter(tAdapter);



    }
}