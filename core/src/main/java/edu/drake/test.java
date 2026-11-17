package edu.drake;

public class test {
    public static void main(String[] args) {
        Player test = new Player();
        Enemy testEnemy = new Enemy("Pixie");
        Skill testSkill = new Skill("Mana_bonus");

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
        System.out.println("Phys resistance: " + test.checkResistance("phys"));

        // Inventory
        System.out.println("Inventory before: " + test.getInventory());
        test.addItem("Bead");
        System.out.println("Inventory after: " + test.getInventory());

        // Player toString
        System.out.println(test);

        // Enemy stats
        System.out.println();
        System.out.println("Enemy: " + testEnemy.getName());
        System.out.printf("HP: %d\n", testEnemy.getHp());
        System.out.printf("Strength: %d", testEnemy.getStrength());

        // Enemy skills
        System.out.println("Skills: " + testEnemy.getSkills());
        System.out.println("Check for Lunge: " + testEnemy.checkForSkill("Lunge"));
        System.out.println("Check for Dia: " + testEnemy.checkForSkill("Dia"));

        // Drops
        System.out.println("Drops list: " + testEnemy.getDrops());

        // Enemy toString
        System.out.println(testEnemy);

        // Skill data
        System.out.println();
        System.out.println("Skill: " + testSkill.getName());
        System.out.println("Type: " + testSkill.getType());
        System.out.println("Effect: " + testSkill.getEffect());
        System.out.printf("Cost: %d\n", testSkill.getCost());
        System.out.println("Target: " + testSkill.getTarget());
        System.out.println("Min hits: " + testSkill.getMinHits() + "; Max hits: " + testSkill.getMaxHits());
        System.out.println("Additional effect: " + testSkill.getAddEffect() + " when " + testSkill.getAddEffectCondition());

        // canLearn
        System.out.println("Learned skills: " + testEnemy.getSkills());
        System.out.println("Can learn: " + testSkill.canLearn(testEnemy.getSkills()));
    }
}
