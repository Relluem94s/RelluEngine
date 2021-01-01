package de.relluem94.vulcan.toolbox.enums;

import java.util.HashMap;
import java.util.Map;

public enum Enchantments {
    // Weapons
    SHARPNESS("sharpness", 0, 5),
    SMITE("smite", 1, 5),
    KOCKBACK("knockback", 2, 5),
    FIREASPECT("fireaspect", 3, 3),
    // Others
    THORNS("thorns", 4, 3),
    BLAST_PROTECTION("blast_protection", 5, 5),
    FIRE_PROTECTION("fire_protection", 6, 5),
    PROJECTILE_PROTECTION("projectile_protection", 7, 5),
    //Others
    EFFICIENCY("efficiency", 8, 5),
    UNBREAKING("unbreaking", 9, 3);

    private static final Map<String, Enchantments> NAME_MAP = new HashMap<String, Enchantments>();
    private static final Map<Integer, Enchantments> ID_MAP = new HashMap<Integer, Enchantments>();
    private static final Map<Integer, Enchantments> MAX_MAP = new HashMap<Integer, Enchantments>();
    private final String name;
    private final int id;
    private final int max;

    static {
        for (Enchantments effect : values()) {
            NAME_MAP.put(effect.name, effect);
            ID_MAP.put(effect.id, effect);
            MAX_MAP.put(effect.max, effect);
        }
    }

    private Enchantments(String name, int id, int max) {
        this.name = name;
        this.id = id;
        this.max = max;

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getMax() {
        return max;
    }

}
