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

    public void increaseHP(double amount) {
        this.hp += amount;
        if (this.hp > this.max_hp) {
            this.hp = this.max_hp;
        } else if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public int getMaxHp() {
        return this.max_hp;
    }

    public double getStrength() {
        return this.strength;
    }

    public double getMagic() {
        return this.magic;
    } 

    public double getVitality() {
        return this.vitality;
    }

    public double getAgility() {
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

    public double getStatBooster(String stat) {
        return this.status_effects.get(stat);
    }

    public void setStatBooster(String stat, double amount) {
        if (stat.equals("attack")) {
            this.status_effects.replace("phys_attack", this.status_effects.get("phys_attack") + amount);
            this.status_effects.replace("magic_attack", this.status_effects.get("magic_attack") + amount);
        } else {
            this.status_effects.replace(stat, this.status_effects.get(stat) + amount);
        }

        String[] stats = {"phys_attack", "magic_attack", "defense"};
        for (String statToCheck : stats) {
            if (this.status_effects.get(statToCheck) > 1.5) {
                this.status_effects.replace(statToCheck, 1.5);
            } else if (this.status_effects.get(statToCheck) < 0.5) {
                this.status_effects.replace(statToCheck, 0.5);
            }
        }
    }

    public void clearStatBoosts() {
        String[] stats = {"phys_attack", "magic_attack", "defense"};
        for (String statToCheck : stats) {
            this.status_effects.replace(statToCheck, 1.0);
        }
    }

    public boolean hasAilment(String ailment) {
        if (this.status_effects.get(ailment.toLowerCase()) == 1.0) {
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

        if (this.status_effects.get("heal_over_time") > 100) {
            if (checkForSkill("Heal_amp")) {
                increaseHP(amount * 1.5);
            } else if (checkForSkill("Heal_boost")) {
                increaseHP(amount * 1.25);
            } else {
                increaseHP(amount);
            }

            this.status_effects.replace("heal_over_time", this.status_effects.get("heal_over_time") - 100);
        } else if (this.status_effects.get("heal_over_time") > 0) {
            this.status_effects.replace("heal_over_time", 0.0);
        }
    }

    public void addHealOverTime(int amount, int duration) {
        double value = duration * 100 + amount;
        this.status_effects.replace("heal_over_time", value);
    }

    public double getHealOverTime() {
        return this.status_effects.get("heal_over_time");
    }

    public String toString() {
        return
            this.name + ": " + this.hp + "/" + this.max_hp + "HP" + '\n' +
            "St: " + this.strength + " | Ma: " + this.magic + " | Vi: " + this.vitality + " | Ag: " + this.agility + 
            this.getAffinities();
    }
}
