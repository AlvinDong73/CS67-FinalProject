package edu.drake;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Combat {
    private static ArrayList<Entity> turnOrder;
    private static Entity currentEnemy;
    private static Skill currentSkill;
    private static boolean allEnemiesDefeated = false;
    private static Scanner input;
    private static Random random = new Random();

    public static void startCombat(Player player, ArrayList<Enemy> enemiesList) {
        input = new Scanner(System.in);
        turnOrder = new ArrayList<Entity>();
        turnOrder.add(player);
        for (Enemy enemy : enemiesList) {
            turnOrder.add(enemy);
        }
        turnOrder.sort((a, b) -> Integer.compare(b.getAgility(), a.getAgility()));

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
                    System.out.print("Select an action (attack, skill, item, defend): ");
                    choice = input.nextLine().toLowerCase();
                    choiceList = choice.split(" ");

                    switch (choiceList[0]) {
                        case "attack":
                            System.out.println("Melee attack");
                            currentSkill = new Skill(choice);
                            break;

                        case "skill":
                            if (choiceList.length == 1) {
                                System.out.println("Skills list: " + player.getActiveSkills());
                            } else {
                                currentSkill = new Skill(choiceList[1]);
                                System.out.println("Use skill " + currentSkill.getName());

                                if (currentSkill.getTarget().equals("single-target")) {
                                    System.out.println("Select target: ");
                                    String target = input.nextLine();

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
                            }
                            break;

                        case "item":
                            if (choiceList.length == 1) {
                                System.out.println("Inventory: " + player.getInventory());
                            } else {
                                System.out.println("Use item " + choiceList[1]);
                            }
                            break;

                        case "defend":
                            System.out.println(player.getName() + " guards. (Incoming damage reduced until next turn)");
                            player.guard();
                            break;
                    }

                } else if (turnOrder.get(i).getType().equals("enemy")) {
                    currentEnemy = turnOrder.get(i);
                    System.out.println("\n------ " + currentEnemy.getName() + "'s turn ------");

                    currentEnemy.clearAilment();
                    currentEnemy.clearGuard();
                    currentEnemy.healOverTime();

                    int enemyChoiceIndex = random.nextInt(currentEnemy.getSkills().size());
                    String enemyChoice = currentEnemy.getSkills().get(enemyChoiceIndex);

                    if (enemyChoice.equals("Attack")) {
                        System.out.println("Melee atack");
                    } else {
                        System.out.println("Use skill " + enemyChoice);
                    }
                }
            }
        }
    }

    public static void useSkill(Entity attacker, Entity receiver, Skill skill) {
        if (receiver == null) {
            System.out.println(attacker.getName() + " is using " + skill.getName());
        } else {
            System.out.println(attacker.getName() + " is using " + skill.getName() + " on " + receiver.getName());
        }
    }
}