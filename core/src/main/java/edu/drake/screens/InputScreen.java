package edu.drake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import edu.drake.BaseInput;
import edu.drake.Main;

/**
 * An abstract class that handles additional input besides the input that a Stage's
 * widgets handles.
 * Author: Alvin Dong
 */
public abstract class InputScreen extends BaseScreen {
    protected BaseInput input;

    public InputScreen(Main game, BaseInput input) {
        this.input = input;
        super(game);
    }

    @Override
    public void initializeInput() {
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, input));
    }
}
