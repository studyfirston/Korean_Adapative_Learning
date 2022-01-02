package com.example.newtopikapp;

import java.io.Serializable;

public class UserSet implements Serializable, Comparable<UserSet>{

    private String number; // problem_number
    private String u_answer; //user_answer
    private String r_answer; //Real_answer
    private String final_result;
    private String point; //문제 점수
    private int totalScore;
    //private int resId;

    public UserSet(String number, String user_answer, String real_answer, String point) {
        this.number = number;
        this.u_answer = user_answer;
        this.r_answer = real_answer;
        if(Integer.parseInt(u_answer) == Integer.parseInt(r_answer)){
            this.final_result = "O";
            totalScore += Integer.parseInt(point);
        } else {
            this.final_result = "X";
        }
        this.point = point;
    }

    // UserSet 만들 때 쓰는 것

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getU_answer() {
        return u_answer;
    }

    public void setU_answer(String u_answer) {
        this.u_answer = u_answer;
    }

    public String getP_answer() {
        return r_answer;
    }

    public void setP_answer(String r_answer) {
        this.r_answer = r_answer;
    }

    public String getFinal_result() {return final_result;}

    public void setFinal_result(String final_result) {this.final_result = final_result;}

    public String getR_answer() {
        return r_answer;
    }

    public void setR_answer(String r_answer) {
        this.r_answer = r_answer;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public int getTotal(){
        return totalScore;
    }

    @Override
    public int compareTo(com.example.newtopikapp.UserSet other) {
        // TODO Auto-generated method stub
        int target = Integer.parseInt(other.number);
        if(number == other.number) return 0;
        else if(Integer.parseInt(number) > Integer.parseInt(other.number)) return 1;
        else return -1;
    }

}

