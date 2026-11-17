package edu.drake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
        Table menu = new Table();
        TextButton play = new TextButton("Start New Run", uiSkin);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: Transition into new run
            }
        });
        TextButton continueRun = new TextButton("Continue Run", uiSkin);
        continueRun.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: Transition into saved run
            }
        });
        // TODO: Check if a preexisting run has been saved
        continueRun.setDisabled(true);
        TextButton settings = new TextButton("Settings", uiSkin);
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: Transition into settings menu
            }
        });
        TextButton quit = new TextButton("Quit Game", uiSkin);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        menu.row();
        menu.add(play)
            .pad(10)
            .minWidth(300);
        menu.row();
        menu.add(continueRun)
            .pad(10)
            .minWidth(300);
        menu.row();
        menu.add(settings)
            .pad(10)
            .minWidth(300);
        menu.row();
        menu.add(quit)
            .pad(10)
            .minWidth(300);
        root.add(menu)
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
