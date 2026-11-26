package edu.drake;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SkillDatabase {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private HashMap<String, Skill> skills;
    private ArrayList<Skill> initiallyAvailableSkills;

    public SkillDatabase() {
        try (InputStream input = Player.class.getResourceAsStream("/edu/drake/game_data.json")) {
            if (input == null) {
                throw new IllegalStateException("Missing classpath resource: /edu/drake/game_data.json");
            }

            JsonNode rootNode = MAPPER.readTree(input);
            JsonNode allSkills = rootNode.get("assets").get("skills");

            Iterator<String> iter = allSkills.fieldNames();

            skills = new HashMap<>();
            initiallyAvailableSkills = new ArrayList<>();

            while (iter.hasNext()) {
                String name = iter.next();
                JsonNode data = allSkills.get(name);

                Skill skill = new Skill(name, data);

                skills.put(name, skill);
            }

            for (Entry<String, Skill> kvPair : skills.entrySet()) {
                Skill s = kvPair.getValue();
                boolean noReqs = true;
                List<String> requirements = s.getRequirements().stream().flatMap(requirementSet -> requirementSet.stream()).toList();
                for (String requirement : requirements) {
                    System.out.println(requirement);
                    skills.get(requirement).addParent(kvPair.getKey());
                    if (noReqs) {
                        noReqs = false;
                    }
                }
                if (noReqs) {
                    initiallyAvailableSkills.add(s);
                }
            }

        } catch (IOException e) {
            throw new IllegalStateException("Could not read /edu/drake/game_data.json", e);
        }
    }

    public HashSet<Skill> getUnlockableSkills(ArrayList<Skill> currentlyKnownSkills) {
        HashSet<Skill> unlockable = new HashSet<>();
        unlockable.addAll(initiallyAvailableSkills);
        for (Skill skill : currentlyKnownSkills) {
            skill.getParents().stream().map(s -> skills.get(s)).forEach(parent -> {
                if (parent.canLearn(currentlyKnownSkills.stream().map(s -> s.getOriginalID()).toList())) {
                    unlockable.add(parent);
                }
            });
        }
        unlockable.removeIf(s -> currentlyKnownSkills.contains(s));
        return unlockable;
    }

    public ArrayList<Skill> getInitiallyAvailableSkills() {
        return initiallyAvailableSkills;
    }

    public HashMap<String, Skill> getSkills() {
        return skills;
    }

    public static void main(String[] args) {
        SkillDatabase db = new SkillDatabase();

        ArrayList<Skill> start = db.getInitiallyAvailableSkills();

        System.out.println(start);
        
        System.out.println(db.getUnlockableSkills(start));
    }
}
