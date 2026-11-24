package edu.drake;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SkillDatabase {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private HashSet<Skill> skills;

    public SkillDatabase() {
        try (InputStream input = Player.class.getResourceAsStream("/edu/drake/game_data.json")) {
            if (input == null) {
                throw new IllegalStateException("Missing classpath resource: /edu/drake/game_data.json");
            }

            JsonNode rootNode = MAPPER.readTree(input);
            JsonNode allSkills = rootNode.get("assets").get("skills");

            Iterator<String> iter = allSkills.fieldNames();
            while (iter.hasNext()) {
                String name = iter.next();
                JsonNode data = allSkills.get(name);

                
            }

        } catch (IOException e) {
            throw new IllegalStateException("Could not read /edu/drake/game_data.json", e);
        }
    }
}
