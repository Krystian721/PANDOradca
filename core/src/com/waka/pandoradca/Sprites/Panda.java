package com.waka.pandoradca.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Screens.PlayScreen;

import static com.waka.pandoradca.Sprites.Panda.State.JUMPING;
import static com.waka.pandoradca.Sprites.Panda.State.RUNNING;

public class Panda extends Sprite {
    public enum State { STANDING, RUNNING, JUMPING };
    public State currentState;
    public State prevoiusState;
    public World world;
    public Body b2body;
    private TextureRegion pandaStand;
    private Animation<TextureRegion> pandaRun;
    private Animation<TextureRegion> pandaJump;
    private float stateTimer;
    //private boolean runningRight;

    public Panda(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("panda"));
        this.world = world;
        currentState = State.STANDING;
        prevoiusState = State.STANDING;
        stateTimer = 0;
        //runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<3; i++)
            frames.add(new TextureRegion(getTexture(), 671 + (i * 40 + i * 12), 125, 40, 37 ));
        pandaRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i=0; i<1; i++)
            frames.add(new TextureRegion(getTexture(), 622 + i * 40, 126, 40, 37));
        pandaJump = new Animation<TextureRegion>(0.1f, frames);

        pandaStand = new TextureRegion(getTexture(), 770, 13, 40, 37);

        definePanda();
        setBounds(20, 0, 40 / Pandoradca.PPM, 38 / Pandoradca.PPM);
        setRegion(pandaStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = pandaJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = pandaRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = pandaStand;
                break;
        }
/*
        if (b2body.getLinearVelocity().x < 0 || !runningRight && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if (b2body.getLinearVelocity().x > 0 || runningRight && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }*/

        stateTimer = currentState == prevoiusState ? stateTimer + delta : 0;
        prevoiusState = currentState;
        return region;
    }

    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && prevoiusState == JUMPING))
            return JUMPING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void definePanda() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / Pandoradca.PPM, 32 / Pandoradca.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6 / Pandoradca.PPM);

        fixtureDef.shape = circleShape;
        b2body.createFixture(fixtureDef);

        EdgeShape legs = new EdgeShape();
        legs.set(new Vector2(-10 / Pandoradca.PPM, -17 / Pandoradca.PPM), new Vector2(-5 / Pandoradca.PPM, -17 / Pandoradca.PPM));
        fixtureDef.shape = legs;
        fixtureDef.isSensor = true;
        b2body.createFixture(fixtureDef).setUserData("legs");

        EdgeShape front = new EdgeShape();
        front.set(new Vector2(10 / Pandoradca.PPM, 5 / Pandoradca.PPM), new Vector2(10 / Pandoradca.PPM, -5 / Pandoradca.PPM));
        fixtureDef.shape = front;
        fixtureDef.isSensor = true;
        b2body.createFixture(fixtureDef).setUserData("front");

        EdgeShape back = new EdgeShape();
        back.set(new Vector2(-15 / Pandoradca.PPM, 0 / Pandoradca.PPM), new Vector2(-15 / Pandoradca.PPM, -11 / Pandoradca.PPM));
        fixtureDef.shape = back;
        fixtureDef.isSensor = true;
        b2body.createFixture(fixtureDef).setUserData("back");
    }
}
