package edu.drake.game;

import java.io.Serializable;

public abstract class RoomData implements Serializable {
    protected RoomType type;

    public RoomType getType() {
        return type;
    }
}
