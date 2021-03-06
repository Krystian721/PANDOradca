package com.waka.pandoradca.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Tools.Results;

public class Spikes extends InteractiveTileObject {
    public static boolean hit = false;
    public Spikes(World world, TiledMap map, Rectangle rectangle) {
        super(world, map, rectangle);
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        if (Results.getLevelNumber() == 1) {
            Results.setForest1Score(Results.getForest1Score() - 20);
            hit = true;
        }
        else {
            Results.setForest2Score(Results.getForest2Score() - 20);
            hit = true;
        }
    }
}
