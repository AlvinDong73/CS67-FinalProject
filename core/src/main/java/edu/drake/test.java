package edu.drake;

public class test {
    public static void main(String[] args) {
        Player test = new Player();

        System.out.printf("\nHP: %d", test.getHp());

        System.out.printf("\nStrength before: %d", test.getStrength());
        test.increaseStrength();
        System.out.printf("\nStrength after: %d", test.getStrength());

        System.out.printf("\nVitality before: %d", test.getVitality());
        test.increaseVitality();
        System.out.printf("\nVitality after: %d", test.getVitality());
        System.out.printf("\nHP after: %d", test.getMaxHp());

        System.out.printf("\nMoney before: %d", test.getMoney());
        test.increaseMoney(50);
        System.out.printf("\nMoney after: %d\n", test.getMoney());
    }
}
