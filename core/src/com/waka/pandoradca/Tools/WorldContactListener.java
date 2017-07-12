package com.waka.pandoradca.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.waka.pandoradca.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "legs" || fixB.getUserData()=="legs"){
            Fixture legs = fixA.getUserData() == "legs" ? fixA : fixB;
            Fixture object = legs == fixA ? fixB : fixA;
            if (object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHit();
            }
        }

        if (fixA.getUserData() == "front" || fixB.getUserData()=="front"){
            Fixture front = fixA.getUserData() == "front" ? fixA : fixB;
            Fixture object = front == fixA ? fixB : fixA;
            if (object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHit();
            }
        }

        if (fixA.getUserData() == "back" || fixB.getUserData()=="back"){
            Fixture back = fixA.getUserData() == "back" ? fixA : fixB;
            Fixture object = back == fixA ? fixB : fixA;
            if (object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
