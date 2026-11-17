package edu.drake;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Skill {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private String name;
    private String type, subtype;
    private int cost;
    private int level;
    private ArrayList<HashSet<String>> requirements = new ArrayList<>();
    private String[] effect;
    private String target;
    private int critChance;
    private int minHits, maxHits;
    private String additionalEffect, additionalEffectCondition;

    public Skill (String skillName) {
        try (InputStream input = Player.class.getResourceAsStream("/edu/drake/game_data.json")) {
            if (input == null) {
                throw new IllegalStateException("Missing classpath resource: /edu/drake/game_data.json");
            }

            JsonNode rootNode = MAPPER.readTree(input);
            JsonNode skillData = rootNode.get("assets").get("skills").get(skillName);

            this.name = skillName;
            String[] typeSet = skillData.get("type").asText().split(" ");
            this.type = typeSet[0];
            this.subtype = typeSet[1];
            this.level = skillData.get("level").asInt();
            this.effect = skillData.get("effect").asText().split(" ");

            JsonNode requirementsList = skillData.path("requirements");
            for (JsonNode requirementNode : requirementsList) {
                HashSet<String> mainRequirement = new HashSet<String>();

                if (requirementNode.isArray()) {
                    for (JsonNode subRequirement : requirementNode) {
                        mainRequirement.add(subRequirement.asText());
                    }

                } else {
                    mainRequirement.add(requirementNode.asText());
                }

                this.requirements.add(mainRequirement);
            }

            switch (this.type) {
                case "strength":
                    this.cost = skillData.get("hp_cost").asInt();
                    this.critChance = Integer.valueOf(this.effect[2]);

                    if (this.effect.length > 5) {
                        this.target = this.effect[5];
                    } else {
                        this.target = "single-target";
                    }

                    if (this.effect.length > 6) {
                        if (this.effect[6].equals("x")) {
                            this.minHits = Integer.valueOf(this.effect[7]);
                            this.maxHits = Integer.valueOf(this.effect[9]);

                        } else if (this.effect[6].equals("+")) {
                            this.minHits = 1;
                            this.maxHits = 1;

                            if (this.effect[8].equals("attack")) {
                                additionalEffect = "Tarunda";
                            } else if (this.effect[8].equals("defense")) {
                                additionalEffect = "Rakunda";
                            } else if (this.effect[9].equals("against")) {
                                additionalEffect = this.effect[7];
                                additionalEffectCondition = this.effect[10];
                            }
                        }
                    } else {
                        this.minHits = 1;
                        this.maxHits = 1;
                    }
                    break;

                case "magic":
                    this.cost = skillData.get("mp_cost").asInt();

                    if (this.effect.length > 2) {
                        this.target = this.effect[2];
                    } else {
                        this.target = "single-target";
                    }

                    if (this.effect.length > 3) {
                        if (this.effect[3].equals("x")) {
                            this.minHits = Integer.valueOf(this.effect[4]);
                            this.maxHits = Integer.valueOf(this.effect[6]);

                        } else if (this.effect[3].equals("+")) {
                            this.minHits = 1;
                            this.maxHits = 1;

                            if (this.effect[5].equals("attack")) {
                                additionalEffect = "Tarunda";
                            } else if (this.effect[5].equals("defense")) {
                                additionalEffect = "Rakunda";
                            } else if (this.effect[6].equals("against")) {
                                additionalEffect = this.effect[4];
                                additionalEffectCondition = this.effect[7];
                            }
                        }
                    } else {
                        this.minHits = 1;
                        this.maxHits = 1;
                    }
                    break;

                case "buff":
                    this.cost = skillData.get("mp_cost").asInt();

                    if (Integer.valueOf(this.effect[0]) > 1 | this.subtype.equals("recovery")) {
                        this.target = "self";
                    } else {
                        this.target = "all";
                    }
                    break;

                case "passive":

                    break;
            }

        } catch (IOException e) {
            throw new IllegalStateException("Could not read /edu/drake/game_data.json", e);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type + " " + this.subtype;
    }

    public int getLevel() {
        return this.level;
    }

    public String getEffect() {
        String returnString = "";
        for (String component : this.effect) {
            returnString += component + " ";
        }
        return returnString;
    }

    public int getCost() {
        return this.cost;
    }

    public String getTarget() {
        return this.target;
    }

    public int getCritChance() {
        return this.critChance;
    }

    public int getMinHits() {
        return this.minHits;
    }

    public int getMaxHits() {
        return this.maxHits;
    }

    public String getAddEffect() {
        return additionalEffect;
    }

    public String getAddEffectCondition() {
        return additionalEffectCondition;
    }

    public boolean canLearn(ArrayList<String> skillsList) {
        boolean skillFound = false;
        
        for (HashSet<String> mainRequirement : this.requirements) {
            for (String skill : mainRequirement) {
                if (skillsList.contains(skill)) {
                    skillFound = true;
                    break;
                }
            }

            if (skillFound == false) {
                return false;
            }
        }
        return true;
    }
}
