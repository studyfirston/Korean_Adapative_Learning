package com.example.topikappv2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter{
    private int totalScore = 0;
    private Context mContext = null;
    private ArrayList<UserSet> mUserSet = new ArrayList<UserSet>();

    public UserAdapter(Context context){
        this.mContext = context;
    }
    public UserAdapter(){}

    // 아이템을 추가
    public void addItem(UserSet userSet){
        mUserSet.add(userSet);
    }

    public List<UserSet> returnList() {
        return mUserSet;}

    public void setList(ArrayList<UserSet> mList) {
        mUserSet = mList;
    }

    public void deleteItem(int problem_number) {
        int i =1;
        while (i <= mUserSet.size()){
            if(mUserSet.get(mUserSet.size()-i).getArranged_num() == String.valueOf(problem_number)){
                mUserSet.remove(mUserSet.get(mUserSet.size()-i));
            }
            i +=1;
        }
    }

    //아이템의 개수를 리턴
    public int getCount(){
        return mUserSet.size();
    }

    @Override
    public UserSet getItem(int position) {
//        return new UserSet(getArranged_num(position).toString(),getU_answer(position).toString(),getNumber(position).toString(),
//                getP_answer(position).toString());
        return new UserSet(getArranged_num(position).toString(),getNumber(position).toString(),getU_answer(position).toString(),
                getP_answer(position).toString(),getProb_set(position).toString(),getScore(position).toString());
    }

    public Object getProb_set(int position) { return mUserSet.get(position).getProb_set();}
    public Object getArranged_num(int position) {
        return mUserSet.get(position).getArranged_num();
    }
    public Object getU_answer(int position){
        return mUserSet.get(position).getU_answer();
    }
    public Object getP_answer(int position){
        return mUserSet.get(position).getP_answer();
    }
    public Object getNumber(int position) {
        return mUserSet.get(position).getProb_num();
    }

    public Object getResult(int position) {
        return mUserSet.get(position).getFinal_result();
    }
    public Object getScore(int position) {return mUserSet.get(position).getScore();}

    public int getTotal(int position){
        totalScore = totalScore + Integer.parseInt(mUserSet.get(position).getScore());
        return totalScore;
    }

    public long getItemId(int position){
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent){

        ResultView resultView;

        if(convertView == null){
            resultView = new ResultView(mContext,mUserSet.get(position));
        }else {
            resultView = (ResultView)convertView;
        }

        resultView.setResultNumber(mUserSet.get(position).getArranged_num());
        resultView.setUserAnswer(mUserSet.get(position).getU_answer());
        resultView.setRealAnswer(mUserSet.get(position).getP_answer());
        resultView.setResult(mUserSet.get(position).getFinal_result());
        resultView.setScore(mUserSet.get(position).getScore());


        if((position % 2) == 1) {
            //convertView.setBackgroundColor(0x800000ff);
            resultView.setBackgroundColor(0x800000ff);
        }else {
            //convertView.setBackgroundColor(0x200000ff);
            resultView.setBackgroundColor(0x200000ff);
        }

        return resultView;
    }
}