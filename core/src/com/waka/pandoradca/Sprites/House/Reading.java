package com.waka.pandoradca.Sprites.House;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Sprites.InteractiveTileObject;

public class Reading extends InteractiveTileObject {
    private Pandoradca game;
    private PlayScreen screen;

    public Reading(World world, TiledMap map, Rectangle rectangle, Pandoradca game, PlayScreen screen) {
        super(world, map, rectangle);
        this.game = game;
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(Pandoradca.READING_BIT);
    }

    @Override
    public void onHit() {
        int count = 0;
        for (int i=0; i<5; i++) {
            if (screen.getResults().getHouseAnswers()[i] != null) {
                count++;
            }
        }
        setCategoryFilter(Pandoradca.DESTROYED_BIT);
        if (screen.getHud().getQuestion() < 5) {
            screen.getResults().getHouseAnswers()[screen.getHud().getQuestion()] = "Czytanie książek";
            screen.getHud().setQuestion(screen.getHud().getQuestion() + 1);
            screen.getHud().updateQuestionCounter();
        }
        if (count < 5)
            map.getLayers().remove(22 - count);
    }
}
