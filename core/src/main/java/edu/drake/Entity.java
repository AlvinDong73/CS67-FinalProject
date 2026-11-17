package edu.drake;
import java.util.HashMap;
import java.util.ArrayList;

public abstract class Entity {
    protected int hp;
    protected int max_hp;
    protected int strength;
    protected int magic;
    protected int vitality;
    protected int agility;
    protected int money;
    protected ArrayList<String> skills;
    protected HashMap<String, String> affinities;
    protected HashMap<String, Double> status_effects;

    public int getHp() {
        return this.hp;
    }

    public int getMaxHp() {
        return this.max_hp;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getMagic() {
        return this.magic;
    } 

    public int getVitality() {
        return this.vitality;
    }

    public int getAgility() {
        return this.agility;
    }

    public int getMoney() {
        return this.money;
    }

    public ArrayList<String> getSkills() {
        return this.skills;
    }

    public boolean checkForSkill(String skillName) {
        return this.skills.contains(skillName);
    }

    public String getAffinities() {
        return
            '\n' + "Phys: " + this.affinities.get("phys") + 
            '\n' + "Pierce: " + this.affinities.get("pierce") +
            '\n' + "Fire: " + this.affinities.get("fire") + 
            '\n' + "Ice: " + this.affinities.get("ice") + 
            '\n' + "Elec: " + this.affinities.get("elec") + 
            '\n' + "Wind: " + this.affinities.get("wind") + 
            '\n' + "Light: " + this.affinities.get("light") +
            '\n' + "Dark: " + this.affinities.get("dark");
    }

    public String checkResistance(String element) {
        return this.affinities.get(element);
    }
}
