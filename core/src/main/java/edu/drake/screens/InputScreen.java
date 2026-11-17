package edu.drake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import edu.drake.BaseInput;
import edu.drake.Main;

public class InputScreen extends BaseScreen {
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
