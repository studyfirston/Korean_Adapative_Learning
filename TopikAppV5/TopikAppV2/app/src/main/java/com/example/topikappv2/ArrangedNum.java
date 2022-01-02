package com.example.topikappv2;

import android.util.Log;

import java.util.ArrayList;

public class ArrangedNum {
    private ArrayList<String> mArrangedNum = new ArrayList<String>();
    private ArrayList<Integer> mIntArrangedNum = new ArrayList<Integer>();
    private String arranged_num;


    public ArrangedNum(){}

    public ArrangedNum(String arranged_num){
        this.arranged_num = arranged_num;
    }

    public int getCount(){
        return mArrangedNum.size();
    }
    public String get_num(int pos){
        return mArrangedNum.get(pos);
    }
    public Integer get_int_num(int pos){
        return mIntArrangedNum.get(pos);
    }


    public void add_num(String arranged_num){
        mArrangedNum.add(arranged_num);
    }
    public void add_num_int(int arranged_num){
        mIntArrangedNum.add(arranged_num);
        set_numList(this.mIntArrangedNum);
    }

    public void add_num_pos(int pos,String arranged_num){
        mArrangedNum.add(pos,arranged_num);
    }

    public ArrayList<String> return_list(){
        return mArrangedNum;
    }

    public void set_list(ArrayList<String> mList){
        this.mArrangedNum = mList;
    }
    public void set_numList(ArrayList<Integer> mList){
        this.mIntArrangedNum = mList;
        ArrayList<String> mStringNum = new ArrayList<String>();
        for(int i = 0; i<mIntArrangedNum.size();i++){
            mStringNum.add(mIntArrangedNum.get(i).toString());
        }
        set_list(mStringNum);
    }

}