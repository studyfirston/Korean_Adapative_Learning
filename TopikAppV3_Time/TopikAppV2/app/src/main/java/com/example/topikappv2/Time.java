package com.example.topikappv2;

import java.io.Serializable;

public class Time implements Serializable,Comparable<Time>{
    private int arranged_num;
    private int time;

    public Time(int arranged_num, int time) {
        this.arranged_num = arranged_num;
        this.time = time;
    }

    public Integer getArranged_Num() {
        return arranged_num;
    }

    public void setArranged_num(Integer arranged_num) {
        this.arranged_num = arranged_num;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public int compareTo(Time other) {
        // TODO Auto-generated method stub
        //int target = Integer.parseInt(other.prob_num);

        if(arranged_num == other.arranged_num) return 0;
        else if(arranged_num > other.arranged_num) return 1;
        else return -1;
    }
}
