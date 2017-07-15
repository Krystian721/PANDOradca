package com.waka.pandoradca.Sprites.Forest1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Sprites.InteractiveTileObject;

public class Spikes extends InteractiveTileObject {
    public Spikes(World world, TiledMap map, Rectangle rectangle) {
        super(world, map, rectangle);
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        Gdx.app.log("Spikes", "Collision");
    }
}
