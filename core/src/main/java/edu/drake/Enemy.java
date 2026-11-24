package edu.drake;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Enemy extends Entity {

    private ArrayList<String> drops;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Enemy(String name) {
        try (InputStream input = Player.class.getResourceAsStream("/edu/drake/game_data.json")) {
            if (input == null) {
                throw new IllegalStateException("Missing classpath resource: /edu/drake/game_data.json");
            }
            JsonNode rootNode = MAPPER.readTree(input);
            JsonNode enemyData = rootNode.get("assets").get("enemies").get(name);

            this.type = "enemy";
            this.name = name;
            this.max_hp = enemyData.get("max_hp").asInt();
            this.hp = this.max_hp;
            this.strength = enemyData.get("strength").asInt();
            this.magic = enemyData.get("magic").asInt();
            this.vitality = enemyData.get("vitality").asInt();
            this.agility = enemyData.get("agility").asInt();
            this.money = enemyData.get("money").asInt();

            this.skills = new ArrayList<String>();
            for (JsonNode skillName : enemyData.path("skills")) {
                this.skills.add(skillName.asText());
            }

            this.affinities = new HashMap<String, String>();
            this.affinities.put("phys", enemyData.get("affinities").get("phys").asText());
            this.affinities.put("pierce", enemyData.get("affinities").get("pierce").asText());
            this.affinities.put("fire", enemyData.get("affinities").get("fire").asText());
            this.affinities.put("ice", enemyData.get("affinities").get("ice").asText());
            this.affinities.put("elec", enemyData.get("affinities").get("elec").asText());
            this.affinities.put("wind", enemyData.get("affinities").get("wind").asText());
            this.affinities.put("light", enemyData.get("affinities").get("light").asText());
            this.affinities.put("dark", enemyData.get("affinities").get("dark").asText());

            this.drops = new ArrayList<>();
            for (JsonNode drop : enemyData.path("drops")) {
                this.drops.add(drop.asText());
            }

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

    public ArrayList<String> getDrops() {
        return this.drops;
    }
}
