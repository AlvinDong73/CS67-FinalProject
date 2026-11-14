package edu.drake;

public class test {
    public static void main(String[] args) {
        Player test = new Player();
        Enemy testEnemy = new Enemy("Slime");

        // Player stats
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

        // Player affinities
        System.out.println("\nAffinities before:" + test.getAffinities());
        test.setAffinity("phys", "resist");
        System.out. println("\nAffinities after:" + test.getAffinities());
        System.out.println(test.checkResistance("phys"));

        // Inventory
        System.out.println("Inventory before: " + test.getInventory());
        test.addItem("Bead");
        System.out.println("Inventory after: " + test.getInventory());

        // Enemy stats
        System.out.printf("\nHP: %d", testEnemy.getHp());
        System.out.printf("\nStrength: %d", testEnemy.getStrength());

        // Enemy skills
        System.out.println("Skills: " + testEnemy.getSkills());
        System.out.println("Check for Lunge: " + testEnemy.checkForSkill("Lunge"));
        System.out.println("Check for Dia: " + testEnemy.checkForSkill("Dia"));

        // Drops
        System.out.println("Drops list: " + testEnemy.getDrops());

        // Enemy toString
        System.out.println(testEnemy);
    }
}
