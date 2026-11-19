package edu.drake.screens.rooms;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import edu.drake.Player;

public interface Room {
    public enum RoomState {
        SELECTING_NEXT_ROOM,
        VIEWING_DETAILS,
        PAUSED,
        ACTIVE,
    }

    public default void displayRoomSelect(Table root, Player player) {

    }

    public default void displayPlayerDetails(Table root, Player player) {

    }

    public default void displayPauseMenu(Table root, Player player) {

    }

    public void displayCurrentRoom(Table root, Player player);

    public default void init(Table root, Player player) {
        root.setFillParent(true);
        root.debug();
        Table hud = new Table(root.getSkin());
        hud.setBackground("base");
        Label label = new Label("PLACEHOLDER HUD", root.getSkin());
        hud.row();
        hud.add(label).pad(5, 5, 5, 5).left().expand();
        root.row();
        root.add(hud).expandX().fillX();
        Table mainDisplay = new Table(root.getSkin());
        displayCurrentRoom(mainDisplay, player);
        root.row();
        root.add(mainDisplay).expand();
    }
}