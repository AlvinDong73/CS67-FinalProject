package edu.drake.screens.rooms;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import edu.drake.Main;
import edu.drake.Player;
import edu.drake.screens.UIScreen;

public class Combat extends UIScreen implements Room {
    public Combat(Main game) {
        super(game);
    }

    @Override
    public void displayCurrentRoom(Table root, Player player) {
        Label label = new Label("Hello World", root.getSkin().get("white", LabelStyle.class));
        root.add(label).expand().center();
    }

    @Override
    public void buildUI(Table root) {
        init(root, null);
    }
}
