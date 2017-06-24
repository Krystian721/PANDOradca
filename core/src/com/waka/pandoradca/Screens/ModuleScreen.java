
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

/**
 * Created by szajb on 21.06.2017.
 */

public class ModuleScreen implements Screen {


    final Pandoradca game;

    OrthographicCamera camera;

    private SpriteBatch sb;
    private Texture background;

    public ModuleScreen(final Pandoradca gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Pandoradca.V_WIDTH, Pandoradca.V_WIDTH);

        background = new Texture("module_bg.png");

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
        sb.end();


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


