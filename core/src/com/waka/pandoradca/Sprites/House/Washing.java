package com.waka.pandoradca.Sprites.House;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Sprites.InteractiveTileObject;

public class Washing extends InteractiveTileObject {
    private Pandoradca game;
    private PlayScreen screen;

    public Washing(World world, TiledMap map, Rectangle rectangle, Pandoradca game, PlayScreen screen) {
        super(world, map, rectangle);
        this.game = game;
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(Pandoradca.WASHING_BIT);
    }

    @Override
    public void onHit() {
        setCategoryFilter(Pandoradca.DESTROYED_BIT);
        if (screen.getHud().getQuestion() < 5) {
            screen.getResults().getHouseAnswers()[screen.getHud().getQuestion()] = "Wkładanie brudnych rzeczy do prania";
            screen.getHud().setQuestion(screen.getHud().getQuestion() + 1);
            screen.getHud().updateQuestionCounter();
        }
    }
}
