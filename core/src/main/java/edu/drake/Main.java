package edu.drake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.drake.screens.LoadingScreen;

public class Main extends Game {
    private AssetManager manager;
    private SpriteBatch batch;
    private Camera camera;
    private Viewport viewport;

    @Override
    public void create() {
        manager = new AssetManager();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1280, 720, camera);
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        super.render();
    }

    @Override
    public void dispose() {
        manager.dispose();
        super.dispose();
    }

    public AssetManager getManager() {
        return manager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Camera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }
}