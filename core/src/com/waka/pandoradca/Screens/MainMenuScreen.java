package com.waka.pandoradca.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waka.pandoradca.Pandoradca;
import com.waka.pandoradca.Tools.DialogBox;
import com.waka.pandoradca.Tools.Results;

public class MainMenuScreen implements Screen {
    private final Pandoradca game;
    private OrthographicCamera camera;
    private SpriteBatch sb;
    private Texture background;
    private boolean startGame = false, dialogShowed = false;

    private DialogBox dialogBox;

    public MainMenuScreen(final Pandoradca game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Pandoradca.V_WIDTH_MENU, Pandoradca.V_HEIGHT_MENU);
        background = new Texture("menu.png");
        sb = new SpriteBatch();
        dialogBox = null;
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
        sb.draw(background, 0, 0, Pandoradca.V_WIDTH_MENU, Pandoradca.V_HEIGHT_MENU);
        sb.end();

        double ox = (Gdx.graphics.getWidth() * 21.5) / 100;
        double oy_start = (Gdx.graphics.getHeight() * 38) / 100;
        double oy_exit = (Gdx.graphics.getHeight() * 53) / 100;

        if ((Gdx.input.getX() > ox && Gdx.input.getX() < ox + 140 && Gdx.input.getY() > oy_start && Gdx.input.getY() < oy_start + 40) && (!dialogShowed)) {
            if ((!dialogShowed) && (!startGame)) {
                dialogBox = new DialogBox("Uzupełnij poniższe pola!", sb);
                dialogBox.addLabel("");
                dialogBox.add2TextBoxesAndConfirmButton("Jak się nazywasz?", "Adres email", "OK");
                dialogBox.dialogShow();
                game.batch.setProjectionMatrix(dialogBox.stage.getCamera().projection);
                dialogShowed = true;
                background.dispose();
            }
        }

        if (dialogShowed) {
            if (!dialogBox.checkAnswer)
            {
                dialogBox.stage.draw();
                if (dialogBox.noAnswer)
                {
                    dialogBox = new DialogBox("Uzupełnij poniższe pola!", sb);
                    dialogBox.addLabel("");
                    dialogBox.add2TextBoxesAndConfirmButton("Jak się nazywasz?", "Adres email", "OK");
                    dialogBox.dialogShow();
                    game.batch.setProjectionMatrix(dialogBox.stage.getCamera().projection);
                    dialogShowed = true;
                }
            }
            else
            {
                Results.setStudentName(dialogBox.string);
                Results.setTeachersEmail(dialogBox.string2);
                startGame = true;
            }
        }

        if ((startGame) && (dialogShowed)) {
            dispose();
            game.setScreen(new PlayScreen(this.game));
        }

        if ((Gdx.input.getX() > ox && Gdx.input.getX() < ox + 140 && Gdx.input.getY() > oy_exit && Gdx.input.getY() < oy_exit + 40) && (!dialogShowed)) {
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
        background.dispose();
    }
}


