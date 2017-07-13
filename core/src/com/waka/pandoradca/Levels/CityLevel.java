package com.waka.pandoradca.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Tools.DialogBox;
import com.waka.pandoradca.Tools.FontFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class CityLevel {
    private String levelName;
    private DialogBox answerDialog;
    private String[] answerTable;
    private boolean pressed;
    private boolean hint = false;
    private Texture questionBG;
    private String answerText;
    private int questionNumber = 0;
    private static final int maxQuestions = 17;
    private static final int NOQUESTION = 0;
    private static final int QUESTION = 1;
    private int state;
    private int showQ = 0;
    private boolean answer;
    private SpriteBatch spriteBatch;
    private StringBuilder stringBuilder;
    private String string;
    private String text;
    private Locale plLocale;
    private PlayScreen screen;
    private int questionTimer;

    public int getQuestionNumber(){
        return questionNumber;
    }

    public String getLevelName() {
        return levelName;
    }

    public CityLevel(PlayScreen screen){
        this.screen = screen;
        state = NOQUESTION;
        plLocale = new Locale("pl", "PL");
        answerTable = new String[maxQuestions];
        stringBuilder = new StringBuilder();
        levelName = "maps/City/CityMap.tmx";
        FontFactory.getInstance().initialize();
    }

    public boolean handleQuestionInput() {
        if (!pressed) {
            if (Gdx.input.justTouched()) {
                answerDialog = new DialogBox("", screen.getGame().batch);
                answerDialog.addLabel("Jaki to zawód?");
                answerDialog.addTextBoxAndConfirmButton("Tu wpisz odpowiedź!", "OK");
                answerDialog.addHintButton("PODPOWIEDŹ");
                answerDialog.addCancelButton("ANULUJ");
                answerDialog.dialogShow();
                pressed = true;
            }
        } else if (answerDialog.cancelled)
            pressed = false;
        else if (answerDialog.hint) {
            if (!hint)
                FontFactory.getInstance().dispose();
            pressed = false;
            hint = true;
            questionBG = new Texture("questions/module1/hint" + questionNumber + ".jpg");
        } else if (answerDialog.noAnswer)
            pressed = false;
        if (pressed) {
            if (answerDialog.checkAnswer) {
                if ((!answerDialog.string.isEmpty())) {
                    answerTable[questionNumber - 1] = answerText;
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
            screen.getGame().batch.setProjectionMatrix(screen.getHud().getStage().getCamera().combined);
            screen.getBox2DDebugRenderer().render(screen.getWorld(), screen.getGameCamera().combined);
            screen.getRenderer().render();
            screen.getGame().batch.setProjectionMatrix(screen.getGameCamera().combined);
            screen.getGame().batch.begin();
            screen.getPlayer().draw(screen.getGame().batch);
            screen.getGame().batch.end();
            screen.getHud().getStage().draw();
        }
        if (state == QUESTION)
        {
            if (pressed) {
                screen.getGame().batch.setProjectionMatrix(answerDialog.stage.getCamera().combined);
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
                screen.getHud().update(deltaTime);
                screen.getPlayer().update(deltaTime);
                screen.getGameCamera().update();
                screen.getRenderer().setView(screen.getGameCamera());
                if (((questionTimer = screen.getHud().getTime()) > 15) && questionNumber < maxQuestions)
                    state = QUESTION;
                break;
            case QUESTION:
                if (showQ == 0) {
                    showQ = 1;
                    answer = false;
                    questionBG = new Texture("questions/module2/questionBG.jpg");
                    spriteBatch = new SpriteBatch();
                    questionNumber++;
                    if (Gdx.files.internal("questions/module2/" + questionNumber + ".txt").exists()) {
                        FileHandle file = Gdx.files.internal("questions/module2/" + questionNumber + ".txt");
                        try {
                            stringBuilder.setLength(0);
                            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("questions/module2/" + questionNumber + ".txt"), "windows-1250"));
                            string = "";
                            while ((string = in.readLine()) != null) {
                                stringBuilder.append(string + "\r\n");
                            }
                            text = stringBuilder.toString();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                spriteBatch.begin();
                spriteBatch.draw(questionBG, 0, 0, screen.getGamePort().getScreenWidth(), screen.getGamePort().getScreenHeight());
                if (!hint)
                    FontFactory.getInstance().getFont(plLocale).draw(spriteBatch, text, 60, 300.f);
                spriteBatch.end();
                answer = handleQuestionInput();
                if (answer) {
                    screen.getHud().resetTimer();
                    questionBG.dispose();
                    state = NOQUESTION;
                }
                break;
            default: break;
        }
    }
}
