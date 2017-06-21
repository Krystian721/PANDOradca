package com.waka.pandoradca;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.waka.pandoradca.Screens.PlayScreen;

public class Pandoradca extends Game {
	public static final int V_WIDTH = 500;
	public static final int V_HEIGHT = 260;
	public static final float PPM = 100;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
