package edu.drake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private AssetManager manager;

    @Override
    public void create() {
        manager = new AssetManager();
        setScreen(new FirstScreen());
    }

    @Override
    public void render() {
        if (manager.update()) {
            // transition to main menu
            return;
        }
        float progress = manager.getProgress();
        super.render();
    }

    @Override
    public void dispose() {
        manager.dispose();
        super.dispose();
    }
}