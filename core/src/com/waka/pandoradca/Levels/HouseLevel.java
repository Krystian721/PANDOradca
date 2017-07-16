package com.waka.pandoradca.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Tools.FontFactory;
import com.waka.pandoradca.Tools.Results;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class HouseLevel {
    private String levelName;
    private PlayScreen screen;
    private SpriteBatch spriteBatch;
    private boolean showInstruction = true, alreadyShowed = false;
    private Texture instructionBG;
    private StringBuilder stringBuilder;
    private String string;
    private String text;
    private Locale plLocale;


    public String getLevelName() {
        return levelName;
    }

    public HouseLevel(PlayScreen screen){
        this.screen = screen;
        plLocale = new Locale("pl", "PL");
        stringBuilder = new StringBuilder();
        levelName = "maps/House/HouseMap.tmx";
        FontFactory.getInstance().initialize();
    }

    public void update(float deltaTime){
        if (showInstruction){
            showInstruction();
            alreadyShowed = true;
            handleInput();
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
        if (showInstruction){

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
        FontFactory.getInstance().getFont(plLocale).draw(spriteBatch, text, 60, 300.f);
        spriteBatch.end();
    }

    public void handleInput(){
        if (Gdx.input.justTouched()){
            showInstruction = false;
        }
    }
}
