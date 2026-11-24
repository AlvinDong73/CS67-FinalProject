package edu.drake;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.Random;

public class Combat {
    private static ArrayList<Entity> turnOrder;
    private static String target;
    private static Entity currentEnemy;
    private static Skill currentSkill;
    private static boolean allEnemiesDefeated;
    private static Scanner input;
    private static Random random = new Random();

    public static boolean startCombat(Player player, ArrayList<Enemy> enemiesList) {
        allEnemiesDefeated = false;
        input = new Scanner(System.in);
        turnOrder = new ArrayList<Entity>();
        turnOrder.add(player);
        for (Enemy enemy : enemiesList) {
            turnOrder.add(enemy);
        }
        turnOrder.sort((a, b) -> Double.compare(b.getAgility(), a.getAgility()));

        while (player.getHp() > 0 & allEnemiesDefeated == false) {
            System.out.println("\nTurn order:");
            for (int i = 0; i<turnOrder.size(); i++) { // print turn order
                if (i == turnOrder.size() - 1) {
                    System.out.print(turnOrder.get(i).getName() + "\n");
                } else {
                    System.out.print(turnOrder.get(i).getName() + ", ");
                }
            }

            for (int i=0; i<turnOrder.size(); i++) { // actions
                if (turnOrder.get(i).getType().equals("player")) {
                    System.out.println("\n------ " + player.getName() + "'s turn ------");

                    player.clearAilment();
                    player.clearGuard();
                    player.healOverTime();

                    String choice = "";
                    String[] choiceList;

                    do {
                        System.out.print("Select an action (attack, skill, item, defend, analyze): ");
                        choice = input.nextLine().toLowerCase();
                        choiceList = choice.split(" ");

                        if (choice.toLowerCase().equals("analyze")) {
                            System.out.println();
                            for (Entity combatant : turnOrder) {
                                System.out.println(combatant + "\n");
                            }
                        }

                        if (choice.toLowerCase().equals("skill") & choiceList.length == 1) {
                            System.out.println("Skills list: " + player.getActiveSkills());
                        }

                        if (choice.toLowerCase().equals("item") & choiceList.length == 1) {
                            System.out.println("Inventory: " + player.getInventory());
                        }

                    } while (
                        choice.toLowerCase().equals("analyze") |
                        (choice.toLowerCase().equals("skill") & choiceList.length == 1) |
                        (choice.toLowerCase().equals("item") & choiceList.length == 1));

                    switch (choiceList[0]) {
                        case "attack":
                            currentSkill = new Skill("Attack");

                            if (choiceList.length > 1) {
                                target = capitalize(choiceList[1]);
                            } else {
                                System.out.print("Select target: ");
                                target = capitalize(input.nextLine());
                            }

                            for (Enemy enemy : enemiesList) {
                                if (enemy.getName().equals(target)) {
                                    currentEnemy = enemy;
                                }
                            }
                            
                            useSkill(player, currentEnemy, currentSkill);
                            break;

                        case "skill":
                            currentSkill = new Skill(choiceList[1]);
                            System.out.println("Use skill " + currentSkill.getName());

                            if (currentSkill.getTarget().equals("single-target")) {

                                if (choiceList.length > 2) {
                                    target = capitalize(choiceList[2]);
                                } else {
                                    System.out.print("Select target: ");
                                    target = capitalize(input.nextLine());
                                }

                                for (Enemy enemy : enemiesList) {
                                    if (enemy.getName().equals(target)) {
                                        currentEnemy = enemy;
                                    }
                                }

                                int numHits;
                                if (currentSkill.getMinHits() != currentSkill.getMaxHits()) {
                                    numHits = random.nextInt(currentSkill.getMinHits(), currentSkill.getMaxHits());
                                } else {
                                    numHits = 1;
                                }

                                for (int j=0; j<numHits; j++) {
                                    useSkill(player, currentEnemy, currentSkill);
                                }

                            } else if (currentSkill.getTarget().equals("all")) {
                                int numHits;
                                for (Enemy enemy : enemiesList) {
                                    if (currentSkill.getMinHits() != currentSkill.getMaxHits()) {
                                        numHits = random.nextInt(currentSkill.getMinHits(), currentSkill.getMaxHits());
                                    } else {
                                        numHits = 1;
                                    }

                                    for (int j=0; j<numHits; j++) {
                                        useSkill(player, enemy, currentSkill);
                                    }
                                }

                            } else if (currentSkill.getTarget().equals("random-target")) {
                                int numHits = random.nextInt(currentSkill.getMinHits(), currentSkill.getMaxHits());
                                for (int j=0; j<numHits; j++) {
                                    currentEnemy = enemiesList.get(random.nextInt(enemiesList.size()));
                                    useSkill(player, currentEnemy, currentSkill);
                                }

                            } else if (currentSkill.getTarget().equals("self")) {
                                useSkill(player, null, currentSkill);
                            }

                            if (currentSkill.getType().equals("strength")) {
                                double hpPercent = currentSkill.getCost() / 100;
                                player.increaseHP(-(player.getMaxHp() * hpPercent));
                            } else {
                                player.increaseMP(-currentSkill.getCost());
                            }

                        break;

                        case "item":
                            System.out.println("Use item " + choiceList[1]);
                            break;

                        case "defend":
                            System.out.println(player.getName() + " guards. (Incoming damage reduced until next turn)");
                            player.guard();
                            break;
                    }

                } else if (turnOrder.get(i).getType().equals("enemy")) {
                    currentEnemy = turnOrder.get(i);
                    if (currentEnemy.getHp() == 0) {
                        int enemiesDefeated = 0;
                        for (Enemy enemy : enemiesList) {
                            if (enemy.getHp() == 0) {
                                enemiesDefeated++;
                            }
                        }

                        if (enemiesDefeated == enemiesList.size()) {
                            allEnemiesDefeated = true;
                        }

                        continue;
                    }

                    System.out.println("\n------ " + currentEnemy.getName() + "'s turn ------");
                    currentEnemy.clearAilment();
                    currentEnemy.clearGuard();
                    currentEnemy.healOverTime();

                    int enemyChoiceIndex = random.nextInt(currentEnemy.getSkills().size());
                    String enemyChoice = currentEnemy.getSkills().get(enemyChoiceIndex);
                    currentSkill = new Skill(enemyChoice);

                    if (currentSkill.getTarget().equals("self")) {
                        useSkill(currentEnemy, null, currentSkill);
                    } else {
                        int numHits;
                        if (currentSkill.getMinHits() != currentSkill.getMaxHits()) {
                            numHits = random.nextInt(currentSkill.getMinHits(), currentSkill.getMaxHits());
                        } else {
                            numHits = 1;
                        }

                        for (int j=0; j<numHits; j++) {
                            useSkill(currentEnemy, player, currentSkill);
                        }
                    }
                }
            }
        }

        return finishBattle(player, enemiesList);
    }

