package com.waka.pandoradca.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.waka.pandoradca.Levels.CityLevel;
import com.waka.pandoradca.Levels.ForestLevel;
import com.waka.pandoradca.Levels.HouseLevel;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Scenes.Hud;
import com.waka.pandoradca.Sprites.Panda;
import com.waka.pandoradca.Tools.B2WorldCreator;
import com.waka.pandoradca.Tools.Results;
import com.waka.pandoradca.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    private Panda player;
    private Pandoradca game;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;
    private Hud hud;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private String mapName;
    private ForestLevel level1, level3;
    private HouseLevel level2;
    private CityLevel level4;

    public TiledMap getMap(){
        return map;
    }

    public Box2DDebugRenderer getBox2DDebugRenderer(){
        return box2DDebugRenderer;
    }

    public Pandoradca getGame(){
        return game;
    }

    public OrthogonalTiledMapRenderer getRenderer(){
        return renderer;
    }

    public OrthographicCamera getGameCamera(){
        return gameCamera;
    }

    public World getWorld(){
        return world;
    }

    public Viewport getGamePort(){
        return gamePort;
    }

    public Panda getPlayer(){
        return player;
    }

    //constructor
    public PlayScreen(final Pandoradca game){
        switch (Results.getLevelNumber()){
            case 1:
                level1 = new ForestLevel(this);
                mapName = level1.getLevelName();
                hud = new Hud(game.batch, 0);
                break;
            case 2:
                level2 = new HouseLevel(this);
                mapName = level2.getLevelName();
                hud = new Hud(game.batch, 5);
                break;
            case 3:
                level3 = new ForestLevel(this);
                mapName = level3.getLevelName();
                hud = new Hud(game.batch, 0);
                break;
            case 4:
                level4 = new CityLevel(this);
                mapName = level4.getLevelName();
                hud = new Hud(game.batch, 6);
                break;
        }
        this.game = game;
        gameCamera = new OrthographicCamera();
        gamePort = new FillViewport(Pandoradca.V_WIDTH / Pandoradca.PPM, Pandoradca.V_HEIGHT / Pandoradca.PPM, gameCamera);
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapName);
        box2DDebugRenderer = new Box2DDebugRenderer();
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Pandoradca.PPM);
        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        new B2WorldCreator(world, map, game);
        player = new Panda(world);
        world.setContactListener(new WorldContactListener());
    }

    @Override
    public void show() {
    }

    public void handleInput() {
        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP)) && (player.b2body.getLinearVelocity().y == 0)) {
            player.setLastXY(player.b2body.getPosition().x, player.b2body.getPosition().y);
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)) {
            if (player.b2body.getPosition().x > player.getLastX() + 2.5f)
                player.setLastXY(player.b2body.getPosition().x - 0.02f, player.b2body.getPosition().y);
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)) {
            if (player.b2body.getPosition().x > player.getLastX() + 2.5f)
                player.setLastXY(player.b2body.getPosition().x + 0.02f, player.b2body.getPosition().y);
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    private void update(float delta) {
        switch (Results.getLevelNumber()){
            case 1:
                level1.update(delta);
                break;
            case 2:
                level2.update(delta);
                break;
            case 3:
                level3.update(delta);
                break;
            case 4:
                level4.update(delta);
                break;
        }
    }

    @Override
    public void render(float delta) {
        switch (Results.getLevelNumber()){
            case 1:
                level1.render();
                break;
            case 2:
                level2.render();
                break;
            case 3:
                level3.render();
                break;
            case 4:
                level4.render();
                break;
        }
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        hud.dispose();
    }

    public static void boundary(Camera camera, PlayScreen screen, int X){
        float startX = camera.viewportWidth / 2;
        Vector3 position = camera.position;
        position.x = screen.getPlayer().b2body.getPosition().x;
        if (position.x > startX + X)
            position.x = startX + X;
        else if (position.x < startX)
            position.x = startX;
        camera.position.set(position);
        camera.update();
    }
}




