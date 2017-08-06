package com.waka.pandoradca.Tools;

public class Results {
    private static Integer forest1Score, forest2Score, levelNumber = 1;
    private static String [] houseAnswers = new String[5], cityAnswers = new String[5], cityQuestions = new String[5];

    //Get
    public static Integer getForest1Score(){
        return forest1Score;
    }
    public static Integer getForest2Score() {
        return forest2Score;
    }
    public static Integer getLevelNumber() { return levelNumber; }
    public static String [] getHouseAnswers(){
        return houseAnswers;
    }
    public static String[] getCityAnswers() {
        return cityAnswers;
    }
    public static String[] getCityQuestions() {
        return cityQuestions;
    }

    //Set
    public static void setForest1Score(Integer forest1Score){
        Results.forest1Score = forest1Score;
    }
    public static void setForest2Score(Integer forest2Score) {
        Results.forest2Score = forest2Score;
    }
    public static void setCityAnswers(Integer i, String cityAnswers) {Results.cityAnswers[i] = cityAnswers;
    }
    public static void setHouseAnswers(Integer i, String houseAnswers){
        Results.houseAnswers[i] = houseAnswers;
    }
    public static void setLevelNumber(Integer levelNumber){
        Results.levelNumber = levelNumber;
    }
    public static void setCityQuestions(Integer i, String cityQuestions){
        Results.cityQuestions[i] = cityQuestions;
    }

    //Constructor
    public Results(){
        forest1Score = 2000;
        houseAnswers = new String[5];
        forest2Score = 5000;
        cityAnswers = new String[5];
    }
}
