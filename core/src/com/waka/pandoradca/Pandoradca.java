package com.waka.pandoradca;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waka.pandoradca.Screens.MainMenuScreen;

public class Pandoradca extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 260;
	public static final int V_WIDTH_MENU = 700;
	public static final int V_HEIGHT_MENU = 500;
	public static final float PPM = 100;
	public SpriteBatch batch;

	//region CategoryBits
	public static final short DEFAULT_BIT = 1;
	public static final short PANDA_BIT = 2;
	public static final short TOOTH_BIT = 4;
	public static final short WASHING_BIT = 8;
	public static final short GARBAGE_BIT = 16;
	public static final short TV_BIT = 32;
	public static final short PC_BIT = 64;
	public static final short HOMEWORK_BIT = 128;
	public static final short READING_BIT = 256;
	public static final short SWEEPING_BIT = 512;
	public static final short DESTROYED_BIT = 1024;
	//endregion
	
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
	}
}
