package edu.drake.game;

public class ShopData extends RoomData {
    public ShopData() {
        super();
        type = RoomType.SHOP;
    }

    @Override
    public String toString() {
        return type.toString() + "\n\nSpend your money on new \n skills, items, and stats";
    }
}
