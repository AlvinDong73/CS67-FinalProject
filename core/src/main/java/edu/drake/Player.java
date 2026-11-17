package edu.drake;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Player extends Entity {
    private int mp;
    private int max_mp;
    private int level;
    private int room;
    private HashMap<String, Integer> inventory;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public Player() {
        try (InputStream input = Player.class.getResourceAsStream("/edu/drake/game_data.json")) {
            if (input == null) {
                throw new IllegalStateException("Missing classpath resource: /edu/drake/game_data.json");
            }
            JsonNode rootNode = MAPPER.readTree(input);
            JsonNode baseStats = rootNode.path("assets").path("base_stats");

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
            this.inventory = new HashMap<String, Integer>();

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
            this.status_effects.put("ailment", null);
            this.status_effects.put("heal_over_time", null);

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

    public void increaseStrength() {
        this.strength++;
    }

    public void increaseMagic() {
        this.magic++;
        this.max_mp += 5;
    }

    public void increaseVitality() {
        this.vitality++;
        this.max_hp += 10;
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

    public String toString() {
        return
            this.hp + "/" + this.max_hp + "HP" + " " + this.mp + "/" + this.max_mp + "MP" + '\n' +
            "St: " + this.strength + " | Ma: " + this.magic + " | Vi: " + this.vitality + " | Ag: " + this.agility + 
            this.getAffinities();
    }
}