    public static boolean finishBattle(Player player, ArrayList<Enemy> enemiesList) {
        if (allEnemiesDefeated == true) {
            for (Enemy enemy : enemiesList) {
                player.increaseMoney(enemy.getMoney());
                if (enemy.getDrops().size() == 0) {
                    continue;
                }
                
                int itemChance = random.nextInt(100);
                if (itemChance < 50) {
                    int itemNumber = random.nextInt(enemy.getDrops().size());
                    player.addItem(enemy.getDrops().get(itemNumber));
                }
            }
            return true;

        } else {
            System.out.println("You died.");
            return false;
        }
    }

    public static void useSkill(Entity attacker, Entity receiver, Skill skill) {
        if (receiver == null) {
            System.out.println(attacker.getName() + " is using " + skill.getName());
        } else {
            System.out.println(attacker.getName() + " is using " + skill.getName() + " on " + receiver.getName());
        }

        String[] effect = skill.getEffect().split(" ");

        if (skill.getSubType().equals("recovery")) {
            int amount = Integer.valueOf(effect[0]);

            if (effect.length > 2) { // recarm & samarecarm
                attacker.addHealOverTime(amount, 3);
            } else if (attacker.checkForSkill("Heal_amp")) { // else check for boosts
                attacker.increaseHP(amount * 1.5);
                attacker.setStatBooster("defense", 0.25);
            } else if (attacker.checkForSkill("Heal_boost")) {
                attacker.increaseHP(amount * 1.25);
            } else {
                attacker.increaseHP(amount);
            }

        } else if (skill.getType().equals("buff")) {
            if (skill.getSubType().equals("clear")) {
                if (receiver == null) { // dekunda
                    attacker.clearStatBoosts();
                } else { // dekaja
                    receiver.clearStatBoosts();
                }

            } else if (receiver == null) { // buffs
                attacker.setStatBooster(effect[1], Double.valueOf(effect[0]));
            } else { // debuffs
                receiver.setStatBooster(effect[1], Double.valueOf(effect[0]));
            }

        } else {
            calculateDamage(attacker, receiver, skill);
        }
    }

