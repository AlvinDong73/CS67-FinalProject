package edu.drake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private AssetManager manager = new AssetManager();

    @Override
    public void create() {
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
}