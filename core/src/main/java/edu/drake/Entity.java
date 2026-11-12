package edu.drake;
import java.util.HashMap;

public abstract class Entity {
    protected int hp;
    protected int max_hp;
    protected int strength;
    protected int magic;
    protected int vitality;
    protected int agility;
    protected HashMap<String, String> affinities;
    protected HashMap<String, Boolean> status_effects;
}
