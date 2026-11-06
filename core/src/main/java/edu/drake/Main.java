package edu.drake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private AssetManager manager;
    private SpriteBatch batch;
    private Camera camera;
    private Viewport viewport;

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