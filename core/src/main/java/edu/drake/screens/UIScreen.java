package edu.drake.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import edu.drake.Main;

public abstract class UIScreen extends BaseScreen {
    protected Skin uiSkin;
    protected Table uiRoot;

    public UIScreen(Main game) {
        super(game);
        uiSkin = game.getManager().get("sprites/ui.json", Skin.class);
        uiRoot = new Table(uiSkin);
        uiRoot.setFillParent(true);
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
        initUI(uiRoot);
        stage.addActor(uiRoot);
    }

    public abstract void initUI(Table root);
}
