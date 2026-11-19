package edu.drake.screens.rooms;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import edu.drake.Main;
import edu.drake.Player;
import edu.drake.screens.UIScreen;

public abstract class Room extends UIScreen {
    public Room(Main game) {
        super(game);
    }

    public enum RoomState {
        SELECTING_NEXT_ROOM,
        VIEWING_DETAILS,
        PAUSED,
        ACTIVE,
    }

    public void displayRoomSelect(Table root, Player player) {

    }

    public void displayPlayerDetails(Table root, Player player) {

    }

    public void displayPauseMenu(Table root, Player player) {

    }

    public abstract void displayCurrentRoom(Table root, Player player);

    public void init(Table root, Player player) {
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