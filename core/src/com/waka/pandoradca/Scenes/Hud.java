package com.waka.pandoradca.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.waka.pandoradca.Pandoradca;

import org.omg.CORBA.StringSeqHelper;

public class Hud {
    public Stage stage;
    public Viewport viewport;

    private Integer score;
    private Integer question;
    private Integer questionsPerLevel;


    Label scoreLabel;
    Label questionsLabel;

    public Hud(SpriteBatch sb){
        score = 0;
        question = 0;
        questionsPerLevel = 10;

        viewport = new FitViewport(Pandoradca.V_WIDTH, Pandoradca.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        questionsLabel = new Label(question.toString()+"/"+questionsPerLevel.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(questionsLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }
}
