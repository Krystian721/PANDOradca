package com.waka.pandoradca.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Scenes.Hud;
import com.waka.pandoradca.Sprites.Panda;
import com.waka.pandoradca.Tools.B2WorldCreator;
import com.waka.pandoradca.Tools.FontFactory;
import com.waka.pandoradca.Tools.WorldContactListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

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

    //font
    private Locale plLocale;
    private StringBuilder stringBuilder;


    public static final int maxQuestions = 17;
    private String[] answerTable;


    public PlayScreen(final Pandoradca game) {
        atlas = new TextureAtlas("Panda.pack");

        this.game = game;

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

        //font.getData().setScale(.9f, .9f);
        FontFactory.getInstance().initialize();
        plLocale = new Locale("pl", "PL");
        answerTable = new String[maxQuestions];

        stringBuilder = new StringBuilder();
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
    private int questionNumber = 0;
    private boolean answer;
    private int showQ = 0;
    private SpriteBatch spriteBatch;
    private String text;
    private boolean fileExists;
    private FileHandle file;


    private void update(float delta) {
        switch (state) {
            case NOQUESTION:
                showQ = 0;
                answerText = null;
                handleInput(delta);

                gameCamera.position.x = player.b2body.getPosition().x;

                world.step(1/60f, 6, 2);

                hud.update(delta);
                player.update(delta);

                gameCamera.update();
                renderer.setView(gameCamera);
                if (((questionTimer = hud.getTime()) > 1) && questionNumber < maxQuestions)
                {
                    state = QUESTION;
                }
                break;
            case QUESTION:
                if (showQ == 0) {
                    showQ++;
                    answer = false;
                    questionBG = new Texture("questions/module1/bg.jpg");
                    spriteBatch = new SpriteBatch();
                    questionNumber++;
                    if (fileExists = Gdx.files.internal("questions/module1/"+questionNumber+".txt").exists()) {
                        file = Gdx.files.internal("questions/module1/" + questionNumber + ".txt");
                        try {
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream("questions/module1/" + questionNumber + ".txt"), "windows-1250"
                                    )
                            );
                            String string = "";
                            while ((string = in.readLine()) != null) {
                                stringBuilder.append(string + "\r\n");
                            }
                            text = stringBuilder.toString();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                spriteBatch.begin();
                spriteBatch.draw(questionBG, 0, 0, gamePort.getScreenWidth(), gamePort.getScreenHeight());
                FontFactory.getInstance().getFont(plLocale).draw(spriteBatch, text, 50, 400.f);
                spriteBatch.end();
                answer = handleQuestionInput();
                if (answer) {
                    hud.resetTimer();
                    questionBG.dispose();
                    state = NOQUESTION;
                }
                break;
            default: break;
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
        /*
        if (state == QUESTION)
        {
            if (showQ == 0) {

            }
        }*/
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
        FontFactory.getInstance().dispose();
    }

    private String answerText;
    private boolean inputCheck;

    private boolean handleQuestionInput(){
        if (Gdx.input.justTouched()) {
            Gdx.input.getTextInput(new Input.TextInputListener() {

                @Override
                public void input(String text) {
                    answerText = text.toLowerCase();
                }

                @Override
                public void canceled() {
                    inputCheck=false;
                }
            }, "Jaki to zawód?", "", "Tu wpisz odpowiedź!");
        }
        if (!(answerText==null)) {
            answerTable[questionNumber-1]=answerText;
            return true;
        }
        else
            return false;
    }
}

