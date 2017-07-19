package com.waka.pandoradca.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Screens.PlayScreen;
import com.waka.pandoradca.Sprites.Doors;
import com.waka.pandoradca.Sprites.Food;
import com.waka.pandoradca.Sprites.House.Garbage;
import com.waka.pandoradca.Sprites.House.Homework;
import com.waka.pandoradca.Sprites.House.PC;
import com.waka.pandoradca.Sprites.House.Reading;
import com.waka.pandoradca.Sprites.House.Sweeping;
import com.waka.pandoradca.Sprites.House.TV;
import com.waka.pandoradca.Sprites.House.Tooths;
import com.waka.pandoradca.Sprites.House.Washing;
import com.waka.pandoradca.Sprites.Spikes;

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map, Integer level, Pandoradca game, PlayScreen screen) {
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        switch (level){
            case 1:
                //region Ground
                for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Pandoradca.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Pandoradca.PPM);
                    body = world.createBody(bodyDef);
                    polygonShape.setAsBox(rectangle.getWidth() / 2 / Pandoradca.PPM, rectangle.getHeight() / 2 / Pandoradca.PPM);
                    fixtureDef.shape = polygonShape;
                    body.createFixture(fixtureDef);
                }
                //endregion

                //region Tree
                for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Pandoradca.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Pandoradca.PPM);
                    body = world.createBody(bodyDef);
                    polygonShape.setAsBox(rectangle.getWidth() / 2 / Pandoradca.PPM, rectangle.getHeight() / 2 / Pandoradca.PPM);
                    fixtureDef.shape = polygonShape;
                    body.createFixture(fixtureDef);
                }
                //endregion

                //region Spike
                for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new Spikes(world, map, rectangle);
                }
                //endregion

                //region Door
                for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new Doors(world, map, rectangle, game, screen, 1);
                }
                //endregion

                break;
            case 2:
                //region Ground
                for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Pandoradca.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Pandoradca.PPM);
                    body = world.createBody(bodyDef);
                    polygonShape.setAsBox(rectangle.getWidth() / 2 / Pandoradca.PPM, rectangle.getHeight() / 2 / Pandoradca.PPM);
                    fixtureDef.shape = polygonShape;
                    body.createFixture(fixtureDef);
                }
                //endregion

                //region Door
                for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new Doors(world, map, rectangle, game, screen, 2);
                }
                //endregion

                //region Tooths
                for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new Tooths(world, map, rectangle, game, screen);
                }
                //endregion

                //region Washing
                for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new Washing(world, map, rectangle, game, screen);
                }
                //endregion

                //region Garbage
                for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new Garbage(world, map, rectangle, game, screen);
                }
                //endregion

                //region TV
                for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new TV(world, map, rectangle, game, screen);
                }
                //endregion

                //region PC
                for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new PC(world, map, rectangle, game, screen);
                }
                //endregion

                //region Homework
                for (MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new Homework(world, map, rectangle, game, screen);
                }
                //endregion

                //region Reading
                for (MapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new Reading(world, map, rectangle, game, screen);
                }
                //endregion

                //region Sweeping
                for (MapObject object : map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    new Sweeping(world, map, rectangle, game, screen);
                }
                //endregion
                break;
            case 3:
                //region Ground
                for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Pandoradca.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Pandoradca.PPM);
                    body = world.createBody(bodyDef);
                    polygonShape.setAsBox(rectangle.getWidth() / 2 / Pandoradca.PPM, rectangle.getHeight() / 2 / Pandoradca.PPM);
                    fixtureDef.shape = polygonShape;
                    body.createFixture(fixtureDef);
                }
                //endregion


                break;
            case 4:
                //region Ground
                for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Pandoradca.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Pandoradca.PPM);
                    body = world.createBody(bodyDef);
                    polygonShape.setAsBox(rectangle.getWidth() / 2 / Pandoradca.PPM, rectangle.getHeight() / 2 / Pandoradca.PPM);
                    fixtureDef.shape = polygonShape;
                    body.createFixture(fixtureDef);
                }
                //endregion

                break;
        }
        //region Food
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Food(world, map, rectangle);
        }
        //endregion
    }
}
