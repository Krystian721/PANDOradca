package com.waka.pandoradca.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waka.pandoradca.Pandoradca;

import org.w3c.dom.css.Rect;

import java.awt.Rectangle;

public class MainMenuScreen implements Screen {
    final Pandoradca game;

    OrthographicCamera camera;

    private SpriteBatch sb;
    private Texture background;
    private Texture bt_play;
    private Texture bt_exit;
    private Texture bt_score;
    private Rectangle play;

    public MainMenuScreen(final Pandoradca gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Pandoradca.V_WIDTH_MENU, Pandoradca.V_HEIGHT_MENU);

        background= new Texture("menu_bg.png");
        bt_play = new Texture("menu_play1.png");
        bt_exit = new Texture("menu_exit1.png");
        bt_score = new Texture("menu_score.png");
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
        sb.draw(bt_play,(Pandoradca.V_WIDTH_MENU/4)-(bt_play.getWidth()/2),(Pandoradca.V_HEIGHT_MENU/2)+10);
        sb.draw(bt_score,(Pandoradca.V_WIDTH_MENU/4)-(bt_play.getWidth()/2),(Pandoradca.V_HEIGHT_MENU/2)-60);
        sb.draw(bt_exit,(Pandoradca.V_WIDTH_MENU/4)-(bt_play.getWidth()/2),(Pandoradca.V_HEIGHT_MENU/2)-130);
        sb.end();

        int ox = (Pandoradca.V_WIDTH_MENU/4)-(bt_play.getWidth()/2);

        if (Gdx.input.getX() > ox && Gdx.input.getX()< ox+125 && Gdx.input.getY()>170 && Gdx.input.getY()<220) {
            System.out.println("Play");
            dispose();
            game.setScreen(new PlayScreen(game, 1));
        }
        if (Gdx.input.getX() > ox && Gdx.input.getX()< ox+125 && Gdx.input.getY()>310 && Gdx.input.getY()<360) {
            System.out.println("Exit");
            dispose();
            //Gdx.app.exit();
        }
        if (Gdx.input.getX() > ox && Gdx.input.getX()< ox+125 && Gdx.input.getY()>240 && Gdx.input.getY()<290) {
            System.out.println("score");
            dispose();
            //game.setScreen(new ModuleScreen(game));
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
