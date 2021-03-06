package com.waka.pandoradca.Sprites;

import com.badlogic.gdx.Gdx;
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
import com.waka.pandoradca.Tools.Results;

public class Panda extends Sprite {
    private enum State { STANDING, RUNNING, JUMPING }
    private State currentState, previousState;
    private World world;
    public Body b2body;
    private TextureRegion pandaStandRight, pandaStandLeft;
    private Animation<TextureRegion> pandaRunRight, pandaRunLeft, pandaJumpRight, pandaJumpLeft;
    private float stateTimer, startX = 32, startY = 32, lastX = 0.32f, lastY = 0.32f;
    private boolean runningRight;
    private int counter = 0;

    public void setLastXY(float lastX, float lastY){
        this.lastX = lastX;
        this.lastY = lastY;
    }

    public float getLastX(){
        return lastX;
    }

    public Panda(World world) {
        TextureAtlas atlasRight = new TextureAtlas("animations/PandaRight.pack");
        TextureAtlas atlasLeft = new TextureAtlas("animations/pandaLeft.pack");
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

        definePanda(startX, startY);
        setBounds(20, 0, 40 / Pandoradca.PPM, 38 / Pandoradca.PPM);
        setRegion(pandaStandRight);
    }

    public void update(float dt){
        //player falls down
        if (Spikes.hit)
        {
            definePanda(lastX * Pandoradca.PPM, lastY * Pandoradca.PPM);
            Spikes.hit = false;
            counter++;
            if (counter == 2) {
                Results.setLifeLost(Results.getLifeLost() + 1);
                counter = 0;
            }
        }
        else if (b2body.getPosition().y < 0) {
            definePanda(lastX * Pandoradca.PPM, lastY * Pandoradca.PPM);
            if (Results.getLevelNumber() == 1) {
                Results.setForest1Score(Results.getForest1Score() - 100);
                Results.setLifeLost(Results.getLifeLost() + 1);
            } else {
                Results.setForest2Score(Results.getForest2Score() - 100);
                Results.setLifeLost(Results.getLifeLost() + 1);
            }
        }
        else {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
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

    private void definePanda(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / Pandoradca.PPM, y / Pandoradca.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6 / Pandoradca.PPM);
        fixtureDef.filter.categoryBits = Pandoradca.PANDA_BIT;
        fixtureDef.filter.maskBits = Pandoradca.DEFAULT_BIT | Pandoradca.TOOTH_BIT | Pandoradca.WASHING_BIT | Pandoradca.GARBAGE_BIT | Pandoradca.TV_BIT | Pandoradca.PC_BIT | Pandoradca.HOMEWORK_BIT | Pandoradca.READING_BIT | Pandoradca.SWEEPING_BIT;

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
