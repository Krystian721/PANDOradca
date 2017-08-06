package com.waka.pandoradca.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Tools.Results;

public class Hud implements Disposable{
    private static Integer question, worldTimer, questionsPerLevel;
    private static float timeCount;
    private static Label questionLabel, scoreLabel;
    private static Stage stage;
    private Integer score;

    public static Stage getStage(){
        return stage;
    }

    public static int getTime(){
        return worldTimer;
    }

    public static void resetTimer(){
        worldTimer = 0;
    }

    public static Integer getQuestion(){
        return question;
    }

    public static void setQuestion(Integer question){
        Hud.question = question;
    }

    //constructor
    public Hud(SpriteBatch sb, int questionsPerLevel){
        question = 0;
        worldTimer = 0;
        switch (Results.getLevelNumber()){
            case 1:
                score = Results.getForest1Score();
                break;
            case 3:
                score = Results.getForest2Score();
                break;
        }
        this.questionsPerLevel = questionsPerLevel;

        Viewport viewport = new FillViewport(Pandoradca.V_WIDTH, Pandoradca.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        questionLabel = new Label(question.toString() + "/" + this.questionsPerLevel.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        if (questionsPerLevel > 0)
            table.add(Hud.questionLabel).expandX().padTop(10);
        if (questionsPerLevel == 0)
            table.add(scoreLabel).expandX().padTop(50);

        stage.addActor(table);
    }

    public static void updateQuestionCounter(){
        questionLabel.setText(question.toString() + "/" + questionsPerLevel.toString());
    }

    public static void updateScore(int value){
        if (Results.getLevelNumber() == 1) {
            scoreLabel.setText(Results.getForest1Score().toString());
        }
        else {
            scoreLabel.setText(Results.getForest2Score().toString());
        }
    }

    public static void update(float dt){
        timeCount += dt;
        if (timeCount >= 1){
            worldTimer++;
            timeCount = 0;
            if (Results.getLevelNumber() == 1) {
                Results.setForest1Score(Results.getForest1Score() - 1);
            }
            else if (Results.getLevelNumber() == 3) {
                Results.setForest2Score(Results.getForest2Score() - 1);
            }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
