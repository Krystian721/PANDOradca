package com.waka.pandoradca.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Tools.FontFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class HouseLevel {
    private String levelName;
    private PlayScreen screen;
    private SpriteBatch spriteBatch, spriteBatch2;
    private boolean showInstruction = false, alreadyShowed = false, showResults = false, showedResult = false;
    private Texture instructionBG, resultsBG;
    private StringBuilder stringBuilder;
    private String string;
    private String text;
    private Locale plLocale;
    private String[] goodAnswers, badAnswers;

    public String getLevelName() {
        return levelName;
    }

    public HouseLevel(PlayScreen screen){
        this.screen = screen;
        plLocale = new Locale("pl", "PL");
        spriteBatch = new SpriteBatch();
        stringBuilder = new StringBuilder();
        levelName = "maps/House/HouseMap.tmx";
        goodAnswers = new String[]{"Wyrzucanie śmieci", "Odrabianie zadania domowego", "Czytanie książek", "Zamiatanie", "Mycie zębów", "Wkładanie brudnych rzeczy do prania"};
        badAnswers = new String[]{"Gra na komputerze", "Oglądanie telewizji", "TEMP", "TEMP", "TEMP"};
    }

    public void update(float deltaTime){
        if (!showedResult)
            if (!(screen.getResults().getHouseAnswers()[4] == null)) {
                showResults = true;
                for (int i=0; i<3; i++)
                    screen.getMap().getLayers().remove(16);
            }
        if (!showInstruction){
            showInstruction();
            alreadyShowed = true;
            showInstruction = handleInstructionInput();
        }
        else if (showResults) {
            showResults();
            showedResult = true;
            showResults = handleResultsInput();
        }
        else {
            screen.handleInput();
            screen.boundary(screen.getGameCamera(), screen, 41);
            screen.getWorld().step(1 / 60f, 6, 2);
            screen.getPlayer().update(deltaTime);
            screen.getGameCamera().update();
            screen.getRenderer().setView(screen.getGameCamera());
            screen.getHud().update(deltaTime);
        }
    }

    public void render() {
        if (!showInstruction){

        }
        else if (showResults){

        }
        else {
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
    }

    public void showInstruction() {
        if (!alreadyShowed) {
            FontFactory.getInstance().initialize();
            instructionBG = new Texture(Gdx.files.internal("questions/module1/bg.jpg"));
            spriteBatch = new SpriteBatch();
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("questions/module1/instruction.txt"), "windows-1250"));
                string = "";
                while ((string = in.readLine()) != null) {
                    stringBuilder.append(string + "\r\n");
                }
                text = stringBuilder.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        spriteBatch.begin();
        spriteBatch.draw(instructionBG, 0, 0, screen.getGamePort().getScreenWidth(), screen.getGamePort().getScreenHeight());
        FontFactory.getInstance().getFont(plLocale).setColor(Color.BLACK);
        FontFactory.getInstance().getFont(plLocale).draw(spriteBatch, text, 60, 300.f);
        spriteBatch.end();
    }

    public boolean handleInstructionInput() {
        if (Gdx.input.justTouched()) {
            return true;
        }
        else return false;
    }

    public boolean handleResultsInput(){
        if (Gdx.input.justTouched()){
            return false;
        }
        else return true;
    }

    public void showResults(){
        if (!showedResult){
            spriteBatch2 = new SpriteBatch();
            resultsBG = new Texture(Gdx.files.internal("questions/module1/resultBG.jpg"));
            FontFactory.getInstance().initialize();
        }
        spriteBatch2.begin();
        spriteBatch2.draw(resultsBG, 0, 0, screen.getGamePort().getScreenWidth(), screen.getGamePort().getScreenHeight());
        for (int i=0; i<5; i++) {
            text = screen.getResults().getHouseAnswers()[i];
            for (int j=0; j<5; j++) {
                if (text == goodAnswers[j])
                    FontFactory.getInstance().getFont(plLocale).setColor(Color.GREEN);
                else if (text == badAnswers[j])
                    FontFactory.getInstance().getFont(plLocale).setColor(Color.RED);
            }
            FontFactory.getInstance().getFont(plLocale).draw(spriteBatch2, text, 60, 300.f-i*27);
        }
        spriteBatch2.end();
    }
}
