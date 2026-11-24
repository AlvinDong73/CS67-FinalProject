package edu.drake.game;

public class CombatData extends RoomData {
    protected ExtraRewardType rewardType;
    protected boolean completed;

    public CombatData() {
        type = RoomType.COMBAT;
        completed = false;
    }

    public CombatData(ExtraRewardType rewardType) {
        type = RoomType.COMBAT;
        completed = false;
        this.rewardType = rewardType;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public enum ExtraRewardType {
        BOSS_REWARD,
        MONEY,
        SKILL,
        STAT,
    }

    public void setRewardType(ExtraRewardType rewardType) {
        this.rewardType = rewardType;
    }

    @Override
    public String toString() {
        if (rewardType == ExtraRewardType.BOSS_REWARD) {
            return "";
        }
        String reward = "";
        switch (rewardType) {
            case MONEY:
                reward = "Earn extra money";
                break;
            case SKILL:
                reward = "Learn an extra skill";
                break;
            case STAT:
                reward = "Gain an extra stat boost";
                break;
            default:
                break;
        }
        return type.toString() + "\n\nBonus reward:\n" + reward;
    }
}
