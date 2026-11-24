package edu.drake;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Player extends Entity {
    private int level;
    private int room;
    private HashMap<String, Integer> inventory;
    private ArrayList<String> activeSkills;


    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public Player(String name) {
        try (InputStream input = Player.class.getResourceAsStream("/edu/drake/game_data.json")) {
            if (input == null) {
                throw new IllegalStateException("Missing classpath resource: /edu/drake/game_data.json");
            }
            JsonNode rootNode = MAPPER.readTree(input);
            JsonNode baseStats = rootNode.path("assets").path("base_stats");

            this.type = "player";
            this.name = name;
            this.hp = baseStats.get("hp").asInt();
            this.max_hp = baseStats.get("max_hp").asInt();
            this.mp = baseStats.get("mp").asInt();
            this.max_mp = baseStats.get("max_mp").asInt();
            this.strength = baseStats.get("strength").asInt();
            this.magic = baseStats.get("magic").asInt();
            this.vitality = baseStats.get("vitality").asInt();
            this.agility = baseStats.get("agility").asInt();
            this.money = baseStats.get("money").asInt();
            this.skills = new ArrayList<String>();
            this.skills.add("Attack");
            this.activeSkills = new ArrayList<String>();
            this.activeSkills.add("Attack");
            this.inventory = new HashMap<String, Integer>();
            this.level = 1;
            this.room = 1;

            this.affinities = new HashMap<String, String>();
            this.affinities.put("phys", "neutral");
            this.affinities.put("pierce", "neutral");
            this.affinities.put("fire", "neutral");
            this.affinities.put("ice", "neutral");
            this.affinities.put("elec", "neutral");
            this.affinities.put("wind", "neutral");
            this.affinities.put("light", "null");
            this.affinities.put("dark", "neutral");

            this.status_effects = new HashMap<String, Double>();
            this.status_effects.put("phys_attack", 1.0);
            this.status_effects.put("magic_attack", 1.0);
            this.status_effects.put("defense", 1.0);
            this.status_effects.put("dazed", 0.0);
            this.status_effects.put("frozen", 0.0);
            this.status_effects.put("shocked", 0.0);
            this.status_effects.put("heal_over_time", 0.0);
            this.status_effects.put("guarding", 0.0);

        } catch (IOException e) {
            throw new IllegalStateException("Could not read /edu/drake/game_data.json", e);
        }
    }

    public int getMp() {
        return this.mp;
    }

    public int getMaxMp() {
        return this.max_mp;
    }

    public void increaseMP(double amount) {
        this.mp += amount;
        if (this.mp > this.max_hp) {
            this.mp = this.max_hp;
        } else if (this.mp < 0) {
            this.mp = 0;
        }
    }

    public void increaseMaxHP(double percent) {
        this.max_hp += this.max_hp * percent;
    }

    public void increaseMaxMP(double percent) {
        this.max_mp += this.max_mp * percent;
    }

    public void increaseStrength() {
        this.strength++;
    }

    public void increaseMagic() {
        this.magic++;
        
        if (this.skills.contains("Mana_surge")) {
            this.max_mp += 15;
        } else if (this.skills.contains("Mana_gain")) {
            this.max_hp += 11;
        } else if (this.skills.contains("Mana_bonus")) {
            this.max_hp += 8;
        } else {
            this.max_hp += 5;
        }
    }

    public void increaseVitality() {
        this.vitality++;
        
        if (this.skills.contains("Life_surge")) {
            this.max_hp += 20;
        } else if (this.skills.contains("Life_gain")) {
            this.max_hp += 16;
        } else if (this.skills.contains("Life_bonus")) {
            this.max_hp += 13;
        } else {
            this.max_hp += 10;
        }
    }

    public void increaseAgility() {
        this.agility++;
    }

    public void increaseMoney(int amount) {
        this.money += amount;
    }

    public String getInventory() {
        if (this.inventory.isEmpty()) {
            return "Inventory is empty.";
        }

        String returnString = "";
        for (String item : this.inventory.keySet()) {
            returnString += '\n' + item + ": " + this.inventory.get(item);
        }

        return returnString;
    }

    public void addItem(String item) {
        if (this.inventory.containsKey(item)) {
            this.inventory.replace(item, this.inventory.get(item) + 1);
        } else {
            this.inventory.put(item, 1);
        }
    }

    public void setAffinity(String element, String resistance) {
        this.affinities.replace(element, resistance);
    }

    public int getLevel() {
        return level;
    }

    public int getRoom() {
        return room;
    }

    public void addSkill(String skillName) {
        this.skills.add(skillName);
    }

    public ArrayList<String> getActiveSkills() {
        return activeSkills;
    }

    public void addActiveSkill(String skillName) {
        this.activeSkills.add(skillName);
    }

    public String toString() {
        return
            this.name + ": " + this.hp + "/" + this.max_hp + "HP " + this.mp + "/" + this.max_mp + "MP" +'\n' +
            "St: " + this.strength + " | Ma: " + this.magic + " | Vi: " + this.vitality + " | Ag: " + this.agility + 
            this.getAffinities();
    }
}
