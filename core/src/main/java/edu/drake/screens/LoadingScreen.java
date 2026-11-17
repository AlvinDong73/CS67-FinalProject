package edu.drake.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.drake.Main;

public class LoadingScreen extends BaseScreen {
    private BitmapFont font;
    private Label progressText;
    private float progress = 0;
    private ShapeRenderer renderer;

    private final String[] ATLAS_PATHS = {
            "ui.atlas",
    };
    private final String[] SKIN_PATHS = {
            "ui.json",
    };
    private final String[] SFX_PATHS = {
            "melee.ogg",
            "spell.ogg",
    };
    private final String[] FONT_PATHS = {
            "Kreon.ttf",
    };

    public LoadingScreen(Main game) {
        super(game);
        renderer = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        AssetManager manager = game.getManager();
        for (String path : ATLAS_PATHS) {
            manager.load("sprites/" + path, TextureAtlas.class);
        }
        for (String path : SFX_PATHS) {
            manager.load("sfx/" + path, Sound.class);
        }
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(new InternalFileHandleResolver()));
        for (String path : FONT_PATHS) {
            FreeTypeFontLoaderParameter parameter = new FreeTypeFontLoaderParameter();
            parameter.fontFileName = "fonts/" + path;
            parameter.fontParameters.size = 16;
            manager.load(path, BitmapFont.class, parameter);
            manager.finishLoadingAsset(path);
        }
        SkinLoader.SkinParameter skinLoadingParameter = new SkinLoader.SkinParameter(new ObjectMap<>());
        skinLoadingParameter.resources.put("default", manager.get("Kreon.ttf", BitmapFont.class));
        for (String path : SKIN_PATHS) {
            manager.load("sprites/" + path, Skin.class, skinLoadingParameter);
        }
    }

    @Override
    public void show() {
        Table root = new Table();
        root.setFillParent(true);
        progressText = new Label(
                "Loading progress: %.1f%%".formatted(progress * 100),
                new LabelStyle(font, Color.WHITE));
        root.row();
        root.add(progressText)
                .expand()
                .align(Align.center);
        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        // Draw your screen here. "delta" is the time since last render in seconds.
        if (game.getManager().update()) {
            game.setScreen(new MainMenu(game));
        }
        progress = game.getManager().getProgress();
        stage.act(delta);
        progressText.setText("Loading progress: %.1f%%".formatted(progress * 100));
        stage.draw();

        Viewport viewport = stage.getViewport();
        game.getBatch().begin();
        renderer.begin(ShapeType.Filled);
        float h = viewport.getWorldHeight();
        float w = viewport.getWorldWidth();
        renderer.setColor(Color.DARK_GRAY);
        renderer.rect(w / 4, h / 3, w / 2, h / 30);
        renderer.setColor(Color.CYAN);
        renderer.rect(w / 4, h / 3, w / 2 * progress, h / 30);
        renderer.end();
        game.getBatch().end();
    }
}
