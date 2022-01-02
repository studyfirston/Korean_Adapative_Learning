package com.example.topikappv3app;

import java.io.Serializable;

public class UserSet implements Serializable,Comparable<UserSet>{

    //정렬 기준
    private String arranged_num;
    private String prob_num; // problem_number

    private String u_answer; //user_answer
    private String r_answer; //Real_answer
    private String final_result;
    private String score;
    private int totalScore;

    private String prob_set;
    //private int resId;

//    public UserSet(String arranged_num, String number, String user_answer, String real_answer,String score) {
//        this.arranged_num =arranged_num;
//   // public UserSet(String number, String user_answer, String real_answer, String score) {
//        this.prob_num = number;
//        this.u_answer = user_answer;
//        this.r_answer = real_answer;
//        this.score = score;
//        if(Integer.parseInt(u_answer) == Integer.parseInt(r_answer)){
//            this.final_result = "O";
//        } else {
//            this.final_result = "X";
//        }
//    }

//    public UserSet(String arranged_num,String number, String user_answer, String real_answer) {
//        this.arranged_num =arranged_num;
//        this.prob_num = number;
//        this.u_answer = user_answer;
//        this.r_answer = real_answer;
//        if(Integer.parseInt(u_answer) == Integer.parseInt(r_answer)){
//            this.final_result = "O";
//        } else {
//            this.final_result = "X";
//        }
//    }
    public UserSet(String arranged_num, String number, String user_answer, String real_answer, String prob_set, String score) {
        this.arranged_num =arranged_num;
        this.prob_num = number;
        this.u_answer = user_answer;
        this.r_answer = real_answer;
        this.prob_set = prob_set;
        if(Integer.parseInt(u_answer) == Integer.parseInt(r_answer)){
            this.final_result = "O";
        } else {
            this.final_result = "X";
        }
        this.score =score;
    }


    // UserSet 만들 때 쓰는 것

    public String getProb_set() {
        return prob_set;
    }

    public void setProb_set(String prob_set) {
        this.prob_set = prob_set;
    }

    public String getArranged_num() {
        return arranged_num;
    }

    public void setArranged_num(String arranged_num) {
        this.arranged_num = arranged_num;
    }

    public void setProb_num(String prob_num) {
        this.prob_num = prob_num;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getProb_num() {
        return prob_num;
    }

    public void setNumber(String number) {
        this.prob_num = number;
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

    public String getR_answer() {
        return r_answer;
    }

    public void setR_answer(String r_answer) {
        this.r_answer = r_answer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setFinal_result(String final_result) {this.final_result = final_result;}

    public int getTotalScore(){
        return totalScore;
    }

    @Override
    public int compareTo(UserSet other) {
        // TODO Auto-generated method stub
        //int target = Integer.parseInt(other.prob_num);

        if(arranged_num == other.arranged_num) return 0;
        else if(Integer.parseInt(arranged_num) > Integer.parseInt(other.arranged_num)) return 1;
        else return -1;
    }


//    public int getResId() {
//        return resId;
//    }
//
//    public void setResId(int resId) {
//        this.resId = resId;
//    }
}

