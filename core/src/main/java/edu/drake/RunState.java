package edu.drake;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;

import edu.drake.game.BossData;
import edu.drake.game.CombatData;
import edu.drake.game.EliteData;
import edu.drake.game.RoomData;
import edu.drake.game.RoomType;
import edu.drake.game.ShopData;
import edu.drake.game.CombatData.ExtraRewardType;

public class RunState implements Serializable {
    private Player player;
    private RoomData left;
    private RoomData right;
    private RoomData current;
    private boolean seenRandomShop;

    private final int ROOMS_PER_LEVEL = 10;

    public void setSeenRandomShop(boolean seenRandomShop) {
        this.seenRandomShop = seenRandomShop;
    }

    public RunState(String name) {
        player = new Player(name);
        seenRandomShop = false;
        current = new CombatData();
        setRooms();
    }

    public RoomData generateRoom(ExtraRewardType rewardType) {
        if (player.getRoom() == ROOMS_PER_LEVEL - 2) {
            return new BossData();
        }
        if (MathUtils.random() < 0.05 && !seenRandomShop && player.getRoom() > 2) {
            seenRandomShop = true;
            return new ShopData();
        }
        return generateCombatRoom(rewardType, false);
    }

    public RoomData generateRoom(ExtraRewardType rewardType, boolean noElites) {
        if (player.getRoom() == ROOMS_PER_LEVEL - 2) {
            return new BossData();
        }
        if (MathUtils.random() < 0.1 && !seenRandomShop && player.getRoom() > 2) {
            seenRandomShop = true;
            return new ShopData();
        }
        return generateCombatRoom(rewardType, noElites);
    }

    public CombatData generateCombatRoom(ExtraRewardType rewardType, boolean noElites) {
        if (MathUtils.random() < 0.2 && !noElites) {
            return new EliteData(rewardType);
        } else {
            return new CombatData(rewardType);
        }
    }

    public void setRooms() {
        System.out.println(player.getRoom());
        List<ExtraRewardType> choices = new ArrayList<>(Arrays.asList(ExtraRewardType.MONEY,
                ExtraRewardType.SKILL,
                ExtraRewardType.STAT));

        ExtraRewardType r1 = choices.remove(MathUtils.random(2));
        ExtraRewardType r2 = choices.remove(MathUtils.random(1));

        if (player.getRoom() == ROOMS_PER_LEVEL - 2) {
            left = generateRoom(r1);
            right = null;
        } else if (player.getRoom() == ROOMS_PER_LEVEL - 3) {
            left = new ShopData();
            seenRandomShop = true;
            right = generateRoom(r1);
        } else {
            left = generateRoom(r1);
            if (left.getType() == RoomType.ELITE) {
                right = generateRoom(r2, true);
            } else {
                right = generateRoom(r2);
            }
        }
    }

    public RoomData getCurrent() {
        return current;
    }

    public void setCurrent(RoomData current) {
        this.current = current;
    }

    public RoomData getLeft() {
        return left;
    }

    public RoomData getRight() {
        return right;
    }

    public Player getPlayer() {
        return player;
    }
}
