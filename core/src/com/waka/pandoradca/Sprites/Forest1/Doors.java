package com.waka.pandoradca.Sprites.Forest1;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Sprites.InteractiveTileObject;

public class Doors extends InteractiveTileObject {
    private Pandoradca game;

    public Doors(World world, TiledMap map, Rectangle rectangle, Pandoradca game) {
        super(world, map, rectangle);
        this.game = game;
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        game.setScreen(new PlayScreen(game, 2));
    }
}
