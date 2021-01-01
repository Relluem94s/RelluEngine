package de.relluem94.vulcan.toolbox.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum GameMode {
    SURVIVAL(0),
    CREATIVE(1),
    SPECTATOR(2);

    private final int value;
    private static final Map<Integer, GameMode> BY_ID;

    private GameMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static GameMode getByValue(int value) {
        return (GameMode) BY_ID.get(Integer.valueOf(value));
    }

    static {
        BY_ID = Maps.newHashMap();
        GameMode[] array;
        int i = (array = values()).length;
        for (int h = 0; h < i; h++) {
            GameMode mode = array[h];
            BY_ID.put(Integer.valueOf(mode.getValue()), mode);
        }
    }
}
