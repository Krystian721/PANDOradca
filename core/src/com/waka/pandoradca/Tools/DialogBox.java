package com.waka.pandoradca.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.waka.pandoradca.Pandoradca;

public class DialogBox{
    public Stage stage;
    public String string;
    public boolean cancelled = false, hint = false, noAnswer = false, checkAnswer;
    private Skin skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
    private Dialog dialog;

    public DialogBox(String title, SpriteBatch spriteBatch){
        Viewport viewport = new FillViewport(Pandoradca.V_WIDTH, Pandoradca.V_HEIGHT, new OrthographicCamera());
        Gdx.input.setInputProcessor(stage = new Stage(viewport, spriteBatch));
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        dialog = new Dialog(title, skin);
    }

    public void addLabel(String labelText){
        Label label = new Label(labelText, skin);
        label.setWrap(true);
        label.setAlignment(Align.center);
        dialog.getContentTable().add(label).width(100).row();
    }

    public void addCancelButton(String buttonText){
        TextButton button = new TextButton(buttonText, skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                cancelled = true;
            }
        });
        dialog.button(button);
    }

    public void addHintButton(String buttonText){
        TextButton button = new TextButton(buttonText, skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                if (!hint)
                    hint = true;
                else
                    hint = false;
            }
        });
        dialog.button(button);
    }

    public void addTextBoxAndConfirmButton(String hint, String buttonText){
        final TextField textField = new TextField(hint, skin);
        textField.addListener(new FocusListener() {
            @Override
            public boolean handle(Event event) {
                textField.setText("");
                return super.handle(event);
            }
        });
        dialog.getContentTable().add(textField).row();
        TextButton button = new TextButton(buttonText, skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!textField.getText().isEmpty()){
                    string = textField.getText();
                    checkAnswer = true;
                    dispose();
                }
                else
                    noAnswer = true;
            }
        });
        dialog.button(button);
    }

    public void dialogShow(){
        dialog.show(stage);
    }

    private void dispose(){
        stage.dispose();
    }
}
