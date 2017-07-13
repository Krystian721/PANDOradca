package com.waka.pandoradca.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.waka.pandoradca.Screens.PlayScreen;

public class ForestLevel {
    private String levelName;
    private PlayScreen screen;

    public String getLevelName() {
        return levelName;
    }

    public ForestLevel(PlayScreen screen, int level){
        this.screen = screen;
        if (level == 1)
            levelName = "maps/Forest1/ForestMap1.tmx";
        if (level == 3)
            levelName = "maps/Forest2/ForestMap2.tmx";
    }

    public void update(float deltaTime){
        screen.handleInput();
        screen.boundary(screen.getGameCamera(), screen, 40);
        screen.getWorld().step(1 / 60f, 6, 2);
        screen.getHud().update(deltaTime);
        screen.getPlayer().update(deltaTime);
        screen.getGameCamera().update();
        screen.getRenderer().setView(screen.getGameCamera());
    }

    public void render(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.getGame().batch.setProjectionMatrix(screen.getHud().getStage().getCamera().combined);
        screen.getRenderer().render();
        screen.getBox2DDebugRenderer().render(screen.getWorld(), screen.getGameCamera().combined);
        screen.getGame().batch.setProjectionMatrix(screen.getGameCamera().combined);
        screen.getGame().batch.begin();
        screen.getPlayer().draw(screen.getGame().batch);
        screen.getGame().batch.end();
        screen.getHud().getStage().draw();
    }
}
