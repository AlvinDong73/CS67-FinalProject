package edu.drake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import edu.drake.Main;
import edu.drake.screens.rooms.Combat;

public class MainMenu extends UIScreen {

    public MainMenu(Main game) {
        super(game);
    }

    @Override
    public void buildUI(Table root) {
        root.row();
        root.add(new Label("Hello World", uiSkin.get("white", LabelStyle.class)))
            .expand()
            .center();
        root.row();
        Table menu = new Table();
        TextButton play = new TextButton("Start New Run", uiSkin);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: Transition into new run
                game.setScreen(new Combat(game));
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
            .center();
    }
}
