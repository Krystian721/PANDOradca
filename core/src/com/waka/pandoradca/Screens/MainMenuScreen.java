package com.waka.pandoradca.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waka.pandoradca.Pandoradca;

public class MainMenuScreen implements Screen {


    final Pandoradca game;

    OrthographicCamera camera;

    private SpriteBatch sb;
    private Texture background;
    private Texture bt_play;
    private Texture bt_exit;

    public MainMenuScreen(final Pandoradca gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Pandoradca.V_WIDTH_MENU, Pandoradca.V_HEIGHT_MENU);

        background= new Texture("menu_bg.png");
        bt_play = new Texture("menu_play1.png");
        bt_exit = new Texture("menu_exit1.png");
        sb = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();


        sb.begin();
        sb.draw(background,0,0,Pandoradca.V_WIDTH_MENU,Pandoradca.V_HEIGHT_MENU);
        sb.draw(bt_play,(Pandoradca.V_WIDTH_MENU/4)-(bt_play.getWidth()/2),(Pandoradca.V_HEIGHT_MENU/2)+100);
        sb.draw(bt_exit,(Pandoradca.V_WIDTH_MENU/4)-(bt_play.getWidth()/2),(Pandoradca.V_HEIGHT_MENU/2)+20);

        sb.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
