package edu.drake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import edu.drake.Main;

/**
 * An abstract class that reduces the boilerplate of implementing the Screen interface.
 * A child class should call `super.render()` to clear the screen prior to rendering.
 * If a child class needs to dispose of additional resources or resize other viewports,
 * then the corresponding superclass method needs to be called when overriding those
 * methods.
 * Author: Alvin Dong
 */
public abstract class BaseScreen implements Screen {
    protected Main game;
    protected Stage stage;

    public void initializeInput() {
        Gdx.input.setInputProcessor(stage);
    }

    public BaseScreen(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport(game.getCamera()), game.getBatch());
        initializeInput();
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if (width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
