package com.waka.pandoradca.Tools;

public class Results {
    private Integer forest1Score;
    private String [] houseAnswers;
    private Integer forest2Score;
    private String [] cityAnswers;

    public Integer getForest1Score(){
        return forest1Score;
    }

    public String [] getHouseAnswers(){
        return houseAnswers;
    }

    public Integer getForest2Score() {
        return forest2Score;
    }

    public String[] getCityAnswers() {
        return cityAnswers;
    }

    public void setForest2Score(Integer forest2Score) {
        this.forest2Score = forest2Score;
    }

    public void setCityAnswers(String[] cityAnswers) {
        this.cityAnswers = cityAnswers;
    }

    public void setForest1Score(Integer forest1Score){
        this.forest1Score = forest1Score;
    }

    public void setHouseAnswers(String [] houseAnswers){
        this.houseAnswers = houseAnswers;
    }

    //Constructor
    public Results(){
        forest1Score = 0;
        houseAnswers = new String[5];
        forest2Score = 0;
        cityAnswers = new String[5];
    }
}
