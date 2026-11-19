package edu.drake;
import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Entity implements Serializable {
    protected String type;
    protected String name;
    protected int hp;
    protected int max_hp;
    protected int mp;
    protected int max_mp;
    protected int strength;
    protected int magic;
    protected int vitality;
    protected int agility;
    protected int money;
    protected ArrayList<String> skills;
    protected HashMap<String, String> affinities;
    protected HashMap<String, Double> status_effects;

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

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

    public boolean hasAilment(String ailment) {
        if (this.status_effects.get(ailment.toLowerCase()) == 1) {
            return true;
        }
        return false;
    }

    public void inflictAilment(String ailment) {
        if (!hasAilment(ailment)) {
            this.status_effects.replace(ailment, 1.0);
        }
    }

    public void clearAilment() {
        if (hasAilment("dazed")) {
            this.status_effects.replace("dazed", 0.0);
        } else if (hasAilment("frozen")) {
            this.status_effects.replace("frozen", 0.0);
        } else if (hasAilment("shocked")) {
            this.status_effects.replace("shocked", 0.0);
        }
    }

    public boolean isGuarding() {
        if (this.status_effects.get("guarding") == 1.0) {
            return true;
        }
        return false;
    }

    public void guard() {
        this.status_effects.replace("guarding", 1.0);
    }

    public void clearGuard() {
        if (isGuarding()) {
            this.status_effects.replace("guarding", 0.0);
        }
    }

    public void healOverTime() {
        double amount = this.status_effects.get("heal_over_time") % 100;

        if (this.status_effects.get("heal_over_time") < 100) {
            this.status_effects.replace("heal_over_time", 0.0);
        } else {

        }
    }
}
