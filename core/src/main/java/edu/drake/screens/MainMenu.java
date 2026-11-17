package edu.drake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import edu.drake.Main;

public class MainMenu extends BaseScreen {
    private Skin uiSkin;

    public MainMenu(Main game) {
        super(game);
        uiSkin = game.getManager().get("sprites/ui.json", Skin.class);
    }

    @Override
    public void show() {
        // Prepare your screen here.
        Table root = new Table(uiSkin);
        root.setFillParent(true);
        root.row();
        root.add(new Label("Hello World", uiSkin.get("white", LabelStyle.class)))
            .expand()
            .align(Align.center);
        root.row();
        TextButton b = new TextButton("Quit Game", uiSkin);
        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        root.add(b)
            .expand()
            .align(Align.center);
        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        // Draw your screen here. "delta" is the time since last render in seconds.
        stage.act(delta);
        stage.draw();
    }

}
