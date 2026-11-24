package edu.drake.game;

public class BossData extends CombatData {
    public BossData() {
        super();
        type = RoomType.BOSS;
        rewardType = ExtraRewardType.BOSS_REWARD;
    }

    @Override
    public String toString() {
        return type.toString() + "\n\nBeat this boss to\nprogress to the\nnext level";
    }
}
