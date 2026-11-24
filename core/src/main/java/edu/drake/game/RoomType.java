package edu.drake.game;

public enum RoomType {
    SHOP,
    COMBAT,
    ELITE,
    BOSS;

    @Override
    public String toString() {
        switch (this) {
            case COMBAT:
                return "Combat";
            case SHOP:
                return "Shop";
            case ELITE:
                return "Elite Combat";
            case BOSS:
                return "Boss";
            default:
                return "ERROR";
        }
    }
}