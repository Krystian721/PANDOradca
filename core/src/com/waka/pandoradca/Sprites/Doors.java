package com.waka.pandoradca.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Tools.FontFactory;
import com.waka.pandoradca.Tools.Results;

import java.util.Locale;

public class Doors extends InteractiveTileObject {
    private Pandoradca game;

    public Doors(World world, TiledMap map, Rectangle rectangle, Pandoradca game) {
        super(world, map, rectangle);
        this.game = game;
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        if (Results.getLevelNumber() == 1) {
            Results.setLevelNumber(2);
            game.setScreen(new PlayScreen(game));
        }
        else if ((Results.getLevelNumber() == 2)&&(!((Results.getHouseAnswers()[4]) == null))){
            Results.setLevelNumber(3);
            game.setScreen(new PlayScreen(game));
        }
        else if (Results.getLevelNumber() == 3){
            Results.setLevelNumber(4);
            game.setScreen(new PlayScreen(game));
        }
    }
}
