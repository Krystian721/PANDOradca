package com.waka.pandoradca.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Screens.PlayScreen;

public class Doors extends InteractiveTileObject {
    private Pandoradca game;
    private PlayScreen screen;
    private Integer level;

    public Doors(World world, TiledMap map, Rectangle rectangle, Pandoradca game, PlayScreen screen, Integer level) {
        super(world, map, rectangle);
        this.game = game;
        this.screen = screen;
        this.level = level;
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        if (level == 1)
            game.setScreen(new PlayScreen(game, 2, screen.getResults()));
        if ((level == 2)&&(!((screen.getResults().getHouseAnswers()[4])==null))){
            game.setScreen(new PlayScreen(game, 3, screen.getResults()));
        }
    }
}
