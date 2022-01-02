package com.example.newtopikapp;

public class ProblemSet{
    private int answer;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String conversation;
    private String example;
    private String explanation;
    private String hint;
    //이미지는 나중에 새로 추가
    private String large_category;
    private int level;
    private String mediumcategory;
    private int multiple_question;
    private String music;
    private int prob_num;
    private String question;
    private String text;
    private String section;
    private String set_problem;
    private String small_category;
    private String topic;
    private int year;
    private int point;

    public ProblemSet(){}

    public ProblemSet(int answer, String choice1, String choice2, String choice3, String choice4, String conversation, String example,
                      String explanation, String hint, String large_category, int level, String mediumcategory, int multiple_question,
                      String music, int prob_num, String question,String text ,String section, String set_problem, String small_category,
                      String topic, int year, int point) {
        this.answer = answer;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.conversation = conversation;
        this.example = example;
        this.explanation = explanation;
        this.hint = hint;
        this.large_category = large_category;
        this.level = level;
        this.mediumcategory = mediumcategory;
        this.multiple_question = multiple_question;
        this.music = music;
        this.prob_num = prob_num;
        this.question = question;
        this.text = text;
        this.section = section;
        this.set_problem = set_problem;
        this.small_category = small_category;
        this.topic = topic;
        this.year = year;
        this.point = point;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getConversation() {
        return conversation;
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getLarge_category() {
        return large_category;
    }

    public void setLarge_category(String large_category) {
        this.large_category = large_category;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMediumcategory() {
        return mediumcategory;
    }

    public void setMediumcategory(String mediumcategory) {
        this.mediumcategory = mediumcategory;
    }

    public int getMultiple_question() {
        return multiple_question;
    }

    public void setMultiple_question(int multiple_question) {
        this.multiple_question = multiple_question;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public int getProb_num() {
        return prob_num;
    }

    public void setProb_num(int prob_num) {
        this.prob_num = prob_num;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSet_problem() {
        return set_problem;
    }

    public void setSet_problem(String set_problem) {
        this.set_problem = set_problem;
    }

    public String getSmall_category() {
        return small_category;
    }

    public void setSmall_category(String small_category) {
        this.small_category = small_category;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}