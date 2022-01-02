package com.example.topikappv2;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ListeningOptionsAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<ListeningOptions> mLOptions =new ArrayList<ListeningOptions>();

    public ListeningOptionsAdapter(Context context){
        this.mContext = context;
    }
    public ListeningOptionsAdapter(){};

    // 아이템을 추가
    public void addItem(ListeningOptions LOptions){
        mLOptions.add(LOptions);
        Log.d("어뎁터 안에 있는 list 사이즈",String.valueOf(mLOptions.size()));
    }

    public ArrayList<ListeningOptions> returnList() {
        return mLOptions;}

    public void setList(ArrayList<ListeningOptions> mList) {
        mLOptions = mList;
    }

    public void deleteItem(String problem_number) {
        int i =1;
        while (i <= mLOptions.size()){
            if(mLOptions.get(mLOptions.size()-i).getProb_num() == problem_number){
                mLOptions.remove(mLOptions.get(mLOptions.size()-i));
            }
            i +=1;
        }
    }

    //아이템의 개수를 리턴
    public int getCount(){
        return mLOptions.size();
    }

    @Override
    public ListeningOptions getItem(int position) {
        return mLOptions.get(position).return_lOptions();
//        new ListeningOptions(getPosition(position),getProb_num(position),getUrl(position),
//                getStr_mp3(position),getIsPlaying(position),
//                getFlag_restart(position),getFlag_first(position)));
    }

//    public boolean getAfter_flag(int position){
//        return mLOptions.get(position).getAfter_flag();
//    }

    public int getPosition(int position) {
        return mLOptions.get(position).getPosition();
    }
    public void setPosition(int position, int pos) {
        this.mLOptions.get(position).setPosition(pos);
    }
    public String getProb_num(int position) {
        return mLOptions.get(position).getProb_num();
    }

    public void setProb_num(int position, String prob_num) {
        this.mLOptions.get(position).setProb_num(prob_num);
    }
    public String getUrl(int position) {
        return mLOptions.get(position).getUrl();
    }
    public void setUrl(int position, String url) {
        this.mLOptions.get(position).setUrl(url);
    }
    public String getStr_mp3(int position) {
        return mLOptions.get(position).getStr_mp3();
    }
    public void setStr_mp3(int position, String str_mp3) {
        this.mLOptions.get(position).setStr_mp3(str_mp3);
    }
    public boolean getIsPlaying(int position) {
        return mLOptions.get(position).getIsPlaying();
    }
    public void setIsPlaying(int position, boolean isPlaying) {
        this.mLOptions.get(position).setIsPlaying(isPlaying);
    }

    public boolean getFlag_restart(int position) {
        return mLOptions.get(position).getFlag_restart();
    }
    public void setFlag_restart(int position, boolean flag_restart) {
        this.mLOptions.get(position).setFlag_restart(flag_restart);
    }
    public boolean getFlag_first(int position) {
        return mLOptions.get(position).getFlag_first();
    }
    public void setFlag_first(int position, boolean flag_first) {
        this.mLOptions.get(position).setFlag_first(flag_first);
    }

    @Override
    //안 쓰는 함수
    public long getItemId(int position) {
        return 0;
    }

    @Override
    //안 쓰는 함수
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}