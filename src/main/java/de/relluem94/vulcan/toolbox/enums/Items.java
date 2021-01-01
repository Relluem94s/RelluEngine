package de.relluem94.vulcan.toolbox.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Items {
    STONE(0),
    OBSIDIAN(1),
    IRON(2),
    STEEL(3);

    private final int value;
    private static final Map<Float, Items> BY_ID;

    private Items(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Items getByValue(int value) {
        return (Items) BY_ID.get(Float.valueOf(value));
    }

    static {
        BY_ID = Maps.newHashMap();
        Items[] array;
        int i = (array = values()).length;
        for (int h = 0; h < i; h++) {
            Items mode = array[h];
            BY_ID.put(Float.valueOf(mode.getValue()), mode);
        }
    }
}
