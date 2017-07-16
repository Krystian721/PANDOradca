package com.waka.pandoradca.Sprites.House;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Sprites.InteractiveTileObject;

public class Tooths extends InteractiveTileObject {
    private Pandoradca game;
    private PlayScreen screen;

    public Tooths(World world, TiledMap map, Rectangle rectangle, Pandoradca game, PlayScreen screen) {
        super(world, map, rectangle);
        this.game = game;
        this.screen = screen;
        setCategoryFilter(Pandoradca.TOOTH_BIT);
        fixture.setUserData(this);
    }

    @Override
    public void onHit() {
        setCategoryFilter(Pandoradca.DESTROYED_BIT);
        if (screen.getHud().getQuestion() < 5) {
            screen.getResults().getHouseAnswers()[screen.getHud().getQuestion()] = "Mycie zębów";
            screen.getHud().setQuestion(screen.getHud().getQuestion() + 1);
            screen.getHud().updateQuestionCounter();
        }
        //map.getLayers().remove();
    }
}
