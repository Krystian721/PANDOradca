package com.waka.pandoradca;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.waka.pandoradca.Screens.MainMenuScreen;
import com.waka.pandoradca.Screens.PlayScreen;

public class Pandoradca extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 260;
	public static final int V_WIDTH_MENU = 700;
	public static final int V_HEIGHT_MENU = 500;
	public static final int V_WIDTH_QUESTION = 800;
	public static final int V_HEIGHT_QUESTION = 600;
	public static final float PPM = 100;

	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
