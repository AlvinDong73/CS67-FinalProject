package edu.drake.screens.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import edu.drake.Main;
import edu.drake.Player;
import edu.drake.RunState;
import edu.drake.game.RoomType;
import edu.drake.screens.KeyboardInput;
import edu.drake.screens.UIScreen;

public abstract class Room extends UIScreen {
    protected InputProcessor keyboard;
    protected RoomState state;
    protected Player player;
    protected boolean isPaused;

    public Room(Main game) {
        state = RoomState.ACTIVE;
        isPaused = false;
        Player p = game.getRun().getPlayer();
        this.player = game.getRun().getPlayer();
        if (p.getRoom() >= 10) {
            p.nextLevel();
            game.getRun().setSeenRandomShop(false);
        } else {
            p.incrementRoom();
        }
        super(game);
    }

    @Override
    public void initializeInput() {
        this.keyboard = new KeyboardInput() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        togglePause();
                        return true;
                    default:
                        return false;
                }
            }
            @Override
            public boolean keyTyped(char character) {
                return false;
            }
            @Override
            public boolean keyUp(int keycode) {
                return false;
            }
        };
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, keyboard));
    }

    public enum RoomState {
        SELECTING_NEXT_ROOM,
        VIEWING_DETAILS,
        ACTIVE,
    }

    public void togglePause() {
        if (isPaused) {
            switchState(state);
            isPaused = false;
        } else {
            uiRoot.clear();
            uiRoot.row();
            uiRoot.add(buildPauseMenuUI()).expand();
            isPaused = true;
        }
    }

    public void switchState(RoomState state) {
        this.state = state;
        uiRoot.clear();
        uiRoot.row();
        uiRoot.add(buildHUD()).expandX().fillX();
        uiRoot.row();
        switch (state) {
            case SELECTING_NEXT_ROOM:
                uiRoot.add(buildRoomSelectUI()).expand();
                break;
            case VIEWING_DETAILS:
                uiRoot.add(buildPlayerDetailsUI()).expand();
                break;
            case ACTIVE:
                uiRoot.add(buildCurrentRoomUI()).expand();
                break;
            default:
                break;
        }
    }

    public Table buildRoomSelectUI() {
        Table screenRoot = new Table(uiSkin);
        Label label = new Label("Select a Room", uiSkin.get("white", LabelStyle.class));
        screenRoot.row();
        screenRoot.add(label).expand().pad(10).center();

        RunState state = game.getRun();

        TextButton left = new TextButton(state.getLeft().toString(), uiSkin);
        left.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                state.setCurrent(state.getLeft());
                state.setRooms();
                if (state.getCurrent().getType() != RoomType.SHOP) {
                    game.setScreen(new Combat(game));
                } else {
                    // TODO: Shop screen
                }
            }
        });

        Table buttonRow = new Table(uiSkin);
        buttonRow.row();
        buttonRow.add(left)
            .pad(10)
            .minWidth(300)
            .minHeight(200)
            .center();
        if (state.getRight() != null) {
            TextButton right = new TextButton(state.getRight().toString(), uiSkin);
            right.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.getRun().setCurrent(state.getRight());
                    state.setRooms();
                    if (state.getCurrent().getType() != RoomType.SHOP) {
                        game.setScreen(new Combat(game));
                    } else {
                        // TODO: Shop screen
                    }
                }
            });
            buttonRow.add(right)
                .pad(10)
                .minWidth(300)
                .minHeight(200)
                .center();
        }
        screenRoot.row();
        screenRoot.add(buttonRow).expand().center();
        return screenRoot;
    }

    public Table buildPlayerDetailsUI() {
        Table screenRoot = new Table(uiSkin);
        Label label = new Label("Paused", uiSkin.get("white", LabelStyle.class));
        screenRoot.row();
        screenRoot.add(label).expand().pad(10).center();

        TextButton quit = new TextButton("Quit", uiSkin);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                
            }
        });
        TextButton settings = new TextButton("Settings", uiSkin);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                
            }
        });
        TextButton resume = new TextButton("Resume", uiSkin);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchState(RoomState.ACTIVE);
            }
        });

        screenRoot.row();
        screenRoot.add(quit)
            .pad(10)
            .minWidth(300);
        screenRoot.row();
        screenRoot.add(settings)
            .pad(10)
            .minWidth(300);
        screenRoot.row();
        screenRoot.add(resume)
            .pad(10)
            .minWidth(300);

        return screenRoot;
    }

    public Table buildPauseMenuUI() {
        Table screenRoot = new Table(uiSkin);
        Label label = new Label("Paused", uiSkin.get("white", LabelStyle.class));
        screenRoot.row();
        screenRoot.add(label).expand().pad(10).center();

        TextButton mainMenu = new TextButton("Main Menu", uiSkin);
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: save game then quit to main menu
            }
        });
        TextButton settings = new TextButton("Settings", uiSkin);
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: settings menu
            }
        });
        TextButton quit = new TextButton("Forefit Run", uiSkin);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: give up function
            }
        });
        TextButton resume = new TextButton("Resume", uiSkin);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                togglePause();
                switchState(state);
            }
        });

        screenRoot.row();
        screenRoot.add(mainMenu)
            .pad(10)
            .minWidth(300);
        screenRoot.row();
        screenRoot.add(settings)
            .pad(10)
            .minWidth(300);
        screenRoot.row();
        screenRoot.add(quit)
            .pad(10)
            .minWidth(300);
        screenRoot.row();
        screenRoot.add(resume)
            .pad(10)
            .minWidth(300);

        return screenRoot;
    }

    public abstract Table buildCurrentRoomUI();

    public Table buildHUD() {
        Table hud = new Table(uiSkin);
        hud.setBackground("base");
        Label name = new Label(player.getName(), uiSkin);
        Label progress1 = new Label("Room " + player.getRoom() +"/10", uiSkin);
        Label progress2 = new Label("Level " + player.getLevel() +"/5", uiSkin);
        hud.row();
        hud.add(name).pad(5).left();
        hud.add(progress1).pad(5).left();
        hud.add(progress2).pad(5).left().expand();
        return hud;
    }

    @Override
    public void initUI(Table root) {
        root.debug();
        switchState(state);
    }
}