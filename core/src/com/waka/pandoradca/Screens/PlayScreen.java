package com.waka.pandoradca.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.String;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Scenes.Hud;
import com.waka.pandoradca.Sprites.Panda;
import com.waka.pandoradca.Tools.B2WorldCreator;
import com.waka.pandoradca.Tools.WorldContactListener;

public class PlayScreen implements Screen {

    static final int NOQUESTION = 0;
    static final int QUESTION = 1;
    private Texture questionBG;

    private Pandoradca game;
    private TextureAtlas atlas;
    private int state;

    private OrthographicCamera gameCamera;
    private Viewport gamePort;
    private Hud hud;

    //Tiled
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Panda player;


    public PlayScreen(final Pandoradca game) {
        atlas = new TextureAtlas("Panda.pack");

        this.game = game;

        questionBG = new Texture("1.jpg");

        state = NOQUESTION;

        gameCamera = new OrthographicCamera();

        gamePort = new FillViewport(Pandoradca.V_WIDTH / Pandoradca.PPM, Pandoradca.V_HEIGHT / Pandoradca.PPM, gameCamera);

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Pandoradca.PPM);

        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        player = new Panda(world, this);

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float delta) {
        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP)) && (player.b2body.getLinearVelocity().y == 0)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2))
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2))
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

    }

    private int questionTimer;
    private int showQ = 0;

    public void update(float delta) {
        switch (state) {
            case NOQUESTION:
                showQ = 0;
                handleInput(delta);

                gameCamera.position.x = player.b2body.getPosition().x;

                world.step(1/60f, 6, 2);

                hud.update(delta);
                player.update(delta);

                gameCamera.update();
                renderer.setView(gameCamera);

                if ((questionTimer = hud.getTime()) > 3)
                {
                    state = QUESTION;
                }
                break;
            case QUESTION:
                boolean answer = false;
                if (showQ == 0) {
                    showQ++;
                    SpriteBatch spriteBatch = new SpriteBatch();
                    spriteBatch.begin();
                    spriteBatch.draw(questionBG, 0, 0, Pandoradca.V_WIDTH_MENU, Pandoradca.V_HEIGHT_MENU);
                    spriteBatch.end();
                }
                if (answer) {
                    hud.resetTimer();
                    questionBG.dispose();
                    state = NOQUESTION;
                }
                break;
        }
    }


    @Override
    public void render(float delta) {
        update(delta);

        if (state == NOQUESTION) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.stage.draw();

            renderer.render();

            box2DDebugRenderer.render(world, gameCamera.combined);

            game.batch.setProjectionMatrix(gameCamera.combined);
            game.batch.begin();
            player.draw(game.batch);
            game.batch.end();
        }
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

    public boolean handleQuestionInput(float dt){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            return true;
        else return false;
    }
}
