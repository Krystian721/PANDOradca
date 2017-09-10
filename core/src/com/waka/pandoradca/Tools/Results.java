package com.waka.pandoradca.Tools;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Results {
    private static Integer forest1Score, forest2Score, levelNumber = 4;
    private static String [] houseAnswers = new String[5], houseQuestions = new String[8], cityAnswers = new String[5], cityQuestions = new String[5];
    public static String teachersEmail = "", studentName = "";
    public static int houseTime = 0, forestTime = 0, lifeLost = 1;

    //Get
    public static Integer getForest1Score(){
        return forest1Score;
    }
    public static Integer getForest2Score() {
        return forest2Score;
    }
    public static Integer getLevelNumber() { return levelNumber; }
    public static String[] getHouseAnswers(){
        return houseAnswers;
    }
    public static String[] getCityAnswers() {
        return cityAnswers;
    }
    public static String[] getCityQuestions() {
        return cityQuestions;
    }
    public static Integer getHouseTime() { return houseTime; }
    public static Integer getForestTime() { return forestTime; }
    public static Integer getLifeLost() { return lifeLost; }

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
    public static void setHouseQuestions(Integer i, String houseQuestions){
        Results.houseQuestions[i] = houseQuestions;
    }
    public static void setLevelNumber(Integer levelNumber){
        Results.levelNumber = levelNumber;
    }
    public static void setCityQuestions(Integer i, String cityQuestions){
        Results.cityQuestions[i] = cityQuestions;
    }
    public static void setTeachersEmail(String teachersEmail) {
        Results.teachersEmail = teachersEmail;
    }
    public static void setStudentName(String studentName){
        Results.studentName = studentName;
    }
    public static void setHouseTime(Integer time){
        Results.houseTime = time;
    }
    public static void setForestTime(Integer time){
        Results.forestTime = time;
    }
    public static void setLifeLost(Integer fails){
        Results.lifeLost = fails;
    }

    //Constructor
    public Results(){
        forest1Score = 2000;
        houseAnswers = new String[5];
        forest2Score = 5000;
        cityAnswers = new String[5];
    }

    public static void sendResults(){
        final String username = "pandoradca@gmail.com";
        final String password = "P@nd@p@ssw0rd";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        String houseQ = "", houseA = "", cityQ = "", cityA = "";
        for (int i = 0; i < houseAnswers.length; i++)
        {
            houseA += houseAnswers[i] + " ,";
        }
        for (int i = 0; i < houseQuestions.length; i++)
        {
            houseQ += houseQuestions[i] + " ,";
        }
        for (int i = 0; i < cityQuestions.length; i++)
        {
            cityQ += cityQuestions[i] + " ,";
        }
        for (int i = 0; i < cityAnswers.length; i++)
        {
            cityA += cityAnswers[i] + " ,";
        }
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(teachersEmail));
            message.setSubject("PANDOradca - wyniki");
            message.setText("Imię i nazwisko dziecka: " + studentName + "\n\n Wyniki: " + "\n\n" +
                            "Etap - PANDA W DOMU\n" +
                            "czas: "+ houseTime + "\n" +
                            "czynności do wyboru: " + houseQ + "\n" +
                            "czynności wybrane przez ucznia: " + houseA +
                            "\n\n" +
                            "Etap - PANDA W PRACY\n" +
                            "zawody do odgadnięcia: " + cityQ + "\n" +
                            "Odpowiedzi: " + cityA +
                            "\n\n"+
                            "Łącznie wszystkie ćwiczenia (walka z przeszkodami w lesie)\n" +
                            "czas: \n" + forestTime +
                            "ilość zużytych szans: " + lifeLost);

            Transport.send(message);

        System.out.println("Done");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
