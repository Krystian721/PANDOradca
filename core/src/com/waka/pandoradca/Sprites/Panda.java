package com.waka.pandoradca.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

public class Panda extends Sprite {
    private enum State { STANDING, RUNNING, JUMPING }
    private State currentState;
    private State previousState;
    private World world;
    public Body b2body;
    private TextureRegion pandaStandRight, pandaStandLeft;
    private Animation<TextureRegion> pandaRunRight, pandaRunLeft;
    private Animation<TextureRegion> pandaJumpRight, pandaJumpLeft;
    private float stateTimer;
    private boolean runningRight;

    public Panda(World world) {
        TextureAtlas atlasRight = new TextureAtlas("animations/PandaRight.pack");
        TextureAtlas atlasLeft = new TextureAtlas("animations/PandaLeft.pack");
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<3; i++)
            frames.add(new TextureRegion(atlasRight.findRegion("pandaRight").getTexture(), 671 + (i * 40 + i * 13), 125, 40, 36 ));
        pandaRunRight = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i=0; i<1; i++)
            frames.add(new TextureRegion(atlasRight.findRegion("pandaRight").getTexture(), 622 + i * 40, 126, 40, 37));
        pandaJumpRight = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        pandaStandRight = new TextureRegion(atlasRight.findRegion("pandaRight").getTexture(), 770, 13, 40, 36);
        frames.clear();

        for (int i=0; i<3; i++)
            frames.add(new TextureRegion(atlasLeft.findRegion("pandaLeft").getTexture(), 2 + (i * 40 + i * 13), 125, 40, 36 ));
        pandaRunLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i=0; i<1; i++)
            frames.add(new TextureRegion(atlasLeft.findRegion("pandaLeft").getTexture(), 151 + i * 40, 126, 40, 36));
        pandaJumpLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        pandaStandLeft = new TextureRegion(atlasLeft.findRegion("pandaLeft").getTexture(), 4, 14, 40, 36);
        frames.clear();

        definePanda();
        setBounds(20, 0, 40 / Pandoradca.PPM, 38 / Pandoradca.PPM);
        setRegion(pandaStandRight);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;
            if (runningRight) {
                switch (currentState) {
                    case JUMPING:
                        region = pandaJumpRight.getKeyFrame(stateTimer);
                        break;
                    case RUNNING:
                        region = pandaRunRight.getKeyFrame(stateTimer, true);
                        break;
                    case STANDING:
                    default:
                        region = pandaStandRight;
                        break;
                }
            }
            else
            {
                switch (currentState) {
                    case JUMPING:
                        region = pandaJumpLeft.getKeyFrame(stateTimer);
                        break;
                    case RUNNING:
                        region = pandaRunLeft.getKeyFrame(stateTimer, true);
                        break;
                    case STANDING:
                    default:
                        region = pandaStandLeft;
                        break;
                }
            }

        if (b2body.getLinearVelocity().x < 0) {
            runningRight = false;
        } else if (b2body.getLinearVelocity().x > 0) {
            runningRight = true;
        }


        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    private State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    private void definePanda() {
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
