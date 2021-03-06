package com.waka.pandoradca.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Food extends InteractiveTileObject{
    public Food(World world, TiledMap map, Rectangle rectangle) {
        super(world, map, rectangle);
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        Gdx.app.log("Food", "Collision");
    }
}
