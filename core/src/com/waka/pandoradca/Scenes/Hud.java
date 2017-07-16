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

public class Hud implements Disposable{
    private Stage stage;
    private Integer score;
    private Integer question;
    private Integer questionsPerLevel;
    private Integer worldTimer;
    private float timeCount;
    private Label questionsLabel;

    public Integer getQuestion(){
        return question;
    }

    public Stage getStage(){
        return stage;
    }

    public int getTime(){
        return worldTimer;
    }

    public void resetTimer(){
        worldTimer = 0;
    }

    public void setQuestion(Integer question){
        this.question = question;
    }

    public Hud(SpriteBatch sb, int questionsPerLevel){
        score = 0;
        question = 0;
        worldTimer = 0;
        this.questionsPerLevel = questionsPerLevel;

        Viewport viewport = new FillViewport(Pandoradca.V_WIDTH, Pandoradca.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        questionsLabel = new Label(question.toString() + "/" + this.questionsPerLevel.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        if (questionsPerLevel > 0)
            table.add(questionsLabel).expandX().padTop(10);
        if (questionsPerLevel == 0)
            table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void update(float dt){
        timeCount += dt;
        if (timeCount >= 1){
            worldTimer++;
            timeCount = 0;
        }
    }

    public void updateQuestionCounter(){
        questionsLabel.setText(question.toString() + "/" + this.questionsPerLevel.toString());
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
