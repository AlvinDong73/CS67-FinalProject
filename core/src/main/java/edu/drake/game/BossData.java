package edu.drake.game;

public class BossData extends CombatData {
    public BossData() {
        super();
        type = RoomType.BOSS;
        rewardType = null;
    }

    @Override
    public String toString() {
        return super.toString() + "\nBeat this boss to\nprogress to the\nnext level";
    }
}
