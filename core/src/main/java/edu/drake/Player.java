package edu.drake;
import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Player extends Entity {
    private int mp;
    private int max_mp;
    private int money;

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

        } catch (IOException e) {
            throw new IllegalStateException("Could not read /edu/drake/game_data.json", e);
        }
    }

    public int getHp() {
        return this.hp;
    }

    public int getMaxHp() {
        return this.max_hp;
    }

    public int getMp() {
        return this.mp;
    }

    public int getMaxMp() {
        return this.max_mp;
    }

    public int getStrength() {
        return this.strength;
    }

    public void increaseStrength() {
        this.strength++;
    }

    public int getMagic() {
        return this.magic;
    }

    public void increaseMagic() {
        this.magic++;
        this.max_mp += 5;
    } 

    public int getVitality() {
        return this.vitality;
    }

    public void increaseVitality() {
        this.vitality++;
        this.max_hp += 10;
    }

    public int getAgility() {
        return this.agility;
    }

    public void increaseAgility() {
        this.agility++;
    }

    public int getMoney() {
        return this.money;
    }

    public void increaseMoney(int amount) {
        this.money += amount;
    }
}
