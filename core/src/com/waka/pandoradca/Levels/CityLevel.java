package com.waka.pandoradca.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Scenes.Hud;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.TXT.Resources;
import com.waka.pandoradca.Tools.DialogBox;
import com.waka.pandoradca.Tools.FontFactory;
import com.waka.pandoradca.Tools.Results;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class CityLevel {
    private String levelName, answerText, text;
    private DialogBox answerDialog;
    private boolean pressed, hint = false, answer;
    private Texture questionBG;
    private static final int maxQuestions = 5, NOQUESTION = 0, QUESTION = 1;
    private Integer state, showQ = 0, questionNumber = 0, questionTimer;
    private SpriteBatch spriteBatch;
    private Locale plLocale;
    private PlayScreen screen;

    public String getLevelName() {
        return levelName;
    }

    public CityLevel(PlayScreen screen){
        this.screen = screen;
        state = NOQUESTION;
        plLocale = new Locale("pl", "PL");
        levelName = "maps/City/city.tmx";
        FontFactory.getInstance().initialize();
    }

    public boolean handleQuestionInput() {
        if (!pressed) {
            if (Gdx.input.justTouched()) {
                if (!hint) {
                    answerDialog = new DialogBox("", screen.getGame().batch);
                    answerDialog.addLabel("Jaki to zawód?");
                    answerDialog.addTextBoxAndConfirmButton("Tu wpisz odpowiedź!", "OK");
                    answerDialog.addHintButton("PODPOWIEDŹ");
                    answerDialog.addCancelButton("ANULUJ");
                    answerDialog.dialogShow();
                    pressed = true;
                }
                else
                {
                    answerDialog = new DialogBox("", screen.getGame().batch);
                    answerDialog.addLabel("Jaki to zawód?");
                    answerDialog.addTextBoxAndConfirmButton("Tu wpisz odpowiedź!", "OK");
                    answerDialog.addHintButton("PYTANIE");
                    answerDialog.addCancelButton("ANULUJ");
                    answerDialog.dialogShow();
                    pressed = true;
                }
            }
        } else if (answerDialog.cancelled)
            pressed = false;
        else if (answerDialog.hint) {
            if (!hint) {
                pressed = false;
                hint = true;
                questionBG.dispose();
                questionBG = new Texture("questions/module2/hint" + questionNumber + ".jpg");
            }
            else
            {
                hint = false;
                questionBG.dispose();
                questionBG = new Texture("questions/module2/questionBG.png");
                pressed = false;
            }
        } else if (answerDialog.noAnswer)
            pressed = false;
        if (pressed) {
            if (answerDialog.checkAnswer) {
                if ((!answerDialog.string.isEmpty())) {
                    Results.setCityAnswers(questionNumber - 1, answerText);
                    pressed = false;
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public void render(){
        if (state == NOQUESTION) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            screen.getGame().batch.setProjectionMatrix(Hud.getStage().getCamera().combined);
            screen.getBox2DDebugRenderer().render(screen.getWorld(), screen.getGameCamera().combined);
            screen.getRenderer().render();
            screen.getGame().batch.setProjectionMatrix(screen.getGameCamera().combined);
            screen.getGame().batch.begin();
            screen.getPlayer().draw(screen.getGame().batch);
            screen.getGame().batch.end();
            Hud.getStage().draw();
        }
        if (state == QUESTION)
        {
            if (pressed) {
                screen.getGame().batch.setProjectionMatrix(answerDialog.stage.getCamera().projection);
                answerDialog.stage.draw();
            }
        }
    }

    public void update(float deltaTime){
        switch (state) {
            case NOQUESTION:
                showQ = 0;
                answerText = null;
                screen.handleInput();
                screen.boundary(screen.getGameCamera(), screen, 40);
                screen.getWorld().step(1 / 60f, 6, 2);
                Hud.update(deltaTime);
                screen.getPlayer().update(deltaTime);
                screen.getGameCamera().update();
                screen.getRenderer().setView(screen.getGameCamera());
                if (((questionTimer = Hud.getTime()) > 5) && questionNumber < maxQuestions)
                    state = QUESTION;
                if (questionNumber == maxQuestions){
                    spriteBatch.begin();
                    spriteBatch.draw(new Texture("questions/module2/end.png"), 0, 0, 600, 480);
                    spriteBatch.end();
                }
                break;
            case QUESTION:
                if (showQ == 0) {
                    showQ = 1;
                    answer = false;
                    questionBG = new Texture("questions/module2/questionBG.png");
                    spriteBatch = new SpriteBatch();
                    questionNumber++;
                    String randomQuestion = "";
                    Gdx.app.log("", questionNumber.toString());
                    Results.setCityQuestions(questionNumber - 1, randomQuestion);
                    text = Resources.Job(questionNumber);
                }
                answer = handleQuestionInput();
                if (!pressed) {
                    spriteBatch.begin();
                    spriteBatch.draw(questionBG, 0, 0, 600, 480);
                    if (!hint) {
                        FontFactory.getInstance().getFont(plLocale).setColor(Color.BLACK);
                        FontFactory.getInstance().getFont(plLocale).draw(spriteBatch, text, 40, 440.f);
                    }
                    spriteBatch.end();
                }
                if (answer) {
                    Hud.resetTimer();
                    questionBG.dispose();
                    state = NOQUESTION;
                }
                break;
            default: break;
        }
    }
}
