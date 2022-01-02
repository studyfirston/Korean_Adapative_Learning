package com.example.topikappv4;

import java.io.Serializable;

public class ListeningOptions implements Serializable,Comparable<ListeningOptions> {
    //라디오버튼
    int position; // 프로그래스 바 위치
    String prob_num;
    String url; //주소
    String str_mp3; // 리스닝 파일 이름


    boolean isPlaying ; // 재생중인지 확인할 변수
    boolean flag_restart;
    boolean flag_first;
    boolean after_flag;


    public ListeningOptions(int position, String prob_num, String url, String str_mp3 , boolean isPlaying,
                             boolean flag_restart, boolean flag_first){
        this.position = position;
        this.prob_num = prob_num;
        this.url = url;
        this.str_mp3 = str_mp3;
        this.isPlaying = isPlaying;
        this.flag_restart = flag_restart;
        this.flag_first = flag_first;
        //this.after_flag = true;


    }

//    public boolean getAfter_flag() {
//        return after_flag;
//    }

    public String getProb_num() {
        return prob_num;
    }

    public void setProb_num(String prob_num) {
        this.prob_num = prob_num;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStr_mp3() {
        return str_mp3;
    }

    public void setStr_mp3(String str_mp3) {
        this.str_mp3 = str_mp3;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }
    public void setIsPlaying(boolean playing) {
        this.isPlaying = playing;
    }

    public boolean getFlag_restart() {
        return flag_restart;
    }

    public void setFlag_restart(boolean flag_restart) {
        this.flag_restart = flag_restart;
    }

    public boolean getFlag_first() {
        return flag_first;
    }

    public void setFlag_first(boolean flag_first) {
        this.flag_first = flag_first;
    }

    public ListeningOptions return_lOptions(){
        return this;
    }

    @Override
    public int compareTo(ListeningOptions other) {
        // TODO Auto-generated method stub
        //int target = Integer.parseInt(other.prob_num);

        if(prob_num == other.prob_num) return 0;
        else if(Integer.parseInt(prob_num) > Integer.parseInt(other.prob_num)) return 1;
        else return -1;
    }
}
