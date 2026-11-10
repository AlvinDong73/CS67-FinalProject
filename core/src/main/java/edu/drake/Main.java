package edu.drake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.drake.screens.LoadingScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private AssetManager manager;
    private SpriteBatch batch;
    private Camera camera;
    private Viewport viewport;

    @Override
    public void create() {
        manager = new AssetManager();
        batch = new SpriteBatch();
        viewport = new FitViewport(0, 0);
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        manager.dispose();
        super.dispose();
    }
}