package com.waka.pandoradca.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Locale;

public class FontFactory {
    private static FontFactory instance;
    private BitmapFont plFont;

    private FontFactory() {
        super();
    }

    public static synchronized FontFactory getInstance() {
        if (instance == null) {
            instance = new FontFactory();
        }
        return instance;
    }

    public void initialize() {
        if (plFont != null) plFont.dispose();
        plFont = generateFont("font/Montserrat-Bold.otf");
    }

    private BitmapFont generateFont(String fontName) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = "aąbcćdeęfghijklłmnńoóprsśtuvwqxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWQXYZŹŻ1234567890-.,:;_!?";
        parameter.size = 21;
        BitmapFont font = generator.generateFont(parameter);
        font.getData().setScale(0.7f);
        generator.dispose();
        return font;
    }

    public BitmapFont getFont(Locale locale) {
        if ("pl".equals(locale.getLanguage()))
            return plFont;
        else
            throw new IllegalArgumentException("Not supported language");
    }

    public void dispose() {
        plFont.dispose();
    }
}

