package com.waka.pandoradca.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import java.util.Random;

public class CityLevel {
    private String levelName, answerText, text;
    private DialogBox answerDialog;
    private boolean pressed, hint = false, answer, emailSend = false;
    private Texture questionBG, endTexture;
    private static final int maxQuestions = 6, NOQUESTION = 0, QUESTION = 1;
    private Integer state, showQ = 0, questionNumber = 0, questionTimer = 0,randomQuestionNumber = 0;
    private SpriteBatch spriteBatch;
    private Locale plLocale;
    private PlayScreen screen;
    private Integer[] randomQuestions = new Integer[]{0, 0, 0, 0, 0, 0};

    public String getLevelName() {
        return levelName;
    }

    public CityLevel(PlayScreen screen){
        this.screen = screen;
        state = NOQUESTION;
        plLocale = new Locale("pl", "PL");
        levelName = "maps/City/city.tmx";
        FontFactory.getInstance().initialize();
        endTexture = new Texture("questions/module2/end.png");
        Hud.setQuestion(0);
    }

    public boolean handleQuestionInput() {
        if (!pressed) {
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
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
                questionBG = new Texture("questions/module2/hint" + randomQuestionNumber + ".jpg");
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
                    answerText = answerDialog.string;
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
        if (!emailSend) {
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
            if (state == QUESTION) {
                if (pressed) {
                    screen.getGame().batch.setProjectionMatrix(answerDialog.stage.getCamera().projection);
                    answerDialog.stage.draw();
                }
            }
        }
    }

    public void update(float deltaTime){
        if (questionNumber == maxQuestions){
            spriteBatch.begin();
            spriteBatch.draw(endTexture, 0, 0, 600, 480);
            spriteBatch.end();
            if (!emailSend) {
                emailSend = true;
                Results.sendResults();
            }
        }
        else {
            switch (state) {
                case NOQUESTION:
                    showQ = 0;
                    answerText = null;
                    hint = false;
                    screen.handleInput();
                    screen.boundary(screen.getGameCamera(), screen, 32);
                    screen.getWorld().step(1 / 60f, 6, 2);
                    Hud.update(deltaTime);
                    screen.getPlayer().update(deltaTime);
                    screen.getGameCamera().update();
                    screen.getRenderer().setView(screen.getGameCamera());
                    if (((questionTimer = Hud.getTime()) > 3) && questionNumber < maxQuestions)
                        state = QUESTION;
                    break;
                case QUESTION:
                    if (showQ == 0) {
                        showQ = 1;
                        answer = false;
                        questionBG = new Texture("questions/module2/questionBG.png");
                        spriteBatch = new SpriteBatch();
                        questionNumber++;

                        randomQuestionNumber = 0;
                        Random generator = new Random();
                        //0 start, 1 gdy jest w tablicy, 2 gdy mozemy uzyc
                        int result = 0;
                        while (result != 2) {
                            result = 0;
                            randomQuestionNumber = generator.nextInt(11) + 1;
                            for (Integer i : randomQuestions) {
                                if (i == randomQuestionNumber) {
                                    result = 1;
                                }
                            }
                            if (result == 0) {
                                result = 2;
                                randomQuestions[questionNumber - 1] = randomQuestionNumber;
                            }
                        }

                        Results.setCityQuestions(questionNumber - 1, Resources.cityQuestions()[randomQuestionNumber - 1]);
                        text = Resources.Job(randomQuestionNumber);
                    }
                    answer = handleQuestionInput();
                    if (!pressed) {
                        spriteBatch.begin();
                        spriteBatch.draw(questionBG, 0, 0, 600, 480);
                        if (!hint) {
                            FontFactory.getInstance().getFont(plLocale).setColor(Color.BLACK);
                            FontFactory.getInstance().getFont(plLocale).draw(spriteBatch, text, 150, 440.f);
                        }
                        spriteBatch.end();
                    }
                    if (answer) {
                        Hud.setQuestion(Hud.getQuestion() + 1);
                        Hud.updateQuestionCounter();
                        Hud.resetTimer();
                        questionBG.dispose();
                        state = NOQUESTION;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
