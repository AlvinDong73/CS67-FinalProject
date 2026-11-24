package edu.drake.game;

public class EliteData extends CombatData {
    public EliteData() {
        super();
        type = RoomType.ELITE;
    }

    public EliteData(ExtraRewardType rewardType) {
        super(rewardType);
        type = RoomType.ELITE;
    }

    @Override
    public String toString() {
        return super.toString() + "\nRewards are better";
    }
}
