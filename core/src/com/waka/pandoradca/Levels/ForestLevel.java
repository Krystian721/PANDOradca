package com.waka.pandoradca.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.waka.pandoradca.Scenes.Hud;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Tools.Results;

public class ForestLevel {
    private String levelName;
    private PlayScreen screen;

    public String getLevelName() {
        return levelName;
    }

    public ForestLevel(PlayScreen screen){
        this.screen = screen;
        if (Results.getLevelNumber() == 1)
            levelName = "maps/Forest1/ForestMap1.tmx";
        if (Results.getLevelNumber() == 3)
            levelName = "maps/Forest2/ForestMap2.tmx";
    }

    public void update(float deltaTime){
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
        }
        else
        {
            Hud.updateScore(Results.getForest2Score());
        }
    }

    public void render(){
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
