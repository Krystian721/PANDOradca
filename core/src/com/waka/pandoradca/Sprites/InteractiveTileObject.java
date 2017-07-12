package com.waka.pandoradca.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.waka.pandoradca.Pandoradca;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle rectangle;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(World world, TiledMap map, Rectangle rectangle) {
        this.world = world;
        this.map = map;
        this.rectangle = rectangle;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Pandoradca.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Pandoradca.PPM);

        body = world.createBody(bodyDef);

        polygonShape.setAsBox(rectangle.getWidth() / 2 / Pandoradca.PPM, rectangle.getHeight() / 2 / Pandoradca.PPM);
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);
        fixture = body.createFixture(fixtureDef);
    }

    public abstract void onHit();
}
