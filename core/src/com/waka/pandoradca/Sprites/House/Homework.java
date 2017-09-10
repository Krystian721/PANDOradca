package com.waka.pandoradca.Sprites.House;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Scenes.Hud;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Sprites.InteractiveTileObject;
import com.waka.pandoradca.Tools.Results;

public class Homework extends InteractiveTileObject {

    public Homework(World world, TiledMap map, Rectangle rectangle) {
        super(world, map, rectangle);
        fixture.setUserData(this);
        setCategoryFilter(Pandoradca.HOMEWORK_BIT);
        Results.setHouseQuestions(1, "Odrabianie zadania domowego");
    }

    @Override
    public void onHit() {
        int count = 0;
        for (int i=0; i<5; i++) {
            if (Results.getHouseAnswers()[i] != null) {
                count++;
            }
        }
        setCategoryFilter(Pandoradca.DESTROYED_BIT);
        if (Hud.getQuestion() < 5) {
            Results.setHouseAnswers(Hud.getQuestion(), "Odrabianie zadania domowego");
            Hud.setQuestion(Hud.getQuestion() + 1);
            Hud.updateQuestionCounter();
        }
        if (count < 5)
            map.getLayers().remove(21 - count);
    }
}
