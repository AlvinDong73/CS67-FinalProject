package edu.drake;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Optional;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
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
    private Optional<RunState> run;
    private Preferences preferences;

    @Override
    public void create() {
        manager = new AssetManager();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1280, 720, camera);
        preferences = Gdx.app.getPreferences("config");
        run = Optional.empty();
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

    public Preferences getPreferences() {
        return preferences;
    }

    public void setRun(RunState state) {
        if (state == null) {
            run = Optional.empty();
        } else {
            run = Optional.of(state);
        }
    }

    public RunState getRun() {
        return run.orElse(null);
    }

    public void save() {
        run.ifPresent((state) -> {
            try {
                FileHandle handle = Gdx.files.local("save.dat");
                OutputStream out = handle.write(false);
                ObjectOutputStream outputStream = new ObjectOutputStream(out);
                outputStream.writeObject(state);
                outputStream.close();
                out.close();
            } catch (IOException e) {
                System.out.println("something went wrong with saving data");
            }
        });
    }

    public void load() {
        try {
            FileHandle handle = Gdx.files.local("save.dat");
            if (handle.exists()) {
                InputStream in = handle.read();
                ObjectInputStream inputStream = new ObjectInputStream(in);
                RunState state = (RunState) inputStream.readObject();
                run = Optional.of(state);
                inputStream.close();
                in.close();
            }
        } catch (IOException e) {
            System.out.println("something went wrong with loading data");
        } catch (ClassNotFoundException e) {
            System.out.println("something went wrong with loading data");
        }
    }

    public boolean hasSavedRun() {
        return Gdx.files.local("save.dat").exists();
    }

    public void clear() {
        FileHandle handle = Gdx.files.local("save.dat");
        if (handle.exists()) {
            handle.delete();
        }
    }
}