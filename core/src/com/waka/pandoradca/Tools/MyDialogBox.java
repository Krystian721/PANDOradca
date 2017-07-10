package com.waka.pandoradca.Tools;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyDialogBox extends Dialog{

    public MyDialogBox(String title, Skin skin) {
        super(title, skin);
    }

    public MyDialogBox(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public MyDialogBox(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    {
        text("???");
        button("YES");
        button("NO");
    }

    @Override
    protected void result(Object object) {
        System.out.println(object);
    }
}
