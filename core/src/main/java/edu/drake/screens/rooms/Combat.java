package edu.drake.screens.rooms;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import edu.drake.Main;

public class Combat extends Room {
    public Combat(Main game) {
        super(game);
    }

    @Override
    public Table buildCurrentRoomUI() {
        Table screenRoot = new Table(uiSkin);
        Label label = new Label("COMBAT PLACEHOLDER", uiSkin.get("white", LabelStyle.class));
        screenRoot.row();
        screenRoot.add(label).expand().pad(10).center();

        TextButton lose = new TextButton("Lose Combat", uiSkin);
        lose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                
            }
        });
        TextButton win = new TextButton("Win Combat", uiSkin);
        win.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchState(RoomState.SELECTING_NEXT_ROOM);
            }
        });

        screenRoot.row();
        screenRoot.add(lose)
            .pad(10)
            .minWidth(300);
        screenRoot.row();
        screenRoot.add(win)
            .pad(10)
            .minWidth(300);

        return screenRoot;
    }
}
