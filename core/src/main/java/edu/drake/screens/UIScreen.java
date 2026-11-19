package edu.drake.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import edu.drake.Main;

public abstract class UIScreen extends BaseScreen {
    protected Skin uiSkin;

    public UIScreen(Main game) {
        super(game);
        uiSkin = game.getManager().get("sprites/ui.json", Skin.class);
    }

    @Override
    public void dispose() {
        super.dispose();
        uiSkin.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        Table root = new Table(uiSkin);
        root.setFillParent(true);
        buildUI(root);
        stage.addActor(root);
    }

    public abstract void buildUI(Table root);
}