    public static void calculateDamage(Entity attacker, Entity receiver, Skill skill) {
        int basePower = Integer.valueOf(skill.getEffect().split(" ")[0]);
        if (skill.getAddEffectCondition() != null) {
            if (receiver.hasAilment(skill.getAddEffectCondition())) {
                basePower += Integer.valueOf(skill.getAddEffect());
            }
        }
        
        double amplifier = 1;
        if (skill.getType().equals("strength")) {
            amplifier = attacker.getStrength() / receiver.getVitality();
            if (amplifier > 2) {
                amplifier = 2;
            } else if (amplifier < .5) {
                amplifier = .5;
            }

            double critChance = skill.getCritChance();
            if (attacker.getSkills().contains("Crit_amp")) {
                critChance *= 1.4;
            } else if (attacker.getSkills().contains("Crit_boost")) {
                critChance *= 1.2;
            }

            int critRoll = random.nextInt(100);
            if (critRoll < critChance) {
                System.out.println("CRITICAL!");
                if (attacker.getSkills().contains("Apt_pupil")) {
                    amplifier += 1.5;
                } else {
                    amplifier += 1;
                }
            }

        } else if (skill.getType().equals("magic")) {
            amplifier = attacker.getMagic() / receiver.getVitality();
            if (amplifier > 2) {
                amplifier = 2;
            } else if (amplifier < .5) {
                amplifier = .5;
            }

            if (attacker.getSkills().contains("Magic_mastery")) {
                amplifier += .5;
            }
        }

        if (attacker.getSkills().contains(capitalize(skill.getSubType()) + "_amp")) {
            amplifier += .4;
        } else if (attacker.getSkills().contains(capitalize(skill.getSubType()) + "_boost")) {
            amplifier += .2;
        }

        double damage = basePower * amplifier;
        String affinity = receiver.checkResistance(skill.getSubType());
        switch (affinity) {
            case "weak":
                damage *= 1.5;
                receiver.increaseHP(-damage);
                System.out.println("WEAK! " + attacker.getName() + " hits for " + (int) damage + " damage.");
                break;
        
            case "resist":
                damage *= 0.5;
                receiver.increaseHP(-damage);
                System.out.println("RESIST. " + attacker.getName() + " hits for " + (int) damage + " damage.");
                break;

            case "null":
                System.out.println("NULL. " + attacker.getName() + " hits for 0 damage.");
                break;

            case "drain":
                receiver.increaseHP(damage);
                System.out.println("DRAIN. " + receiver.getName() + " heals for " + (int) damage + " damage.");
                break;

            case "repel":
                String attackerAffinity = attacker.checkResistance(skill.getSubType());
                switch (attackerAffinity) {
                    case "weak":
                        damage *= 1.5;
                        attacker.increaseHP(-damage);
                        System.out.println("REPEL! " + attacker.getName() + " is hit for " + (int) damage + " damage.");
                        break;
                
                    case "resist":
                        damage *= 0.5;
                        attacker.increaseHP(-damage);
                        System.out.println("REPEL! " + attacker.getName() + " is hit for " + (int) damage + " damage.");
                        break;

                    case "neutral":
                        attacker.increaseHP(-damage);
                        System.out.println("REPEL! " + attacker.getName() + " is hit for " + (int) damage + " damage.");
                        break;
                    
                    case "drain":
                        attacker.increaseHP(damage);
                        System.out.println("REPEL! " + attacker.getName() + " heals for " + (int) damage + " damage.");
                        break;
                    
                    default: // null or repel
                        System.out.println("REPEL! " + attacker.getName() + " is hit for 0 damage.");
                        break;
                }
                break;

            default: // neutral
                receiver.increaseHP(-damage);
                System.out.println(attacker.getName() + " hits for " + (int) damage + " damage.");
                break;
        }
    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase(Locale.ROOT) + 
            text.substring(1).toLowerCase(Locale.ROOT);
    }
}