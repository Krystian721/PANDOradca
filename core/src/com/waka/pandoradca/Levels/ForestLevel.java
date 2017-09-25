package com.waka.pandoradca.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waka.pandoradca.Scenes.Hud;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.TXT.Resources;
import com.waka.pandoradca.Tools.FontFactory;
import com.waka.pandoradca.Tools.Results;

import java.util.Locale;

public class ForestLevel {
    private String levelName;
    private PlayScreen screen;
    private boolean showInstruction = false, alreadyShowed = false;
    private Texture instructionBG;
    private SpriteBatch spriteBatch;
    private String text;
    private Locale plLocale;

    public String getLevelName() {
        return levelName;
    }

    public ForestLevel(PlayScreen screen){
        this.screen = screen;
        if (Results.getLevelNumber() == 1) {
            levelName = "maps/Forest1/ForestMap1.tmx";
            plLocale = new Locale("pl", "PL");
            spriteBatch = new SpriteBatch();
        }
        if (Results.getLevelNumber() == 3)
            levelName = "maps/Forest2/ForestMap2.tmx";
    }

    public void update(float deltaTime){
        if ((!showInstruction) && Results.getLevelNumber() == 1){
            showInstruction();
            alreadyShowed = true;
            showInstruction = handleInstructionInput();
        }
        else {
            screen.handleInput();
            if (Results.getLevelNumber() == 1)
                PlayScreen.boundary(screen.getGameCamera(), screen, 40);
            else
                PlayScreen.boundary(screen.getGameCamera(), screen, 36);
            screen.getWorld().step(1 / 60f, 6, 2);
            Hud.update(deltaTime);
            screen.getPlayer().update(deltaTime);
            screen.getGameCamera().update();
            screen.getRenderer().setView(screen.getGameCamera());
            if (Results.getLevelNumber() == 1) {
                Hud.updateScore(Results.getForest1Score());
            } else {
                Hud.updateScore(Results.getForest2Score());
            }
        }
    }

    public void render(){
        if ((!showInstruction) && Results.getLevelNumber() == 1)
        {
        }
        else {
            screen.getBox2DDebugRenderer().render(screen.getWorld(), screen.getGameCamera().combined);
            screen.getRenderer().render();
            screen.getGame().batch.setProjectionMatrix(Hud.getStage().getCamera().combined);
            screen.getGame().batch.setProjectionMatrix(screen.getGameCamera().combined);
            screen.getGame().batch.begin();
            screen.getPlayer().draw(screen.getGame().batch);
            screen.getGame().batch.end();
            Hud.getStage().draw();
        }
    }

    public void showInstruction() {
        if (!alreadyShowed) {
            FontFactory.getInstance().initialize();
            instructionBG = new Texture(Gdx.files.internal("questions/module1/bg.png"));
            spriteBatch = new SpriteBatch();
            text = Resources.ForestInstruction();
        }
        spriteBatch.begin();
        spriteBatch.draw(instructionBG, 0, 0, screen.getGamePort().getScreenWidth(), screen.getGamePort().getScreenHeight());
        FontFactory.getInstance().getFont(plLocale).setColor(Color.BLACK);
        FontFactory.getInstance().getFont(plLocale).draw(spriteBatch, text, 100, 380.f);
        spriteBatch.end();
    }

    public boolean handleInstructionInput() {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            return true;
        }
        else return false;
    }
}
